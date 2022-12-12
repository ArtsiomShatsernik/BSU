package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class ParseFactory {
    public static ArrayList<Person> parseFile(String fileName) throws IOException, ParserConfigurationException, SAXException {
        Path filePath = Paths.get(fileName);
        String contentType = Files.probeContentType(filePath);
        return switch (contentType) {
            case "application/json" -> JsonLib.jsonParsePerson(fileName);
            case "text/xml" -> XmlLib.xmlParsePerson(fileName);
            default -> throw new IllegalArgumentException("Unsupported file type: " + contentType);
        };
    }
}
