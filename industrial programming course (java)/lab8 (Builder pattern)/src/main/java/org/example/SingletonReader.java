package org.example;

import java.io.*;
import java.util.ArrayList;

public class SingletonReader {
    private static SingletonReader reader;
    private SingletonReader() {
    }
    public static SingletonReader getInstance() {
        if (reader == null) {
            reader = new SingletonReader();
        }
        return reader;
    }
    public static ArrayList<String> readDB(String setName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(setName));
        ArrayList <String> data = new ArrayList<>();
        String nextLine;
        while((nextLine = reader.readLine()) != null) {
            data.add(nextLine);
        }
        return data;
    }
}
