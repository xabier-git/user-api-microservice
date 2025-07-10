package com.xabier.desafio.view;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInput(
    @NotBlank(message = "El nombre es obligatorio")
    String name,
    @Email(message = "El email no cumple con el formato requerido")
    String email,
    //@Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password,
    List<PhoneInput> phones
    ) {
} 