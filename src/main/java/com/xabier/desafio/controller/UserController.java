package com.xabier.desafio.controller;

import com.xabier.desafio.exception.ValidationException;

import com.xabier.desafio.model.User;
import com.xabier.desafio.services.UserService;
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

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Crear usuario
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> addUser(@Valid @RequestBody UserInput userInput,HttpServletRequest request)  {
            // Validado en el filter de seguridad JWT   
            String authHeader = request.getHeader("Authorization");
            String token = authHeader.substring(7);
            logger.info("Intentando crear usuario: " + userInput.name());
            UserView userView = userService.addUser(userInput,token);
            logger.debug("Usuario creado exitosamente: " + userView.getName() + " con ID: " + userView.getId());
            return ResponseEntity.status(201).body(userView);
       
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable Long id)  {
       
            logger.info("Intentando eliminar usuario con ID: " + id);
            userService.deleteUser(id);
            logger.debug("Usuario con ID: " + id + " eliminado exitosamente.");
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", "Usuario eliminado exitosamente");
            return new ResponseEntity<>( body, HttpStatus.OK);
       
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> updateUser(@PathVariable Long id, @RequestBody UserInput userInput)  {
    
            logger.info("Intentando actualizar usuario con ID: " + id);
            UserView updatedUser = userService.updateUser(id,userInput);
            logger.debug(
                    "Usuario actualizado exitosamente: " + updatedUser.getName() + " con ID: " + updatedUser.getId());
            return ResponseEntity.ok(updatedUser);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserView>> getAllUsers()  {
      
            logger.info("Obteniendo todos los usuarios");
            List<UserView> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
 
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> getUserById(@PathVariable Long id) {
      
            logger.info("Buscando usuario por ID: " + id);
            UserView userView = userService.getUserById(id);
            return ResponseEntity.ok(userView);

    }
  
}
