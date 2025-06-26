package com.xabier.desafio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xabier.desafio.model.Phone;
import com.xabier.desafio.model.User;
import com.xabier.desafio.services.UserService;
import com.xabier.desafio.view.UserView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    //private UserService userService;

    //@Autowired
    //private ObjectMapper objectMapper;

    @Test
    void addUser_WrongEmail() throws Exception {


        String json = "{"
                + "\"name\": \"Juan Rodriguez\","
                + "\"email\": \" juan@rodriguez.org \","
                + "\"password\": \"hunter2\","
                + "\"phones\": ["
                + "    {"
                + "        \"number\": \"1234567\","
                + "        \"citycode\": \"1\","
                + "        \"contrycode\": \"57\""
                + "    }"
                + "]"
                + "}";

        mockMvc.perform(post("/users/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensaje").value("Error de Negocio :El email no cumple con el formato requerido."));
                
    }

        @Test
    void addUser_WrongPassword() throws Exception {


        String json = "{"
                + "\"name\": \"Juan Rodriguez\","
                + "\"email\": \"juan@rodriguez.org\","
                + "\"password\": \"hunter2\","
                + "\"phones\": ["
                + "    {"
                + "        \"number\": \"1234567\","
                + "        \"citycode\": \"1\","
                + "        \"contrycode\": \"57\""
                + "    }"
                + "]"
                + "}";

        mockMvc.perform(post("/users/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isInternalServerError())                     
                .andExpect(jsonPath("$.mensaje").value("Error de Negocio :La contraseña no cumple con el formato requerido."));
                
    }

    @Test
    void addUser_returnsCreatedUserView() throws Exception {

        String json = "{"
                + "\"name\": \"Juan Rodriguez\","
                + "\"email\": \"juan@rodriguez.org\","
                + "\"password\": \"Hun77er2\","
                + "\"phones\": ["
                + "    {"
                + "        \"number\": \"1234567\","
                + "        \"citycode\": \"1\","
                + "        \"contrycode\": \"57\""
                + "    }"
                + "]"
                + "}";

        mockMvc.perform(post("/users/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Rodriguez"))
                .andExpect(jsonPath("$.email").value("juan@rodriguez.org"));
    }

}
