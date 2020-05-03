package com.brunoflaviof.resistance.rest.repository;

import com.brunoflaviof.resistance.rest.controller.exception.LobbySameNameException;
import com.brunoflaviof.resistance.rest.model.LobbyModel;
import com.brunoflaviof.resistance.rest.repository.data.Lobby;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LobbyRepo {
    Map<String, Lobby> lobbies = new HashMap<>();

    public List<Lobby> getAll() {
        return new ArrayList<>(lobbies.values());
    }

    public List<LobbyModel> getAllModel(){
        List<LobbyModel> list = new ArrayList<>();
        for(Lobby l : lobbies.values()){
            list.add(new LobbyModel(l.getName(), l.hasPassword()));
        }
        return list;
    }

    public Lobby getLobby(String name){
        return lobbies.get(name);
    }

    public void addLobby(Lobby l) {
        lobbies.put(l.getName(), l);
    }

    public void createLobby(String adminId, String name, Optional<String> password, Optional<String> meetingURL) {
        if(lobbies.containsKey(name))
            throw new LobbySameNameException();
        Lobby l = new Lobby(adminId, name, password.orElse(null));
        meetingURL.ifPresent(l::setMeetingURL);
        lobbies.put(name, l);
    }
}
