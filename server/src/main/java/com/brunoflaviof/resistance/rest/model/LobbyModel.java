package com.brunoflaviof.resistance.rest.model;

public class LobbyModel {
    private final String name;
    private final boolean hasPassword;

    public LobbyModel(String name, boolean hasPassword) {
        this.name = name;
        this.hasPassword = hasPassword;
    }

    public String getName() {
        return name;
    }

    public boolean hasPassword() {
        return hasPassword;
    }
}
