package com.brunoflaviof.resistance.rest.model;

import java.util.Optional;

public class CreateLobby {
    private final String userId;
    private final String name;
    private final Optional<String> password;

    public CreateLobby(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = Optional.ofNullable(password);
    }

    public String getUserId() {
        return userId;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
