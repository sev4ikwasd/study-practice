package com.example.practice.repository.impl;

import com.example.practice.exception.MarketNotFoundException;
import com.example.practice.model.Heading;
import com.example.practice.model.Market;
import com.example.practice.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@Primary
public class CsvMarketRepository implements MarketRepository {
    private final Path csvFile;

    public CsvMarketRepository(@Qualifier("csvFile") Path csvFile) {
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

    private String lineFromMarket(Market market) {
        StringBuilder headingsLine = new StringBuilder("\"");
        for (Heading heading : market.getHeadings()) {
            headingsLine.append(String.format("%s,%s,%s,%s;",
                    heading.getId().toString(),
                    heading.getName(),
                    heading.getDescription(),
                    heading.getPriceCents()));
        }
        headingsLine.append("\"");
        return String.format("%s,%s,%s,%s,%s",
                market.getId().toString(),
                market.getName(),
                market.getCategory(),
                headingsLine,
                market.isBlocked());
    }

    @Override
    public List<Market> getAllMarkets() {
        try (Stream<String> stream = Files.lines(csvFile)) {
            return stream
                    .filter(line -> !line.isEmpty())
                    .map(this::marketFromLine).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Market getMarketById(UUID id) throws MarketNotFoundException {
        try (Stream<String> stream = Files.lines(csvFile)) {
            String marketLine = stream
                    .filter(line -> !line.isEmpty())
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

        try {
            List<String> out = new ArrayList<>();
            out.add(lineFromMarket(market));
            Files.write(csvFile, out, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            return market;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeMarket(UUID id) throws MarketNotFoundException {
        getMarketById(id);
        try (Stream<String> stream = Files.lines(csvFile)) {
            List<String> out = stream
                    .filter(line -> !line.isEmpty())
                    .filter(line -> !line.startsWith(id.toString()))
                    .toList();
            Files.write(csvFile, out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
