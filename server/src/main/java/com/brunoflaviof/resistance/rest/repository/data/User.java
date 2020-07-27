package com.brunoflaviof.resistance.rest.repository.data;
import jdk.internal.vm.annotation.Stable;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID ;
    private String name;

    @ManyToOne
    @JoinColumn(name = "lobby")
    private Lobby lobby;

    protected User(){

    }

    public User(String name) {
        this.name = name;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
