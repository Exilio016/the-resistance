package com.brunoflaviof.resistance.game.exceptions;

import com.brunoflaviof.resistance.game.constants.ErrorMessages;

public class PlayersLimitExcededException extends RuntimeException {
    public PlayersLimitExcededException(){
        super(ErrorMessages.PLAYER_LIMIT_EXCEED);
    }
}
