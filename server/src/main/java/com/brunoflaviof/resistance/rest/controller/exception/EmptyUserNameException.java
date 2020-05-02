package com.brunoflaviof.resistance.rest.controller.exception;

public class EmptyUserNameException extends RuntimeException {

    public EmptyUserNameException(){
        super("User name should not be empty!");
    }
}
