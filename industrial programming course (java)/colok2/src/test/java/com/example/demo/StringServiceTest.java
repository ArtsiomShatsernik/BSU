package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringServiceTest {

    @Test
    void stringCon() {
        StringService service = new StringService("xa");
        service.stringCon("ax");
        assertEquals("xaax",service.result);
    }

    @Test
    void multiString() {
        StringService service1 = new StringService("xa");
        service1.multiString(3);
        assertEquals("xaxaxa", service1.getResult());
        StringService service2 = new StringService("sr");
        service2.multiString(5);
        assertEquals("srsrsrsrsr", service2.getResult());
    }


}