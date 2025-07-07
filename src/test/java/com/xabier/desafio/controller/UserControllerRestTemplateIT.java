package com.xabier.desafio.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import com.xabier.desafio.security.JwtUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.xabier.desafio.view.UserView;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerRestTemplateIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void addUser_returnsCreatedUserView() {
        String url = "http://localhost:" + port + "/api/v1/users";
        String json = "{"
                + "\"name\": \"Xabier Wolf\","
                + "\"email\": \"xwolf@test.cl\","
                + "\"password\": \"Joamp34\","
                + "\"phones\": ["
                + "    {"
                + "        \"number\": \"1234567\","
                + "        \"citycode\": \"1\","
                + "        \"contrycode\": \"57\""
                + "    }"
                + "]"
                + "}";

        // 👇 Generar token válido con tu utilidad
        String token = jwtUtil.generateToken("xwolf@test.cl");

                // 👇 Crear headers con Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token); // <-- más limpio que usar .set("Authorization", ...)

        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<UserView> response = restTemplate.postForEntity(url, entity, UserView.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}