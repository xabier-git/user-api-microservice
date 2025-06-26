package com.xabier.desafio.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
/* 
    @Autowired
    private UserRepository userRepository;

    
    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
    }*/

    @Test
    void addUser_WrongEmail() throws Exception {
        System.out.println("addUser_WrongEmail");
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
        System.out.println("addUser_WrongPassword");
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
        System.out.println("addUser_returnsCreatedUserView");
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
        System.out.println("JSON: " + json);
        mockMvc.perform(post("/users/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Rodriguez"))
                .andExpect(jsonPath("$.email").value("juan@rodriguez.org"))
                .andExpect(jsonPath("$.phones").isArray())
                .andExpect(jsonPath("$.phones[0].number").value("1234567"));
    }
/* 
    @Test
    void getUserById_returnsUserView() throws Exception {
        System.out.println("getUserById_returnsUserView");
        // Crear y guardar usuario en la BD de test
        User user = new User();
        user.setName("Javier Aguirre");
        user.setEmail("javier.aguirre.araya@gmail.com");
        user.setPassword("Almaveloz77");
        Phone phone = new Phone();
        phone.setNumber("941894839");
        phone.setCitycode("800001");
        phone.setCountrycode("56");
        user.setPhones(Collections.singletonList(phone));
        user = userRepository.save(user);
        System.out.println("Usuario guardado: " + user);
        mockMvc.perform(get("/users/api/v1/" + user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Javier Aguirre"))
                .andExpect(jsonPath("$.email").value("javier.aguirre.araya@gmail.com"))
                .andExpect(jsonPath("$.phones[0].number").value("941894839"));
    }*/

}
