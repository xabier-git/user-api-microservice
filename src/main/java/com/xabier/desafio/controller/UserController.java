package com.xabier.desafio.controller;

import com.xabier.desafio.exception.ValidationException;

import com.xabier.desafio.model.User;
import com.xabier.desafio.security.JwtUtil;
import com.xabier.desafio.services.UserService;
import com.xabier.desafio.view.LoginRequest;
import com.xabier.desafio.view.UserInput;
import com.xabier.desafio.view.UserView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JwtUtil jwtUtil;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Crear usuario
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> addUser(@Valid @RequestBody UserInput userInput)  {
        
            logger.info("Creando usuario: " + userInput.name());
            UserView userView = userService.addUser(userInput);
            logger.debug("Usuario creado exitosamente: " + userView.getName() + " con ID: " + userView.getId());
            return ResponseEntity.status(201).body(userView);
       
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable String id)  {
       
            logger.info("Intentando eliminar usuario con ID: " + id);
            userService.deleteUser(id);
            logger.debug("Usuario con ID: " + id + " eliminado exitosamente.");
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", "Usuario eliminado exitosamente");
            return new ResponseEntity<>( body, HttpStatus.OK);
       
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> updateUser(@PathVariable String id, @RequestBody UserInput userInput)  {
    
            logger.info("Intentando actualizar usuario con ID: " + id);
            UserView updatedUser = userService.updateUser(id,userInput);
            logger.debug(
                    "Usuario actualizado exitosamente: " + updatedUser.getName() + " con ID: " + updatedUser.getId());
            return ResponseEntity.ok(updatedUser);

    }

    /**
     * Obtiene todos los usuarios del repositorio y los convierte a UserView.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserView>> getAllUsers()  {
      
            logger.info("Obteniendo todos los usuarios");
            List<UserView> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> getUserById(@PathVariable String id) {
      
            logger.info("Buscando usuario por ID: " + id);
            UserView userView = userService.getUserById(id);
            return ResponseEntity.ok(userView);

    }
  

    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getToken() {

            String token = jwtUtil.generateToken("admin");
            // En este punto, deberías guardar el token en la sesión del usuario o en un lugar seguro
            // no debes exponer el token directamente en un entorno de producción
            // Aquí solo se genera un token jwt temporal para un eventual usuario "admin"
            logger.debug("Token generado para uso de las api: " + token);
            return ResponseEntity.ok(Map.of("token", token));
       
    }


}
