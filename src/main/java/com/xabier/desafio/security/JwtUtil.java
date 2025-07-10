package com.xabier.desafio.security;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
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

        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + Long.parseLong(seconds) * 1000L;

        System.out.println("Hora actual (UTC): " + new Date(currentTime));
        System.out.println("Hora de expiración (UTC): " + new Date(expirationTime));

        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(Long.parseLong(seconds));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration)) 
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token)
                .getBody().getSubject();
    }
   
    public boolean validateToken(String token, String username) {
        String extracted = extractUsername(token);
        
        boolean isTokenValid = extracted.equals(username) && !isTokenExpired(token);
        logger.debug("Token {} válido: ", isTokenValid? "Es" : "No");
        return isTokenValid;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody().getExpiration();
        logger.debug("Fecha de expiración: " + expiration);
        logger.debug("Fecha actual: " + new Date());
        logger.debug("¿Está expirado? " + expiration.before(new Date()));
        return expiration.before(new Date());
    }
}