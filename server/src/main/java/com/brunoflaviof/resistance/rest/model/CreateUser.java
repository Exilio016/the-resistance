package com.brunoflaviof.resistance.rest.model;

public class CreateUser {
    public final String name;

    public CreateUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
