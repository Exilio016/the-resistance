package com.brunoflaviof.resistance.rest.model;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

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

    public boolean isHasPassword() {
        return hasPassword;
    }
}
