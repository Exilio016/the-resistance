package com.brunoflaviof.resistance.rest.controller;

import com.brunoflaviof.resistance.rest.model.UserModel;
import org.springframework.web.bind.annotation.*;

@RestController
public interface UserController {

    @GetMapping("/user")
    public UserModel createUser(@RequestParam("name") String displayName);

}
