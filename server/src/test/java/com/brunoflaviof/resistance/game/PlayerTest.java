package com.brunoflaviof.resistance.game;

import com.brunoflaviof.resistance.game.constants.GameCharacter;
import org.junit.Assert;
import org.junit.Test;

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
        spy.setCharacter(GameCharacter.SPY);
        resistance.setCharacter(GameCharacter.RESISTANCE);
        Assert.assertEquals(GameCharacter.SPY, spy.getCharacter());
        Assert.assertEquals(GameCharacter.RESISTANCE, resistance.getCharacter());
    }
    
}
