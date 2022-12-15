package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, GeneralSecurityException {
        ArrayList <String> files = new ArrayList<>();
        files.add("Book.xml");
        files.add("Book.json");
        ArchivingLib.packZip("testFiles");
        CryptoLib.encrypt("Book.xml", "123456789");
        CryptoLib.decrypt("encrypted_Book.xml", "123456789");
        ContactList contactList = new ContactList("Book.xml");
        System.out.println(contactList.getInfo("375297600590"));
        System.out.println(contactList.findByName("Андрухович"));
        System.out.println(contactList.findByAddress("111"));
    }
}