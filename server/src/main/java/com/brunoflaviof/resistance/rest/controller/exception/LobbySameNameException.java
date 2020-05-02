package com.brunoflaviof.resistance.rest.controller.exception;

public class LobbySameNameException extends RuntimeException{

    public LobbySameNameException(){
        super("A lobby with same name already exists! Please chose another one");
    }
}
