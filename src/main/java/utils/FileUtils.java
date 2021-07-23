package utils;

import exceptions.ApplicationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {
    public static List<String> getFileStrings(String path) throws ApplicationException {
        List<String> fileLines;
        try {
            fileLines = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        return fileLines;
    }
}
