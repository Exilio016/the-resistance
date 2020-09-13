package com.brunoflaviof.resistance.rest.model;

import java.io.Serializable;
import java.util.Optional;

public class ConnectLobby implements Serializable {
	private final String name;
	private final Optional<String> password;

	public ConnectLobby(String name, String password){
		this.name = name;
		this.password = Optional.ofNullable(password);
	}

	public String getName(){
		return name;
	}

	public String getPassword(){
		return password.orElse(null);
	}
}
