package com.xabier.desafio.repository;

import com.xabier.desafio.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Puedes agregar métodos personalizados aquí si lo necesitas
    public Optional<User> findByEmail(String email);
}
