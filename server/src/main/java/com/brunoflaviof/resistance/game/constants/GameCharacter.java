package com.brunoflaviof.resistance.game.constants;

import com.brunoflaviof.resistance.game.exceptions.MinimumNumberOfPlayersException;
import com.brunoflaviof.resistance.game.exceptions.PlayersLimitExcededException;

public enum GameCharacter {
    SPY,
    RESISTANCE;

   private static final int[] ASSIGN_MATRIX = new int[] {3, 4, 4, 5, 6, 6};

    @Override
    public String toString() {
        switch (this){
            case SPY:
                return "SPY";
            case RESISTANCE:
                return "RESISTANCE";
        }
        return super.toString();
    }

    public static int getNumberOfSpies(int nPlayers){
        verifyNumberOfPlayers(nPlayers);
        return (nPlayers - ASSIGN_MATRIX[nPlayers - 5]);
    }
    public static int getNumberOfResistances(int nPlayers){
        verifyNumberOfPlayers(nPlayers);
        return (ASSIGN_MATRIX[nPlayers - 5]);
    }

    private static void verifyNumberOfPlayers(int nPlayers){
        if(nPlayers < 5)
            throw new MinimumNumberOfPlayersException();
        if(nPlayers > 10)
            throw new PlayersLimitExcededException();
    }

}
