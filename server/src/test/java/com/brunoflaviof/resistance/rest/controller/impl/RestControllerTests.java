package com.brunoflaviof.resistance.rest.controller.impl;

import com.brunoflaviof.resistance.rest.controller.exception.EmptyUserNameException;
import com.brunoflaviof.resistance.rest.controller.exception.LobbySameNameException;
import com.brunoflaviof.resistance.rest.jwt.JWTUtil;
import com.brunoflaviof.resistance.rest.model.CreateLobby;
import com.brunoflaviof.resistance.rest.model.LobbyList;
import com.brunoflaviof.resistance.rest.model.LobbyModel;
import com.brunoflaviof.resistance.rest.model.UserModel;
import com.brunoflaviof.resistance.rest.repository.LobbyRepo;
import com.brunoflaviof.resistance.rest.repository.UserRepo;
import com.brunoflaviof.resistance.rest.repository.data.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RestControllerTests {

    private RestController controller;
    private String USER_ID;

    @Autowired
    LobbyRepo lobbyRepo;
    @Autowired
    UserRepo userRepo;

    @BeforeEach
    public void before() throws NoSuchFieldException {
        lobbyRepo.deleteAll();
        userRepo.deleteAll();
        JWTUtil util = new JWTUtil();
        controller = new RestController(lobbyRepo, userRepo, util);
        util.setSecret("MySecretMySecretMySecretMySecretMySecretMySecretMySecretMySecretMySecretMySecretMySecretMySecret");
        USER_ID = "admin";
    }

    @Test
    public void shouldBeAbleToGetLobbyList(){
        String name = "name";
        controller.createLobby(USER_ID, getLobby(name, null));
        LobbyList list = controller.getLobbies();
        assertEquals(1, list.getLobbies().size());
        LobbyModel lobbyModel = list.getLobbies().get(0);
        assertEquals(name, lobbyModel.getName());
        assertFalse(lobbyModel.isProtected());
    }

    @Test
    public void shouldBeAbleToCreateLobbyWithPassword(){
        String name = "withPass";
        String password = "1234";
        Lobby l = new Lobby(USER_ID, name, password, null);
        controller.createLobby(USER_ID, getLobby(name, password));
        assertEquals(l, controller.getLobbyByName(name));
    }

    private CreateLobby getLobby(String name, String password) {
        return new CreateLobby(name, password, null);
    }

    @Test
    public void shouldBeAbleToCreateLobby(){
        String name = "withoutPass";
        Lobby l = new Lobby(USER_ID, name, null, null);
        controller.createLobby(USER_ID, getLobby(name, null));
        assertEquals(l, controller.getLobbyByName(name));
    }

    @Test
    public void shouldNotBeAbleToCreate2LobbiesWithSameName(){
        String name = "sameName";
        controller.createLobby(USER_ID, getLobby(name, null));
        assertThrows(LobbySameNameException.class, () -> {
            controller.createLobby(USER_ID, getLobby(name, null));
        });
    }

    @Test
    public void shouldBeAbleToCreateUser(){
        UserModel user = controller.createUser("Any Name");
        assertNotNull(user);
        assertNotNull(user.getUserId());
        assertNotNull(user.getToken());
        assertNotEquals("", user.getToken());
    }

    @Test
    public void userShouldNotHaveEmptyName(){
        assertThrows(EmptyUserNameException.class, () -> {
            controller.createUser("");
        });
    }

    @Test
    public void userShouldNotHaveNullName() {
        assertThrows(EmptyUserNameException.class, () -> {
            controller.createUser(null);
        });

    }

    @Test
    public void aNewUserShouldBeHaveValidToken(){
        UserModel user = controller.createUser("user");
        UserModel verifiedUser = controller.verifyToken(user.getToken());
        assertEquals(user.getUserId(), verifiedUser.getUserId());
        assertEquals(user.getToken(), verifiedUser.getToken());
    }
}