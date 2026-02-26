package ua.com.foxmined.carrestservice.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.CarServiceException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class TxtFileReader {

    public List<String> readFile(String filename) {
        try (Stream<String> lineStream = Files.lines(Paths.get(filename))) {
            return lineStream.collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error reading file {}: {}", filename, e.getMessage());
            throw new CarServiceException("Error reading file: " + filename, e);
        }
    }

}
