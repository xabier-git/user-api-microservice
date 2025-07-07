package com.xabier.desafio.services.impl;

import com.xabier.desafio.exception.ValidationException;
import com.xabier.desafio.model.User;
import com.xabier.desafio.repository.UserRepository;
import com.xabier.desafio.services.UserService;
import com.xabier.desafio.utils.Cons;
import com.xabier.desafio.view.PhoneView;
import com.xabier.desafio.view.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserView addUser(User user) {
        logger.info(null != user ? "Añadiendo usuario: " + user.getName() : "Usuario nulo recibido");

        // Validar formato de email
        if (!Pattern.matches(Cons.EMAIL_PATTERN, user.getEmail())) {
            logger.error("El email no cumple con el formato requerido: " + user.getEmail());
            throw new ValidationException("El email no cumple con el formato requerido.", HttpStatus.BAD_REQUEST);
        }

        // Validar email único
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.error("El email ya existe en la base de datos: " + user.getEmail());
            throw new ValidationException("El email ya existe en la base de datos.", HttpStatus.BAD_REQUEST);
        }

        // Validar formato de password
        if (!Pattern.matches(Cons.PASSWORD_PATTERN, user.getPassword())) {
            logger.error("La contraseña no cumple con el formato requerido.");
            throw new ValidationException("La contraseña no cumple con el formato requerido.", HttpStatus.BAD_REQUEST);
        }

        // Setear propiedades
        LocalDateTime now = LocalDateTime.now();
        user.setCreated(now);
        user.setModified(now);
        user.setLast_login(now);
        user.setToken(UUID.randomUUID().toString());
        user.setActive(true);
        logger.debug("Usuario antes de persistir: " + user);
        // Persistir usuario
        User savedUser = userRepository.save(user);

        // Crear UserView
        List<PhoneView> phoneViews = null;
        if (savedUser.getPhones() != null) {
            phoneViews = savedUser.getPhones().stream()
                    .map(phone -> new PhoneView(phone.getNumber()))
                    .collect(Collectors.toList());
        }
        logger.debug("usuario guardado: " + savedUser);
        return new UserView(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                phoneViews,
                savedUser.getCreated(),
                savedUser.getModified(),
                savedUser.getLast_login(),
                savedUser.getToken(),
                savedUser.isActive());
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Eliminando usuario con ID: " + id);

        if (userRepository.findById(id).isEmpty()) {
            logger.error("Usuario no encontrado con ID: " + id);
            throw new ValidationException("El usuario no existe en la base de datos.", HttpStatus.NOT_FOUND);
        } else {
            userRepository.deleteById(id);
            logger.debug("Usuario eliminado con ID: " + id);
        }
    }

    @Override
    public UserView updateUser(User user) {

        logger.info("Actualizando usuario: " + user.getName());
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ValidationException("Usuario no encontrado con ID: " + user.getId(),
                        HttpStatus.NOT_FOUND));
                        

        // Validar formato de email
        if (!Pattern.matches(Cons.EMAIL_PATTERN, user.getEmail())) {
            logger.error("El email no cumple con el formato requerido: " + user.getEmail());
            throw new ValidationException("El email no cumple con el formato requerido.", HttpStatus.BAD_REQUEST);
        }

        // No modificar el email si ya existe
        logger.debug("comparando email existente: " + existingUser.getEmail() + " con nuevo email: " + user.getEmail());
        if (!existingUser.getEmail().equals(user.getEmail())) {
            logger.warn("se comparará la preexistencia del email: " + user.getEmail());

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                logger.debug("El email pre existe en la base de datos: " + user.getEmail());
                throw new ValidationException("El email ya existe en la base de datos.", HttpStatus.BAD_REQUEST);

            } else {
                logger.info("nuevo mail en BD:" + user.getEmail());
                existingUser.setEmail(user.getEmail());
            }
        } else {
            logger.debug("El email no ha cambiado, manteniendo: " + existingUser.getEmail());
        }

        // Validar formato de password
        if (!Pattern.matches(Cons.PASSWORD_PATTERN, user.getPassword())) {
            logger.error("La contraseña no cumple con el formato requerido.");
            throw new ValidationException("La contraseña no cumple con el formato requerido.", HttpStatus.BAD_REQUEST);
        }

        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        existingUser.setModified(LocalDateTime.now());
        existingUser.setPhones(user.getPhones());
        userRepository.save(existingUser);
        logger.debug("Usuario actualizado: " + existingUser);

        return new UserView(
                existingUser.getId(),
                existingUser.getName(),
                existingUser.getEmail(),
                existingUser.getPhones() != null ? existingUser.getPhones().stream()
                        .map(phone -> new PhoneView(phone.getNumber()))
                        .collect(Collectors.toList()) : null,
                existingUser.getCreated(),
                existingUser.getModified(),
                existingUser.getLast_login(),
                existingUser.getToken(),
                existingUser.isActive());
    }

    @Override
    public List<UserView> getAllUsers() {
        logger.info("Obteniendo todos los usuarios");
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserView(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhones() != null ? user.getPhones().stream()
                                .map(phone -> new PhoneView(phone.getNumber()))
                                .collect(Collectors.toList()) : null,
                        user.getCreated(),
                        user.getModified(),
                        user.getLast_login(),
                        user.getToken(),
                        user.isActive()))
                .collect(Collectors.toList());

    }

    @Override
    public UserView getUserById(Long id) {
        logger.info("Buscando usuario por ID: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Usuario no encontrado con ID: " + id,
                        HttpStatus.NOT_FOUND));

        logger.debug("Usuario encontrado: " + user);
        List<PhoneView> phoneViews = null;
        if (user.getPhones() != null) {
            phoneViews = user.getPhones().stream()
                    .map(phone -> new PhoneView(phone.getNumber()))
                    .collect(Collectors.toList());
        }

        return new UserView(
                user.getId(),
                user.getName(),
                user.getEmail(),
                phoneViews,
                user.getCreated(),
                user.getModified(),
                user.getLast_login(),
                user.getToken(),
                user.isActive());
    }

}
