package com.brunoflaviof.resistance.rest.controller.impl;

import com.brunoflaviof.resistance.rest.controller.LobbyController;
import com.brunoflaviof.resistance.rest.controller.UserController;
import com.brunoflaviof.resistance.rest.controller.exception.EmptyUserNameException;
import com.brunoflaviof.resistance.rest.controller.exception.LobbyNotExistingException;
import com.brunoflaviof.resistance.rest.controller.exception.LobbySameNameException;
import com.brunoflaviof.resistance.rest.controller.exception.WrongPasswordException;
import com.brunoflaviof.resistance.rest.jwt.JWTUtil;
import com.brunoflaviof.resistance.rest.model.ConnectLobby;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.logging.StreamHandler;

@org.springframework.web.bind.annotation.RestController
public class RestController implements LobbyController, UserController {

    private final LobbyRepo lobbyRepo;
    private final UserRepo userRepo;
    private final JWTUtil jwtUtil;
    private static final HashMap<String, Semaphore> lobbySemaphores = new HashMap<>();
    private static final HashMap<String, Semaphore> userSemaphores = new HashMap<>();

    @Autowired
    public RestController(LobbyRepo lobbyRepo,
			UserRepo userRepo, JWTUtil jwtUtil) {
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
        if (lobbyRepo.findByName(lobby.getName()) != null) {
            throw new LobbySameNameException();
        }
        try{
            Semaphore s = new Semaphore(1);
            s.acquire();
            lobbySemaphores.put(lobby.getName(), s);
            Lobby l = new Lobby(userID,
                    lobby.getName(), lobby.getPassword().orElse(null));
            u.setLobby(l);
            l.getUsers().add(u);
            lobbyRepo.save(l);
            userRepo.save(u);
            s.release();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public UserModel createUser(String displayName) {
        try {
            if (displayName == null || displayName.isEmpty())
                throw new EmptyUserNameException();
            User u = new User(displayName);
            addUserInDB(u);
            return new UserModel(u.getUserID(), jwtUtil.generateToken(u));
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
        return null;
    }

    private void addUserInDB(User u) throws InterruptedException {
        Semaphore s = new Semaphore(1, true);
        userRepo.save(u);
        userSemaphores.put(u.getUserID().toString(), s);
    }

    @Override
    public Lobby getLobbyByName(String name) {
        return lobbyRepo.findByName(name);
    }


    public UserModel verifyToken(String token) {
        User u = userRepo.getByUserID(jwtUtil.getUserIDFromToken(token));
        return new UserModel(u.getUserID(), token);
    }

	@Override
	public Lobby connectToLobby(String userId, ConnectLobby lobby){
        try{
            Semaphore userS = userSemaphores.get(userId);
            userS.acquire();
            Lobby l = updateDBtoConnectUserToLobby(userId, lobby);
            userS.release();
            verifyPassword(lobby, l);
            return l;
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
		return null;
	}

    private void verifyPassword(ConnectLobby lobby, Lobby l) {
        if(!isValidPassword(lobby, l))
            throw new WrongPasswordException();
    }

    private Lobby updateDBtoConnectUserToLobby(String userId, ConnectLobby lobby) throws InterruptedException {
        User u = userRepo.getByUserID(UUID.fromString(userId));
        Lobby l = ConnectUserToLobbyInDB(lobby, u);
        if(isValidPassword(lobby, l)) {
            u.setLobby(l);
            userRepo.save(u);
        }
        return l;
    }

    private Lobby ConnectUserToLobbyInDB(ConnectLobby lobby, User u) throws InterruptedException {
        Semaphore s = getSemaphore(lobby.getName());
        s.acquire();
        Lobby l = saveUserInLobbyDB(lobby, u);
        s.release();
        return l;
    }

    private Lobby saveUserInLobbyDB(ConnectLobby lobby, User u) {
        Lobby l = getLobby(lobby);
        if(isValidPassword(lobby, l)) {
            l.getUsers().add(u);
            lobbyRepo.save(l);
        }
        return l;
    }

    private boolean isValidPassword(ConnectLobby lobby, Lobby l) {
        return l.getPassword().equals(lobby.getPassword());
    }

    private Lobby getLobby(ConnectLobby lobby) {
        Lobby l = lobbyRepo.findByName(lobby.getName());
        if(l == null ) {
            throw new LobbyNotExistingException(lobby.getName());
        }
        return l;
    }

    private Semaphore getSemaphore(String lobbyName) {
        Semaphore s = lobbySemaphores.get(lobbyName);
        if(s == null){
            throw new LobbyNotExistingException(lobbyName);
        }
        return s;
    }
}
