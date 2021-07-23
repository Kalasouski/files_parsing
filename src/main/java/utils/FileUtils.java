package utils;

import exceptions.ApplicationException;
import exceptions.TransactionFileParsingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {
    public static List<String> getFileStrings(String path) throws TransactionFileParsingException {
        List<String> fileLines;
        try {
            fileLines = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new TransactionFileParsingException(e);
        }
        return fileLines;
    }
}
