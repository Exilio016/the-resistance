package com.brunoflaviof.resistance.rest.controller.impl;

import com.brunoflaviof.resistance.rest.controller.LobbyController;
import com.brunoflaviof.resistance.rest.controller.UserController;
import com.brunoflaviof.resistance.rest.controller.exception.EmptyUserNameException;
import com.brunoflaviof.resistance.rest.controller.exception.LobbySameNameException;
import com.brunoflaviof.resistance.rest.jwt.JWTUtil;
import com.brunoflaviof.resistance.rest.model.CreateLobby;
import com.brunoflaviof.resistance.rest.model.LobbyList;
import com.brunoflaviof.resistance.rest.model.LobbyModel;
import com.brunoflaviof.resistance.rest.model.UserModel;
import com.brunoflaviof.resistance.rest.repository.LobbyRepo;
import com.brunoflaviof.resistance.rest.repository.UserRepo;
import com.brunoflaviof.resistance.rest.repository.data.Lobby;
import com.brunoflaviof.resistance.rest.repository.data.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@org.springframework.web.bind.annotation.RestController
public class RestController implements LobbyController, UserController {

    private final LobbyRepo lobbyRepo;
    private final UserRepo userRepo;
    private final JWTUtil jwtUtil;

    @Autowired
    public RestController(LobbyRepo lobbyRepo, UserRepo userRepo, JWTUtil jwtUtil) {
        this.lobbyRepo = lobbyRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LobbyList getLobbies() {
        return new LobbyList(getLobbyModels(lobbyRepo.findAll()));
    }

    private List<LobbyModel> getLobbyModels(Iterable<Lobby> lobbies) {
        List<LobbyModel> list = new ArrayList<>();
        for(Lobby l : lobbies){
            list.add(new LobbyModel(l.getName(), l.hasPassword()));
        }
        return list;
    }

    @Override
    public void createLobby(String userID, CreateLobby lobby) {
        User u = userRepo.getByUserID(UUID.fromString(userID));
        if(lobbyRepo.findByName(lobby.getName()) == null) {
            Lobby l = new Lobby(userID, lobby.getName(), lobby.getPassword().orElse(null));
            u.setLobby(l);
            l.getUsers().add(u);
            lobbyRepo.save(l);
            userRepo.save(u);
            return;
        }
        throw new LobbySameNameException();
    }

    @Override
    public UserModel createUser(String displayName) {
        if(displayName == null || displayName.isEmpty())
            throw new EmptyUserNameException();

        User u = new User(displayName);
        userRepo.save(u);
        return new UserModel(u.getUserID(), jwtUtil.generateToken(u));
    }

    @Override
    public Lobby getLobbyByName(String name) {
        return lobbyRepo.findByName(name);
    }


    public UserModel verifyToken(String token) {
        User u = userRepo.getByUserID(jwtUtil.getUserIDFromToken(token));
        return new UserModel(u.getUserID(), token);
    }
}
