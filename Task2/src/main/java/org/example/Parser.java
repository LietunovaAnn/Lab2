package org.example;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import org.example.entities.Violation;
import org.example.entities.ViolationStatistic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Parser {
    private static final File resultFile = new File("Task2/src/main/resources/violationStatisticFile.xml");
    private static final Map<String, Double> violationStatisticMap = new HashMap<>();


    public static void parseViolationFile(File inputFile) {
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("},")) {
            while (scanner.hasNextLine()) {
                String violationEntity = getValidJson(scanner.next() + "}");
                Violation violation = new Gson().fromJson(violationEntity, Violation.class);
                saveViolationStatistic(violation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getValidJson(String violationEntity) {
        violationEntity = violationEntity.replace("[", "");
        violationEntity = violationEntity.replace("]\r\n}", "");
        return violationEntity;
    }


    private static void saveViolationStatistic(Violation violation) {
        String type = violation.getType();
        Double fineAmount = violation.getFineAmount();
        if (violationStatisticMap.containsKey(type)) {
            violationStatisticMap.put(type, fineAmount + violationStatisticMap.get(type));
        } else {
            violationStatisticMap.put(type, fineAmount);
        }
    }

    public static List<ViolationStatistic> getSortedViolationStatisticList() {
        List<ViolationStatistic> violationStatisticList = new ArrayList<>();
        violationStatisticMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> violationStatisticList.add(new ViolationStatistic(x.getKey(), x.getValue())));
        return violationStatisticList;
    }

    public static void parseListToXml(List<ViolationStatistic> violationStatisticList) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(resultFile, violationStatisticList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
