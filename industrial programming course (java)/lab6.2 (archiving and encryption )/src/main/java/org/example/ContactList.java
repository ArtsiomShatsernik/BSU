package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class ContactList {
    private Hashtable<String, Person> contacts = new Hashtable<>();

    public ContactList(String fileName) throws IOException, ParserConfigurationException, SAXException {
        ArrayList<Person> people = ParseFactory.parseFile(fileName);
        for (Person person : people) {
            contacts.put(person.number(), person);
        }
    }

    public String getInfo(String number) {
        Person person = contacts.get(number);
        String info = "Name: " + person.name() + " Address: " + person.address() + " Number: " + person.number();
        return info;
    }
    public String findByName(String name) {
        Set<String> keys = contacts.keySet();
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()) {
            Person person = contacts.get(iterator.next());
            if (name.equals(person.name())) {
                String info = "Name: " + person.name() + " Address: " + person.address() + " Number: " + person.number();
                return info;
            }
        }
        return "Error! No such person.";
    }
    public String findByAddress(String address) {
        Set<String> keys = contacts.keySet();
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()) {
            Person person = contacts.get(iterator.next());
            if (address.equals(person.address())) {
                String info = "Name: " + person.name() + " Address: " + person.address() + " Number: " + person.number();
                return info;
            }
        }
        return "Error! No such person.";
    }
}
