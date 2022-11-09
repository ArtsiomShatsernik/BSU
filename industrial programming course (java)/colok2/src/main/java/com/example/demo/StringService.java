package com.example.demo;

public class StringService {
    private final String string1;

    public String result = "";

    public StringService(String string1) {
        this.string1 = string1;
    }
    public void stringCon(String string2) {
        StringBuilder builder = new StringBuilder(string1);
        builder.append(string2);
        result = builder.toString();
    }
    public void threeString() {
        result = string1 + string1 + string1;
    }
    public String getResult() {
        return result;
    }
}
