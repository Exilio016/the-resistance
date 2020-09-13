package com.brunoflaviof.resistance.rest.repository.data;

import com.brunoflaviof.resistance.game.Game;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

@Entity
public class Lobby {
    private String adminId;
    @Id
    private String name;
    private String password;
    private Game game;

    @OneToMany(mappedBy = "lobby", fetch = FetchType.EAGER)
    private List<User> users;

    protected Lobby(){

    }

    public Lobby(String adminId, String name, String password) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
        this.users = new ArrayList<>();
    }

    public String getAdminId() {
        return adminId;
    }

    public String getName() {
        return name;
    }

    public boolean hasPassword(){
        return password != null;
    }

    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers( List<User> users){
        this.users = users;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        Lobby l;
        if(obj instanceof Lobby){
            l = (Lobby) obj;
        }
        else {
            return false;
        }

        if(!this.adminId.equals(l.adminId))
            return false;
        if(!this.name.equals(l.name))
            return false;
        if(this.password != null)
            return this.password.equals(l.password);
        return l.password == null;
    }
}
