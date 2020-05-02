package com.brunoflaviof.resistance.rest.repository.data;

public class Lobby {

    private final String adminId;
    private final String name;
    private final String password;
    private String meetingURL;

    public Lobby(String adminId, String name, String password) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getMeetingURL() {
        return meetingURL;
    }

    public void setMeetingURL(String meetingURL) {
        this.meetingURL = meetingURL;
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
