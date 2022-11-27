package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final File inputFile = new File("Task1/src/main/resources/persons.xml");
    private static final File resultFile = new File("Task1/src/main/resources/personsNewFile.xml");

    public static void clearResultFile() {
        if (resultFile.exists()) {
            resultFile.delete();
        }
    }
    public static void parseXML() {
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("/>")) {
            while (scanner.hasNextLine()) {
                String personEntity = joinNameWithSurname(scanner.next());
                writeToResFile(personEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String joinNameWithSurname(String personEntity) {
        Pattern logPatternName = Pattern.compile(
                "(\\sname[^=]*=[^\"]*\"[^\"]*\")");
        Pattern logPatternSurname = Pattern.compile(
                "(\\ssurname[^=]*=[^\"]*\"[^\"]*\")");

        Matcher matcherName = logPatternName.matcher(personEntity);
        Matcher matcherSurname = logPatternSurname.matcher(personEntity);

        if (matcherName.find()) {
            String name = matcherName.group(1);
            if (matcherSurname.find()) {
                String surname = matcherSurname.group(1);
                personEntity = personEntity.replace(surname, "");
                name = name.substring(0, name.length() - 1);
                surname = surname.substring(surname.indexOf('"') + 1, surname.length() - 1);
                if (!surname.isEmpty()) {
                    personEntity = personEntity.replace(name, name + " " + surname);
                }
                return personEntity + "/>";
            }
        } else if (personEntity.contains("</persons>")) {
            //for end file
            return personEntity;
        }
        //for person entity without name or surname
        return personEntity + "/>";
    }
    public static void writeToResFile(String xmlSource) {
        try (FileWriter fw = new FileWriter(resultFile, true)) {
            fw.write(xmlSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

