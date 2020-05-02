package com.brunoflaviof.resistance.game;

import com.brunoflaviof.resistance.game.constants.GameCharacter;

public class Player {
    private GameCharacter character;

    public void setCharacter(GameCharacter character){
        this.character = character;
    }

    public GameCharacter getCharacter() {
        return character;
    }

}
