package com.xabier.desafio.services;

import com.xabier.desafio.view.UserInput;
import com.xabier.desafio.view.UserView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/*
 * 2. Pruebas de Servicio (con Spring, pero sin web)
 */

@SpringBootTest
public class UserServiceImplIT {

    @Autowired
    private UserService userService;

    /**
     * @throws Exception
     */
    @Test
    void addUser_validUser_returnsUserView() throws Exception {
        UserInput userInput = new UserInput("Test User", "testuser@example.com", "Hun77er2", null);
        /*user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPassword("Hun77er2");*/
        UserView userView = userService.addUser(userInput);
        assertEquals("Test User", userView.getName());
        assertEquals("testuser@example.com", userView.getEmail());
    }
}
