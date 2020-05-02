package com.brunoflaviof.resistance.rest.controller.impl;

import com.brunoflaviof.resistance.rest.controller.exception.EmptyUserNameException;
import com.brunoflaviof.resistance.rest.controller.exception.LobbySameNameException;
import com.brunoflaviof.resistance.rest.jwt.JWTUtil;
import com.brunoflaviof.resistance.rest.model.CreateLobby;
import com.brunoflaviof.resistance.rest.model.LobbyModel;
import com.brunoflaviof.resistance.rest.model.UserModel;
import com.brunoflaviof.resistance.rest.repository.LobbyRepo;
import com.brunoflaviof.resistance.rest.repository.UserRepo;
import com.brunoflaviof.resistance.rest.repository.data.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RestControllerTests {

    @Spy
    private LobbyRepo mockedLobbies;
    @Spy
    private UserRepo mockedUsers;


    @InjectMocks
    private RestController controller = new RestController(mockedLobbies, mockedUsers);

    List lobbyList = mock(List.class);
    private String USER_ID;

    @BeforeEach
    public void mockList() throws NoSuchFieldException {
        lobbyList.add(new LobbyModel("test1", false));
        lobbyList.add(new LobbyModel("test2", false));
        when(mockedLobbies.getAllModel()).thenReturn(lobbyList);
        JWTUtil.setSecret("MySecret");
        USER_ID = "admin";
    }

    @Test
    public void shouldBeAbleToGetLobbyList(){
        assertEquals(lobbyList, controller.getLobbies().getLobbies());
    }

    @Test
    public void shouldBeAbleToCreateLobbyWithPassword(){
        String name = "withPass";
        String password = "1234";
        Lobby l = new Lobby(USER_ID, name, password);
        controller.createLobby(new CreateLobby(USER_ID, name, password));
        assertEquals(l, controller.getLobbyByName(name));
    }

    @Test
    public void shouldBeAbleToCreateLobby(){
        String name = "withoutPass";
        Lobby l = new Lobby(USER_ID, name, null);
        controller.createLobby(new CreateLobby(USER_ID, name, null));
        assertEquals(l, controller.getLobbyByName(name));
    }

    @Test
    public void shouldNotBeAbleToCreate2LobbiesWithSameName(){
        String name = "sameName";
        controller.createLobby(new CreateLobby(USER_ID, name, null));
        assertThrows(LobbySameNameException.class, () -> {
            controller.createLobby(new CreateLobby(USER_ID, name, null));
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