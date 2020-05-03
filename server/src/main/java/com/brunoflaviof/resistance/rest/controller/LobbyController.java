package com.brunoflaviof.resistance.rest.controller;

import com.brunoflaviof.resistance.rest.model.CreateLobby;
import com.brunoflaviof.resistance.rest.model.LobbyList;
import com.brunoflaviof.resistance.rest.repository.data.Lobby;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface LobbyController {

    @GetMapping("/lobbies")
    public LobbyList getLobbies();

    @PutMapping("/lobby")
    public void createLobby(@RequestHeader("userId") String userId, @RequestBody CreateLobby lobby);

    @GetMapping("/lobby")
    public Lobby getLobbyByName(@RequestParam String name);
}
