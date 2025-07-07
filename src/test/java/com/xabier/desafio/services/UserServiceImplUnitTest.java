package com.xabier.desafio.services;

import com.xabier.desafio.exception.ValidationException;
import com.xabier.desafio.model.User;
import com.xabier.desafio.repository.UserRepository;
import com.xabier.desafio.services.impl.UserServiceImpl;
import com.xabier.desafio.view.UserView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

/*
 * 1. Pruebas Unitarias (p. ej. lógica pura, sin Spring)
 * 
 */
public class UserServiceImplUnitTest {

    @Test
    void addUser_invalidEmail_throwsException() {
        UserRepository userRepository = mock(UserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        User user = new User();
        user.setEmail("bademail");
        user.setPassword("Hun77er2");

        Exception ex = assertThrows(ValidationException.class, () -> userService.addUser(user));
        assertTrue(ex.getMessage().contains("El email no cumple con el formato requerido."));
    }
}
