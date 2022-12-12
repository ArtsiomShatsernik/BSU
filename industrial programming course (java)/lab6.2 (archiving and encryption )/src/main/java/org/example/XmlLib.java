package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class XmlLib {
    public static class Data {
        public String numbers = "375294615976\n" +
                "375291306018\n" +
                "375297600590\n" +
                "375292224030\n" +
                "375290109975\n" +
                "375290570666\n" +
                "375292748640\n" +
                "375297328633\n" +
                "375294464115\n" +
                "375296333996\n" +
                "375296103501\n" +
                "375294411758\n" +
                "375290461614\n" +
                "375294764737\n" +
                "375294879993\n" +
                "375294541124\n" +
                "375296033276\n" +
                "375295212596\n" +
                "375297496439\n" +
                "375298300641\n" +
                "375295467225\n" +
                "375294790660\n" +
                "375290904393\n" +
                "375293329732\n" +
                "375298810167\n" +
                "375293760364\n" +
                "375296499321\n" +
                "375295363786\n" +
                "375297768344\n" +
                "375291085720\n";
        public String names = "Андрухович\n" +
                "Соколов\n" +
                "Сергеев\n" +
                "Сусаренко\n" +
                "Казаков\n" +
                "Лукин\n" +
                "Филиппов\n" +
                "Сыпченко\n" +
                "Кулишенко\n" +
                "Коломоец\n" +
                "Шаров\n" +
                "Кондратьев\n" +
                "Батейко\n" +
                "Плаксий\n" +
                "Калинин\n" +
                "Цветков\n" +
                "Предыбайло\n" +
                "Негода\n" +
                "Михайлов\n" +
                "Шевченко\n" +
                "Трясило\n" +
                "Скоропадский\n" +
                "Веселов\n" +
                "Панфилов\n" +
                "Ширяев\n" +
                "Мазайло\n" +
                "Батейко\n" +
                "Стрелков\n" +
                "Саксаганский\n" +
                "Романенко\n";
        public String addresses = ("74\n" +
                "116\n" +
                "11\n" +
                "199\n" +
                "50\n" +
                "122\n" +
                "14\n" +
                "15\n" +
                "193\n" +
                "89\n" +
                "114\n" +
                "158\n" +
                "97\n" +
                "37\n" +
                "130\n" +
                "12\n" +
                "160\n" +
                "135\n" +
                "172\n" +
                "156\n" +
                "60\n" +
                "152\n" +
                "187\n" +
                "191\n" +
                "100\n" +
                "72\n" +
                "111\n" +
                "121\n" +
                "133\n" +
                "197\n");
    }

    public static void xmlForm() throws ParserConfigurationException, TransformerException {
        Data data = new Data();
        ArrayList<String> numberList = new ArrayList<>(Arrays.asList(data.numbers.split("\n")));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList(data.names.split("\n")));
        ArrayList<String> adList = new ArrayList<>(Arrays.asList(data.addresses.split("\n")));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element persons = doc.createElement("Persons");
        for (int i = 0; i < numberList.size(); i++) {
            Element person = doc.createElement("Person");
            Element number = doc.createElement("number");
            number.appendChild(doc.createTextNode(numberList.get(i)));
            Element address = doc.createElement("address");
            address.appendChild(doc.createTextNode(adList.get(i)));
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(nameList.get(i)));
            person.appendChild(number);
            person.appendChild(address);
            person.appendChild(name);
            persons.appendChild(person);
        }
        doc.appendChild(persons);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult file = new StreamResult(new File("Book.xml"));
        transformer.transform(source, file);
    }

    public static ArrayList<Person> xmlParsePerson(String fileName) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<Person> people = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(fileName);
        NodeList persons = document.getElementsByTagName("Person");
        for (int i = 0; i < persons.getLength(); i++) {
            Node person = persons.item(i);
            Element ePerson = (Element) person;
            String number = ePerson.getElementsByTagName("number").item(0).getTextContent();
            String address = ePerson.getElementsByTagName("address").item(0).getTextContent();
            String name = ePerson.getElementsByTagName("name").item(0).getTextContent();
            people.add(new Person(name, number, address));
        }
        return people;
    }
}
