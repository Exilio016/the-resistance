package com.brunoflaviof.game.exceptions;

import com.brunoflaviof.game.constants.ErrorMessages;

public class MinimumNumberOfPlayersException extends RuntimeException {
    public MinimumNumberOfPlayersException(){
        super(ErrorMessages.MINIMUM_OF_PLAYERS_NOT_ACHIEVED);
    }

}
