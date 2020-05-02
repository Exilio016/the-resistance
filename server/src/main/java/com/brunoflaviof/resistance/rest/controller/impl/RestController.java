package com.brunoflaviof.resistance.rest.controller.impl;

import com.brunoflaviof.resistance.rest.controller.LobbyController;
import com.brunoflaviof.resistance.rest.controller.UserController;
import com.brunoflaviof.resistance.rest.controller.exception.EmptyUserNameException;
import com.brunoflaviof.resistance.rest.jwt.JWTUtil;
import com.brunoflaviof.resistance.rest.model.CreateLobby;
import com.brunoflaviof.resistance.rest.model.LobbyList;
import com.brunoflaviof.resistance.rest.model.UserModel;
import com.brunoflaviof.resistance.rest.repository.LobbyRepo;
import com.brunoflaviof.resistance.rest.repository.UserRepo;
import com.brunoflaviof.resistance.rest.repository.data.Lobby;
import com.brunoflaviof.resistance.rest.repository.data.User;
import org.springframework.beans.factory.annotation.Autowired;

public class RestController implements LobbyController, UserController {

    private final LobbyRepo lobbyRepo;
    private final UserRepo userRepo;

    @Autowired
    public RestController(LobbyRepo lobbyRepo, UserRepo userRepo) {
        this.lobbyRepo = lobbyRepo;
        this.userRepo = userRepo;
    }

    @Override
    public LobbyList getLobbies() {
        return new LobbyList(lobbyRepo.getAllModel());
    }

    @Override
    public void createLobby(CreateLobby lobby) {
        lobbyRepo.createLobby(lobby.getUserId(), lobby.getName(), lobby.getPassword());
    }

    @Override
    public UserModel createUser(String displayName) {
        if(displayName == null || displayName.isEmpty())
            throw new EmptyUserNameException();

        User u = userRepo.createUser(displayName);
        return new UserModel(u.getUserID(), JWTUtil.generateToken(u));
    }

    @Override
    public Lobby getLobbyByName(String name) {
        return lobbyRepo.getLobby(name);
    }


    public UserModel verifyToken(String token) {
        User u = userRepo.getUser(JWTUtil.getUserIDFromToken(token));
        return new UserModel(u.getUserID(), token);
    }
}
