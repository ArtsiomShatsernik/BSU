package org.example;

import junit.framework.TestCase;

public class ContactListTest extends TestCase {
    public ContactList contactList;
    public void setUp() throws Exception {
        contactList = new ContactList("Book.xml");
    }

    public void testGetInfo() {
        String expected = "Name: Сергеев Address: 11 Number: 375297600590";
        String actual = contactList.getInfo("375297600590");
        assertEquals(expected, actual);
    }

    public void testFindByName() {
        String expected1 = "Name: Андрухович Address: 74 Number: 375294615976";
        String actual1 = contactList.findByName("Андрухович");
        assertEquals(expected1, actual1);
        String expected2 = "Error! No such person.";
        String actual2 = contactList.findByName("Леваков");
        assertEquals(expected2, actual2);
    }
    public void testFindByAddress() {
        String expected1 = "Name: Андрухович Address: 74 Number: 375294615976";
        String actual1 = contactList.findByAddress("74");
        assertEquals(expected1, actual1);
        String expected2 = "Error! No such person.";
        String actual2 = contactList.findByAddress("11111");
        assertEquals(expected2, actual2);
    }
}