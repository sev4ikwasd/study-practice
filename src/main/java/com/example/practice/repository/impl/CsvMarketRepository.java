package com.example.practice.repository.impl;

import com.example.practice.exception.MarketNotFoundException;
import com.example.practice.model.Heading;
import com.example.practice.model.Market;
import com.example.practice.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Component
@Primary
public class CsvMarketRepository implements MarketRepository {
    private final File csvFile;

    public CsvMarketRepository(@Qualifier("csvFile") File csvFile) {
        this.csvFile = csvFile;
    }

    private Market marketFromLine(String marketLine) {
        String[] marketAttributes = marketLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        List<Heading> headings = new ArrayList<>();
        String headingsLine = marketAttributes[3];
        headingsLine = headingsLine.substring(1, headingsLine.length() - 1);
        String[] headingsLines = headingsLine.split(";");
        if (!((headingsLines.length == 1) && headingsLines[0].isEmpty())) {
            for (String headingLine : headingsLines) {
                String[] headingAttributes = headingLine.split(",");
                headings.add(Heading.builder()
                        .id(UUID.fromString(headingAttributes[0]))
                        .name(headingAttributes[1])
                        .description(headingAttributes[2])
                        .priceCents(Integer.parseInt(headingAttributes[3])).build());
            }
        }

        return Market.builder()
                .id(UUID.fromString(marketAttributes[0]))
                .name(marketAttributes[1])
                .category(marketAttributes[2])
                .headings(headings)
                .isBlocked(Boolean.parseBoolean(marketAttributes[4])).build();
    }

    @Override
    public List<Market> getAllMarkets() {
        try (Stream<String> stream = Files.lines(csvFile.toPath())) {
            return stream.filter(line -> !line.isEmpty()).map(this::marketFromLine).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Market getMarketById(UUID id) throws MarketNotFoundException {
        try (Stream<String> stream = Files.lines(csvFile.toPath())) {
            String marketLine = stream
                    .filter(line -> line.startsWith(id.toString()))
                    .findFirst()
                    .orElseThrow(() -> new MarketNotFoundException(id));
            return marketFromLine(marketLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Market saveMarket(Market market) {
        try {
            Market foundMarket = getMarketById(market.getId());
            removeMarket(foundMarket.getId());
        } catch (MarketNotFoundException ignored) {
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
            bw.newLine();
            String headingsLine = "\"";
            for (Heading heading : market.getHeadings()) {
                headingsLine += String.format("%s,%s,%s,%s;",
                        heading.getId().toString(),
                        heading.getName(),
                        heading.getDescription(),
                        heading.getPriceCents());
            }
            headingsLine += "\"";
            bw.write(String.format("%s,%s,%s,%s,%s",
                    market.getId().toString(),
                    market.getName(),
                    market.getCategory(),
                    headingsLine,
                    market.isBlocked()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return market;
    }

    @Override
    public void removeMarket(UUID id) throws MarketNotFoundException {
        File temp = new File("_temp_");
        try (PrintWriter out = new PrintWriter(new FileWriter(temp));
             Stream<String> stream = Files.lines(csvFile.toPath())) {
            AtomicBoolean removed = new AtomicBoolean(false);
            stream
                    .filter(line -> !line.isEmpty())
                    .filter(line -> {
                        if (line.startsWith(id.toString())) {
                            removed.set(true);
                            return false;
                        }
                        return true;
                    })
                    .forEach(out::println);
            out.flush();
            out.close();
            temp.renameTo(csvFile);
            if (!removed.get()) {
                throw new MarketNotFoundException(id);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
