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
import com.brunoflaviof.resistance.rest.repository.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

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
        userRepo.deleteAll();
        lobbyRepo.deleteAll();
        JWTUtil util = new JWTUtil("zqLob7ZAVYar8tfVa5qLPJKRBgxLhQhh8BLZFBth8SLmiZ7vCN4iuAeyMSFPWb95DPqj22TFJXn4VeeT6e7mTiRBA4Rao82ZgGDdptMjm5DzCmQy2q7cJ3vtfjaxyiji");
        controller = new RestController(lobbyRepo, userRepo, util);
        USER_ID = controller.createUser("test user").getUserId().toString();
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
        Lobby l = createLobby(name, password);
        assertEquals(l, controller.getLobbyByName(name));
    }

    private CreateLobby getLobby(String name, String password) {
        return new CreateLobby(name, password, null);
    }

    @Test
    public void shouldBeAbleToCreateLobby(){
        String name = "withoutPass";
        Lobby l = createLobby(name, null);
        assertEquals(l, controller.getLobbyByName(name));
    }

    private Lobby createLobby(String name, String password) {
        controller.createLobby(USER_ID, getLobby(name, password));
        return lobbyRepo.findByName(name);
    }

    @Test
    public void shouldBeAdminAfterCreateLobby(){
        Lobby l = createLobby(getRandomName(), null);
        assertEquals(USER_ID, l.getAdminId());
    }

    private String getRandomName() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void afterCreateLobbyShouldBeConnected(){
        Lobby l = createLobby("adminConnection", null);
        List<User> users = l.getUsers();
        assertEquals(1, users.size());
        assertEquals(USER_ID, users.get(0).getUserID().toString());
    }

    @Test
    public void shouldNotBeAbleToCreate2LobbiesWithSameName(){
        String name = "sameName";
        controller.createLobby(USER_ID, getLobby(name, null));
        assertThrows(LobbySameNameException.class, () -> controller.createLobby(USER_ID, getLobby(name, null)));
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
        assertThrows(EmptyUserNameException.class, () -> controller.createUser(""));
    }

    @Test
    public void userShouldNotHaveNullName() {
        assertThrows(EmptyUserNameException.class, () -> controller.createUser(null));

    }

    @Test
    public void aNewUserShouldBeHaveValidToken(){
        UserModel user = controller.createUser("user");
        UserModel verifiedUser = controller.verifyToken(user.getToken());
        assertEquals(user.getUserId(), verifiedUser.getUserId());
        assertEquals(user.getToken(), verifiedUser.getToken());
    }
}