package com.brunoflaviof.resistance.rest.repository.data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID ;
    private String name;

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
}
