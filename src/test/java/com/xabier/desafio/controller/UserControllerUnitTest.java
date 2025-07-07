package com.xabier.desafio.controller;

import com.xabier.desafio.security.JwtRequestFilter;
import com.xabier.desafio.security.JwtUtil;
import com.xabier.desafio.security.SecurityConfig;
import com.xabier.desafio.services.UserService;
import com.xabier.desafio.view.UserView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * 3. Pruebas de Controlador (unitarias, solo el controlador, servicios mockeados)
 */
@WebMvcTest(UserController.class)
//@Import(SecurityConfig.class) // solo si necesitas el config mínimo
@AutoConfigureMockMvc(addFilters = false) // ⬅️ DESACTIVA filtros de seguridad como JWT

public class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void addUser_returnsCreatedUserView() throws Exception {
        UserView userView = new UserView();
        userView.setName("Juan Rodriguez");
        userView.setEmail("juan@rodriguez.org");
        Mockito.when(userService.addUser(any())).thenReturn(userView);

        String json = "{"
                + "\"name\": \"Juan Rodriguez\","
                + "\"email\": \"juan@rodriguez.org\","
                + "\"password\": \"Hun77er2\","
                + "\"phones\": [{\"number\": \"1234567\", \"citycode\": \"1\", \"contrycode\": \"57\"}]"
                + "}";

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Rodriguez"))
                .andExpect(jsonPath("$.email").value("juan@rodriguez.org"));
    }
}