package com.brunoflaviof.game;

import com.brunoflaviof.game.constants.GameCharacter;

import java.util.List;

public class Player {
    private GameCharacter character;

    public void setCharacter(GameCharacter character){
        this.character = character;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public void discoverSpy(Player spy2) {

    }

    public List<Player> getKnownSpies() {
        return null;
    }
}
