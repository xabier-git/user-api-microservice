package com.xabier.desafio.controller;

import com.xabier.desafio.exception.BussinesException;
import com.xabier.desafio.exception.UserException;
import com.xabier.desafio.model.User;
import com.xabier.desafio.services.UserService;
import com.xabier.desafio.view.UserView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/api/v1")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Crear usuario
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> addUser(@RequestBody User user) throws UserException {
        UserView userView;
        try {
            logger.info("Intentando crear usuario: " + user.getName());
            userView = userService.addUser(user);
            logger.debug("Usuario creado exitosamente: " + userView.getName() + " con ID: " + userView.getId());
            return ResponseEntity.status(201).body(userView);
        } catch (BussinesException e) {
            throw new UserException("Error de Negocio :" + e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws UserException {
        try {
            logger.info("Intentando eliminar usuario con ID: " + id);
            userService.deleteUser(id);
            logger.debug("Usuario con ID: " + id + " eliminado exitosamente.");
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", "Usuario eliminado exitosamente");
            return new ResponseEntity<>( body, HttpStatus.OK);
        } catch (BussinesException e) {
            throw new UserException("Error de Negocio :" + e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> updateUser(@PathVariable Long id, @RequestBody User user) throws UserException {
        try {
            logger.info("Intentando actualizar usuario con ID: " + id);
            user.setId(id); // Asegura que el ID del path se use
            UserView updatedUser = userService.updateUser(user);
            logger.debug(
                    "Usuario actualizado exitosamente: " + updatedUser.getName() + " con ID: " + updatedUser.getId());
            return ResponseEntity.ok(updatedUser);
        } catch (BussinesException e) {
            throw new UserException("Error de Negocio :" + e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserView>> getAllUsers() throws UserException {
        try {
            logger.info("Obteniendo todos los usuarios");
            List<UserView> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (BussinesException e) {
            throw new UserException("Error de Negocio :" + e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> getUserById(@PathVariable Long id) throws UserException {
        try {
            logger.info("Buscando usuario por ID: " + id);
            UserView userView = userService.getUserById(id);
            return ResponseEntity.ok(userView);
        } catch (BussinesException e) {
            throw new UserException("Error de Negocio :" + e.getMessage(), e, HttpStatus.NOT_FOUND);
        } 
    }
  
}
