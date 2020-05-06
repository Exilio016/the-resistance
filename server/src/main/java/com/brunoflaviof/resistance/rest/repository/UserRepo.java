package com.brunoflaviof.resistance.rest.repository;

import com.brunoflaviof.resistance.rest.repository.data.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {
    User getByUserID(UUID id);
    List<User> getByName(String name);
}
