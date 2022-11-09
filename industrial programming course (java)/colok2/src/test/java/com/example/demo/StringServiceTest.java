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
    void threeString() {
        StringService service1 = new StringService("xa");
        service1.threeString();
        assertEquals("xaxaxa", service1.getResult());
        StringService service2 = new StringService("sr");
        service2.threeString();
        assertEquals("srsrsr", service2.getResult());
    }


}