package com.brunoflaviof.game.exceptions;

import com.brunoflaviof.game.constants.ErrorMessages;

public class PlayersLimitExcededException extends RuntimeException {
    public PlayersLimitExcededException(){
        super(ErrorMessages.PLAYER_LIMIT_EXCEED);
    }
}
