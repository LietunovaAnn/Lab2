package org.example;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final File resultFile = new File("Task1/src/main/resources/personsNewFile.xml");
    private static Map<String, Double> violationMap = new HashMap<>;

    public static void clearResultFile() {
        if (resultFile.exists()) {
            resultFile.delete();
        }
    }

    public static void parseXML(File inputFile) {
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter(",")) {
            while (scanner.hasNextLine()) {
                //String personEntity = findTypeFineAmount(scanner.next());
               // writeToResFile(personEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findTypeFineAmount(String violationEntity) {
        violationEntity = violationEntity.replace("[", "");
        violationEntity = violationEntity.replace("]", "");
        Violation violation = new Gson().fromJson(violationEntity, Violation.class);
        String type = violation.getType();
        Double fineAmount = violation.getFineAmount();
        if(violationMap.containsKey(type)) {
            violationMap.put(type, fineAmount + violationMap.get(type));
        } else {
            violationMap.put(type, fineAmount);
        }


    }

    private static Map<String, Double> sortedMap () {
        Map<String, Double> reverseSortedMap = new LinkedHashMap<>();
        violationMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap;
    }


    public static void writeToResFile(String xmlSource) {
        try (FileWriter fw = new FileWriter(resultFile, true)) {
            fw.write(xmlSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
