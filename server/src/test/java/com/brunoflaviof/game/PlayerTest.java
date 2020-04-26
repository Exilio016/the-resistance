package com.brunoflaviof.game;

import com.brunoflaviof.game.constants.GameCharacter;
import org.junit.Test;

import static com.brunoflaviof.game.constants.GameCharacter.RESISTANCE;
import static com.brunoflaviof.game.constants.GameCharacter.SPY;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void shouldBeAbleToCreatePlayer(){
        Player p = new Player();
    }

    @Test
    public void shouldBeAbleToAssignCharacter(){
        Player spy = new Player();
        Player resistance = new Player();
        spy.setCharacter(SPY);
        resistance.setCharacter(RESISTANCE);
        assertEquals(SPY, spy.getCharacter());
        assertEquals(RESISTANCE, resistance.getCharacter());
    }
    
}
