package org.example;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {

    public static void parseXML(File file) {

        try (FileInputStream inputStream = new FileInputStream(file);
             Scanner sc = new Scanner(inputStream, "UTF-8").useDelimiter("/>")) {
            while (sc.hasNextLine()) {
                StringBuilder line = new StringBuilder(sc.next());
                line.append("/>");
                String s = changeString(line.toString());
                stringToDom(s);
               // System.out.println(s);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String changeString(String line) {

        Pattern logPattern = Pattern.compile(
                "(\\sname=\"\\S+\")");
        Pattern logPattern2 = Pattern.compile(
                "(\\ssurname=\"\\S+\")");

        Matcher matcher = logPattern.matcher(line);
        Matcher matcher2 = logPattern2.matcher(line);

        if (matcher.find()) {
            String name = matcher.group(1);
            if (matcher2.find()) {
                String surname = matcher2.group(1);
                line = line.replace(surname, "");
                name = name.substring(0, name.length() - 1);
                surname = surname.substring(surname.indexOf('"') + 1, surname.length() - 1);
                line = line.replace(name, name + " " + surname);
                return line;
            }
        }
        return "\n</persons>";
    }


    public static void stringToDom(String xmlSource) {
        try( FileWriter fw = new FileWriter("Task1/src/main/resources/personsNewFile.xml", true)) {
            fw.write(xmlSource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

