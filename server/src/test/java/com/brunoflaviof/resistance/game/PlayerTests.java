package com.brunoflaviof.resistance.game;

import com.brunoflaviof.resistance.game.constants.GameCharacter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlayerTests {

    @Test
    public void shouldBeAbleToCreatePlayer(){
        Player p = new Player();
    }

    @Test
    public void shouldBeAbleToAssignCharacter(){
        Player spy = new Player();
        Player resistance = new Player();
        spy.setCharacter(GameCharacter.SPY);
        resistance.setCharacter(GameCharacter.RESISTANCE);
        assertEquals(GameCharacter.SPY, spy.getCharacter());
        assertEquals(GameCharacter.RESISTANCE, resistance.getCharacter());
    }
    
}
