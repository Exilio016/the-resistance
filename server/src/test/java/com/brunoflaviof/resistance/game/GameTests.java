package com.brunoflaviof.resistance.game;

import com.brunoflaviof.resistance.game.constants.GameCharacter;
import com.brunoflaviof.resistance.game.exceptions.MinimumNumberOfPlayersException;
import com.brunoflaviof.resistance.game.exceptions.PlayersLimitExcededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameTests {

    private Game game;
    private int nResistance;
    private int nSpy;

    @BeforeEach
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

    @Test
    public void shouldThrowErrorAfterAdd11Player(){
        assertThrows(PlayersLimitExcededException.class, () -> {
            Player p = new Player();
            addPlayersToGame(11);
        });
    }

    private void addPlayersToGame(int n){
        for(int i = 0; i < n; i++)
            game.addPlayer(new Player());
    }

    @Test
    public void shouldThrowErrorAfterStartGameWith0Players(){
        assertThrows(MinimumNumberOfPlayersException.class, () -> {
            game.start();
        });
    }
    private void assertNumberOfPlayersException(int numberOfPlayers) {
        assertThrows(MinimumNumberOfPlayersException.class, () -> {
            addPlayersToGame(numberOfPlayers);
            game.start();
        });
    }
    @Test
    public void shouldThrowErrorAfterStartGameWith1Players(){
        assertNumberOfPlayersException(1);

    }

    @Test
    public void shouldThrowErrorAfterStartGameWith2Players(){
        assertNumberOfPlayersException(2);

    }

    @Test
    public void shouldThrowErrorAfterStartGameWith3Players(){
        assertNumberOfPlayersException(3);
    }
    @Test
    public void shouldThrowErrorAfterStartGameWith4Players(){
        assertNumberOfPlayersException(4);
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
