package com.xabier.desafio.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean isByPassed = path.equals("/api/v1/login") || path.equals("/api/v1/users/token");
        logger.info("Ruta bypass: " + path + " - " + isByPassed);
        return isByPassed;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            logger.info("Header de autorización: " + authHeader);
            logger.info("URI de la solicitud: " + request.getRequestURI());

            // Verificar si el token está presente y es válido
            // Si no está presente y las rutas que comienzan con /api/v1/users
            // Si el token no es válido o está ausente, se envía una respuesta de error 401
            // Unauthorized
            // Esto permite que las rutas de usuarios (registro, inicio de sesión) funcionen
            // sin autenticación previa
            // Si el token no es válido, se envía una respuesta de error 401 Unauthorized
            if (authHeader == null || authHeader.isEmpty() || !(authHeader.startsWith("Bearer"))
                /*&& request.getRequestURI().startsWith("/api/v1/users")*/) {
                logger.warn("Token no proporcionado");
                sendErrorResponse(response, "Token no proporcionado", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String token = authHeader.substring(7);
            logger.debug("Token extraído: " + token);
            String username = jwtUtil.extractUsername(token);
            logger.debug("Usuario extraído del token: " + username);
            if (jwtUtil.validateToken(token, username)) {
                // Aquí se podría configurar la autenticación del usuario
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                        null,
                        new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Token válido para el usuario: " + username);
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {

            logger.warn("Token JWT expirado: {}", ex.getMessage());
            sendErrorResponse(response, "Token expirado", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (JwtException | IllegalArgumentException ex) {

            logger.warn("Token JWT inválido: {}", ex.getMessage());
            sendErrorResponse(response, "Token inválido", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(
                String.format("{\"error\":\"%s\",\"message\":\"%s\"}",
                        status == 401 ? "Unauthorized" : "Forbidden",
                        message));
    }
}
