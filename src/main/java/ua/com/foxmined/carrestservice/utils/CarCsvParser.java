package ua.com.foxmined.carrestservice.utils;

import org.springframework.stereotype.Component;
import ua.com.foxmined.carrestservice.dto.CarCsvRow;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Parses CSV lines into structured car data.
 */
@Component
public class CarCsvParser {

    private static final int MIN_FRAGMENTS = 5;

    /**
     * Parses a CSV line into CarCsvRow.
     *
     * @param line CSV line (objectId,Make,Year,Model,Category)
     * @return parsed row or empty if invalid
     */
    public Optional<CarCsvRow> parseLine(String line) {
        String[] fragments = line.split(",");
        if (fragments.length < MIN_FRAGMENTS) {
            return Optional.empty();
        }

        String objectId = fragments[0].trim();
        String manufacturer = fragments[1].trim();
        String year = fragments[2].trim();
        String model = fragments[3].trim();
        List<String> categoryNames = parseCategoryNames(line, fragments);

        return Optional.of(new CarCsvRow(objectId, manufacturer, year, model, categoryNames));
    }

    /**
     * Returns data lines without header.
     */
    public List<String> skipHeader(List<String> lines) {
        if (lines.isEmpty()) {
            return lines;
        }
        return lines.stream().skip(1).collect(Collectors.toList());
    }

    private List<String> parseCategoryNames(String line, String[] fragments) {
        if (fragments.length == MIN_FRAGMENTS) {
            return splitAndTrim(fragments[4]);
        }
        String[] quoted = line.split("\"");
        if (quoted.length >= 2) {
            return splitAndTrim(quoted[1]);
        }
        return List.of();
    }

    private List<String> splitAndTrim(String categories) {
        return Arrays.stream(categories.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
