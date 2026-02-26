package ua.com.foxmined.carrestservice.utils;

import org.springframework.stereotype.Component;
import ua.com.foxmined.carrestservice.dto.CarCsvRow;
import ua.com.foxmined.carrestservice.exception.CarServiceException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CarCsvParser {

    private static final int MIN_FRAGMENTS = 5;

    // format: objectId,Make,Year,Model,Category
    public Optional<CarCsvRow> parseLine(String line) {
        try {
            String[] fragments = line.split(",");
            if (fragments.length < MIN_FRAGMENTS) {
                return Optional.empty();
            }
            return Optional.of(parseFragments(line, fragments));
        } catch (Exception e) {
            throw new CarServiceException("Failed to parse CSV line: " + line, e);
        }
    }

    private CarCsvRow parseFragments(String line, String[] fragments) {
        return new CarCsvRow(
                fragments[0].trim(),
                fragments[1].trim(),
                fragments[2].trim(),
                fragments[3].trim(),
                parseCategoryNames(line, fragments)
        );
    }

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
