package com.brunoflaviof.resistance.rest.repository;

import com.brunoflaviof.resistance.rest.repository.data.Lobby;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface LobbyRepo extends CrudRepository<Lobby, String>{
    Lobby findByName(String name);
    List<Lobby> findByAdminId(String adminId);
}
