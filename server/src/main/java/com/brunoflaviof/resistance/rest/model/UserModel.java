package com.brunoflaviof.resistance.rest.model;

import java.util.UUID;

public class UserModel {
    private final UUID userId;
    private final String token;

    public UserModel(UUID userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
