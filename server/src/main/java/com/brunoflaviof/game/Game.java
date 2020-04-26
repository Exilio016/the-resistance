package com.brunoflaviof.game;

import com.brunoflaviof.game.constants.GameCharacter;
import com.brunoflaviof.game.exceptions.MinimumNumberOfPlayersException;
import com.brunoflaviof.game.exceptions.PlayersLimitExcededException;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int PLAYERS_MAX_LIMIT = 10;
    private static final int PLAYERS_MIN_LIMIT = 5;
    private List<Player> players;
    private Player leader;


    public Game() {
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        if (players.size() < PLAYERS_MAX_LIMIT)
            players.add(player);
        else
            throw new PlayersLimitExcededException();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void start() {
        if (players.size() < PLAYERS_MIN_LIMIT)
            throw new MinimumNumberOfPlayersException();

        assignPlayerCharacters();
        assignLeader();
    }

    private void assignLeader() {
        int leaderPosition = ((int) (Math.random() * 10)) % players.size( );
        leader = players.get(leaderPosition);
    }

    private void assignPlayerCharacters() {
        int nPlayers = players.size();
        int maxResistance = GameCharacter.getNumberOfResistances(nPlayers);
        int maxSpies = GameCharacter.getNumberOfSpies(nPlayers);
        double probability = ((double)maxResistance )/ nPlayers ;
        for(Player player : players){
            if((maxResistance == 0 || Math.random() > probability) && maxSpies > 0){
                player.setCharacter(GameCharacter.SPY);
                maxSpies--;
            }
            else{
                player.setCharacter(GameCharacter.RESISTANCE);
                maxResistance--;
            }
        }
    }

    public Player getLeader() {
        return leader;
    }
}