package com.brunoflaviof.resistance.rest.controller.exception;

public class LobbyNotExistingException extends RuntimeException {

	public LobbyNotExistingException(String name) {
		super("The lobby " + name + " does not exist!");
	}
}
