package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class JsonLib {
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

    public static void jsonForm() throws IOException {
        Data data = new Data();
        ArrayList<String> numberList = new ArrayList<>(Arrays.asList(data.numbers.split("\n")));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList(data.names.split("\n")));
        ArrayList<String> adList = new ArrayList<>(Arrays.asList(data.addresses.split("\n")));
        FileWriter writer = new FileWriter("Book.json");
        JSONArray jsonPeople = new JSONArray();
        for (int i = 0; i < numberList.size(); i++) {
            JSONObject jsonPerson = new JSONObject();
            String curName = nameList.get(i);
            String curNumber = numberList.get(i);
            String curAddress = adList.get(i);
            jsonPerson.put("name", curName);
            jsonPerson.put("address", curAddress);
            jsonPerson.put("number", curNumber);
            jsonPeople.add(jsonPerson);
        }
        writer.write(jsonPeople.toJSONString());
        writer.write(("\n"));
        writer.flush();
    }

    public static ArrayList<Person> jsonParsePerson(String fileName) {
        ArrayList<Person> people = new ArrayList<>();
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader(fileName));
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                String name = (String) object.get("name");
                String address = (String) object.get("address");
                String number = (String) object.get("number");
                Person person = new Person(name, number, address);
                people.add(person);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public static Hashtable<String, Person> formHashTable(String fileName) {
        ArrayList<Person> people = jsonParsePerson(fileName);
        Hashtable hashtable = new Hashtable<>(people.size());
        for (Person person : people) {
            hashtable.put(person, person);
        }
        return hashtable;
    }

}
