package com.brunoflaviof.resistance.rest.repository.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Lobby {
    private String adminId;
    @Id
    private String name;
    private String password;
    private String meetingURL;

    protected Lobby(){

    }

    public Lobby(String adminId, String name, String password, String meetingURL) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
        this.meetingURL = meetingURL;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getMeetingURL() {
        return meetingURL;
    }


    public String getName() {
        return name;
    }

    public boolean hasPassword(){
        return password != null;
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
