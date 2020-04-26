package com.brunoflaviof.game;

import com.brunoflaviof.game.constants.GameCharacter;
import com.brunoflaviof.game.exceptions.MinimumNumberOfPlayersException;
import com.brunoflaviof.game.exceptions.PlayersLimitExcededException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private int nResistance;
    private int nSpy;

    @Before
    public void CreateGame(){
        this.game = new Game();
        assertNotNull(game);
    }

    @Test
    public void shouldBeAbleToAddPlayer(){
        Player p = new Player();
        game.addPlayer(p);
        assertEquals(p, game.getPlayers().get(0));
    }

    @Test(expected = PlayersLimitExcededException.class)
    public void shouldThrowErrorAfterAdd11Player(){
        Player p = new Player();
        addPlayersToGame(11);
    }

    private void addPlayersToGame(int n){
        for(int i = 0; i < n; i++)
            game.addPlayer(new Player());
    }

    @Test(expected = MinimumNumberOfPlayersException.class)
    public void shouldThrowErrorAfterStartGameWith0Players(){
        game.start();
    }
    @Test(expected = MinimumNumberOfPlayersException.class)
    public void shouldThrowErrorAfterStartGameWith1Players(){
        addPlayersToGame(1);
        game.start();
    }
    @Test(expected = MinimumNumberOfPlayersException.class)
    public void shouldThrowErrorAfterStartGameWith2Players(){
        addPlayersToGame(2);
        game.start();
    }
    @Test(expected = MinimumNumberOfPlayersException.class)
    public void shouldThrowErrorAfterStartGameWith3Players(){
        addPlayersToGame(3);
        game.start();
    }
    @Test(expected = MinimumNumberOfPlayersException.class)
    public void shouldThrowErrorAfterStartGameWith4Players(){
        addPlayersToGame(4);
        game.start();
    }

    @Test
    public void shouldBeAbleToStartGameWithMoreThan5Players(){
        addPlayersToGame(5);
        game.start();
    }

    @Test
    public void beforeStatePlayersCharacterShouldBeNull(){
        addPlayersToGame(5);

        for(Player p : game.getPlayers())
            assertNull(p.getCharacter());
    }

    @Test
    public void afterStartPlayersShouldHaveCharacters(){
        addPlayersToGame(5);
        game.start();
        for(Player p : game.getPlayers())
            assertNotNull(p.getCharacter());
    }

    @Test
    public void afterStartPlayersShouldHaveFixedNumberOfCharacters(){
        for(int i = 5; i <= 10; i++) {
            assertPlayersCharacters(i);
            game = new Game();
        }
    }

    private void assertPlayersCharacters(int nPlayers) {
        addPlayersToGame(nPlayers);
        game.start();

        countCharacters();
        assertEquals(nSpy, GameCharacter.getNumberOfSpies(nPlayers));
        assertEquals(nResistance, GameCharacter.getNumberOfResistances(nPlayers));
    }

    private void countCharacters() {
        initializeCharactersCount();
        for(Player p : game.getPlayers()){
            if(isResistance(p))
                nResistance++;
            if(isSpy(p))
                nSpy++;
        }
    }

    private boolean isSpy(Player p) {
        return p.getCharacter() == GameCharacter.SPY;
    }

    private boolean isResistance(Player p) {
        return p.getCharacter() == GameCharacter.RESISTANCE;
    }

    private void initializeCharactersCount() {
        nSpy = 0;
        nResistance = 0;
    }

    @Test
    public void beforeStartLeaderShouldBeNull(){
        addPlayersToGame(5);
        assertNull(game.getLeader());
    }

    @Test
    public void afterStartLeaderShouldBeAPlayerOnList(){
        addPlayersToGame(5);
        game.start();
        assertTrue(gameContainsLeader());
    }

    private boolean gameContainsLeader() {
        Player leader = game.getLeader();
        boolean containsLeader = false;
        for(Player p : game.getPlayers()) {
            if (p.equals(leader)) {
                containsLeader = true;
                break;
            }
        }
        return containsLeader;
    }
}
