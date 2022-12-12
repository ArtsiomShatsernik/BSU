package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        ArrayList <String> files = new ArrayList<>();
        files.add("Book.xml");
        files.add("Book.json");
        ArchivingLib.packJar(files,"testFiles");
        UnpackingFactory.unpackFile("testFiles.jar");
        CryptoLib.encrypt();
        ContactList contactList = new ContactList("Book.xml");
        System.out.println(contactList.getInfo("375297600590"));
        System.out.println(contactList.findByName("Андрухович"));
        System.out.println(contactList.findByAddress("111"));
    }
}