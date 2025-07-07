package com.xabier.desafio.services;

import com.xabier.desafio.model.User;
import com.xabier.desafio.services.UserService;
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

    @Test
    void addUser_validUser_returnsUserView() throws Exception {
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPassword("Hun77er2");
        UserView userView = userService.addUser(user);
        assertEquals("Test User", userView.getName());
        assertEquals("testuser@example.com", userView.getEmail());
    }
}
