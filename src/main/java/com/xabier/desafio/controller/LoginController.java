package com.xabier.desafio.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.xabier.desafio.exception.LoginException;
import com.xabier.desafio.security.JwtUtil;
import com.xabier.desafio.view.LoginRequest;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {

        logger.info("Intentando iniciar sesión para el usuario: " + loginRequest.username());

        if (loginRequest.username() == null || loginRequest.password() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Credenciales inválidas"));
        }

        // Autenticación (¡en producción usa PasswordEncoder!)
        if ("admin".equals(loginRequest.username()) && "1234".equals(loginRequest.password())) {

            String token = jwtUtil.generateToken(loginRequest.username());
            // En este punto, deberías guardar el token en la sesión del usuario o en un lugar seguro
            // no debes exponer el token directamente en un entorno de producción
            // Aquí solo se muestra como ejemplo
            logger.debug("Token generado para el usuario: " + token);
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }
    }
}
