package com.brunoflaviof.resistance.rest.model;

import java.util.Optional;

public class CreateLobby {
    private final String name;
    private final Optional<String> password;
    private final Optional<String> meetingURL;

    public CreateLobby(String name, String password, String meetingURL) {
        this.name = name;
        this.password = Optional.ofNullable(password);
        this.meetingURL = Optional.ofNullable(meetingURL);
    }

    public Optional<String> getMeetingURL() {
        return meetingURL;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
