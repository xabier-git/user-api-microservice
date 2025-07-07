package com.xabier.desafio.security;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${app.security.jwt.seconds-expiration}")
    private String seconds;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String generateToken(String username) {
        logger.debug("obteniendo secret-key: " + SECRET_KEY);
        logger.info("obteniendo segundos de expiración: " + seconds);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(seconds) * 1000L)) // Convert seconds to milliseconds
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token, String username) {
        String extracted = extractUsername(token);
        System.out.println("Token esValido? "+ (extracted.equals(username) && !isTokenExpired(token)));
        return extracted.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody().getExpiration();
        System.out.println("Fecha de expiración: " + expiration);
        System.out.println("Fecha actual: " + new Date());
        System.out.println("¿Está expirado? " + expiration.before(new Date()));
        return expiration.before(new Date());
    }
}