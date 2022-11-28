package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        ContactList contactList = new ContactList("Book.xml");
        System.out.println(contactList.getInfo("375297600590"));
        System.out.println(contactList.findByName("Андрухович"));
        System.out.println(contactList.findByAddress("111"));
    }
}