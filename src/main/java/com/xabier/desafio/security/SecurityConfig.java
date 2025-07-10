package com.xabier.desafio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Deshabilitar CSRF para simplificar el ejemplo
        // En producción, deberías manejar CSRF de manera adecuada
        // o habilitarlo si es necesario.
        // Aquí se permite el acceso a /api/login sin autenticación,
        // y se requiere autenticación para cualquier otra solicitud.
        System.out.println("filterChain(): Configurando la cadena de seguridad");
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/login").permitAll()
                                .requestMatchers("/api/v1/users/token").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}