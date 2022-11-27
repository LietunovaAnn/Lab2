package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

         getListAllFilePath();

    }

    private static List<Path> getListAllFilePath() {
        try (Stream<Path> paths = Files.walk(Paths.get("Task2/src/main/resources"))) {
            return paths
                    .filter(Files::isRegularFile)

                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
