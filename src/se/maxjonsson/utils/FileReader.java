package se.maxjonsson.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    private FileReader() {
    }

    public static String readSingleLine(String fileName) {
        String singleLine;
        try {
            singleLine = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file.", e);
        }

        return singleLine;
    }

    public static List<String> readLines(String fileName) {
        List<String> lines;
        try (Scanner s = new Scanner(new File(fileName))) {
            lines = new ArrayList<>();
            while (s.hasNext()) {
                lines.add(s.nextLine());
            }
            s.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!", e);
        }

        return lines;
    }
}
