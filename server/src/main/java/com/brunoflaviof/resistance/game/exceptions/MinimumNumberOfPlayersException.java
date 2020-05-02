package com.brunoflaviof.resistance.game.exceptions;

import com.brunoflaviof.resistance.game.constants.ErrorMessages;

public class MinimumNumberOfPlayersException extends RuntimeException {
    public MinimumNumberOfPlayersException(){
        super(ErrorMessages.MINIMUM_OF_PLAYERS_NOT_ACHIEVED);
    }

}
