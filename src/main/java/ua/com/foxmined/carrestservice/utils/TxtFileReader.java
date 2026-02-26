package ua.com.foxmined.carrestservice.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class TxtFileReader {

    public List<String> readFile(String filename) throws FileException {

        try (Stream<String> lineStream = Files.lines(Paths.get(filename))) {
            return lineStream.collect(Collectors.toList());
        } catch (IOException exception) {
            log.error("Error open file" + exception.getMessage());
            throw new FileException("Error with file");
        }
    }

}
