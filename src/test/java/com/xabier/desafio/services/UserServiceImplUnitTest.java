package com.xabier.desafio.services;

import com.xabier.desafio.exception.ValidationException;
import com.xabier.desafio.model.User;
import com.xabier.desafio.repository.UserRepository;
import com.xabier.desafio.security.JwtUtil;
import com.xabier.desafio.services.impl.UserServiceImpl;
import com.xabier.desafio.view.PhoneInput;
import com.xabier.desafio.view.UserInput;
import com.xabier.desafio.view.UserView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
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
    void addUser_invalidPassword_throwsException() {
        UserRepository userRepository = mock(UserRepository.class);
        JwtUtil jwtUtil = mock(JwtUtil.class); 
        UserService userService = new UserServiceImpl(userRepository, jwtUtil);

        UserInput userInput = new UserInput("Juan", "juan@example.com", "badpassword", null);
 
        Exception ex = assertThrows(ValidationException.class, () -> userService.addUser(userInput));
        assertTrue(ex.getMessage().contains("La contraseña no cumple con el formato requerido."));
    }
}
