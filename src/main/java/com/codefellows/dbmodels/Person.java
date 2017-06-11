package com.codefellows.dbmodels;

/**
 * Created by Brad on 6/10/2017.
 */
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return String.format("name: %s | age: %d", name, age);
    }
}
