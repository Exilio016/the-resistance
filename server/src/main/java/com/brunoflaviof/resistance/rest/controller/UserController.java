package com.brunoflaviof.resistance.rest.controller;

import com.brunoflaviof.resistance.rest.model.UserModel;
import org.springframework.web.bind.annotation.*;

public interface UserController {

    @GetMapping("/user")
    UserModel createUser(@RequestParam("name") String displayName);

}
