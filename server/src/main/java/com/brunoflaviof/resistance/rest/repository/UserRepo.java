package com.brunoflaviof.resistance.rest.repository;

import com.brunoflaviof.resistance.rest.repository.data.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepo {
    Map<UUID, User> users = new HashMap<>();

    public User createUser(String name){
        User u = new User(name);
        users.put(u.getUserID(), u);
        return u;
    }

    public User getUser(UUID userID){
        return users.get(userID);
    }

    public void removeUser(UUID userID){
        users.remove(userID);
    }
}
