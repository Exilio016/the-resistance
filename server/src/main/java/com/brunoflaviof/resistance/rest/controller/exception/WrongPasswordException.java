package com.brunoflaviof.resistance.rest.controller.exception;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException(){
        super("Wrong password!");
    }
}
