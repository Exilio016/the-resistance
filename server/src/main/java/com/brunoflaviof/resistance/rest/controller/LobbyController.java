package com.brunoflaviof.resistance.rest.controller;

import com.brunoflaviof.resistance.rest.model.CreateLobby;
import com.brunoflaviof.resistance.rest.model.LobbyList;
import com.brunoflaviof.resistance.rest.repository.data.Lobby;
import org.springframework.web.bind.annotation.*;

public interface LobbyController {

    @GetMapping("/lobbies")
    LobbyList getLobbies();

    @PutMapping("/lobby")
    void createLobby(@RequestHeader("userId") String userId, @RequestBody CreateLobby lobby);

    @GetMapping("/lobby")
    Lobby getLobbyByName(@RequestParam String name);
}
