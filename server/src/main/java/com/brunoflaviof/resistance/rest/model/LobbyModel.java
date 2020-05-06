package com.brunoflaviof.resistance.rest.model;

public class LobbyModel {
    private final String name;
    private final boolean isProtected;

    public LobbyModel(String name, boolean isProtected) {
        this.name = name;
        this.isProtected = isProtected;
    }

    public String getName() {
        return name;
    }

    public boolean isProtected() {
        return isProtected;
    }
}
