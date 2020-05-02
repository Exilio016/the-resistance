package com.brunoflaviof.resistance.rest.repository.data;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class User {
    @Id
    private UUID userID = UUID.randomUUID();
    private final String name;

    public UUID getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }
}
