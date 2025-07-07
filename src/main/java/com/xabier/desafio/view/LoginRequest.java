package com.xabier.desafio.view;

// DTO para recibir los datos
public record LoginRequest(String username, String password) {}
// Este DTO se utiliza para recibir las credenciales de inicio de sesión
// en el controlador de inicio de sesión. Es una forma sencilla de encapsular
// los datos necesarios para la autenticación sin necesidad de crear una clase
// completa. Al ser un record, proporciona automáticamente métodos como equals(),
// hashCode() y toString(), lo que facilita su uso en el contexto de Spring.