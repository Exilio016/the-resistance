package com.brunoflaviof.resistance.rest.model;

import java.util.List;

public class LobbyList {
    private final List<LobbyModel> lobbies;

    public List<LobbyModel> getLobbies() {
        return lobbies;
    }

    public LobbyList(List<LobbyModel> lobbies) {
        this.lobbies = lobbies;
    }
}
