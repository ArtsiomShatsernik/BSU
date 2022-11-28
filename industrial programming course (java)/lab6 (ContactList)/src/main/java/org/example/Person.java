package org.example;

import java.util.Objects;

public final class Person {
    private final String name;
    private final String number;
    private final String address;

    public Person(String name, String number, String address) {
        this.name = name;
        this.number = number;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(number, person.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public String name() {
        return name;
    }

    public String number() {
        return number;
    }

    public String address() {
        return address;
    }

}
