package org.example;

import org.example.entities.ViolationStatistic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Path> paths = getListAllFilePath();
        for (Path path : paths) {
            Parser.parseViolationFile(path.toFile());
        }
        List<ViolationStatistic> violationStatisticList = Parser.getSortedViolationStatisticList();
        Parser.parseListToXml(violationStatisticList);

    }

    private static List<Path> getListAllFilePath() {
        try (Stream<Path> paths = Files.walk(Paths.get("Task2/src/main/resources/input"))) {
            return paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
