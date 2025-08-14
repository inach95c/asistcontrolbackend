package com.sistema.examenes.servicios.impl;


import com.sistema.examenes.entidades.QrToken;
import com.sistema.examenes.repositorios.QrTokenRepository;
import com.sistema.examenes.servicios.QrTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class QrTokenServiceImpl implements QrTokenService {
	
	private final String secretKey = "H7f9j2Kx@wN0!cQpL#Vz83mYt$AaB1Er"; // 👈 Usá tu clave real, y mantenla segura


    @Autowired
    private QrTokenRepository tokenRepository;

    @Override
    public QrToken generarToken(String tipoEvento, Duration duracion) {
    	
        String token = UUID.randomUUID().toString();
        LocalDateTime expiracion = LocalDateTime.now().plus(duracion);

        QrToken qrToken = new QrToken();
        qrToken.setToken(token);
        qrToken.setTipoEvento(tipoEvento.toUpperCase());
        qrToken.setExpiracion(expiracion);
        qrToken.setUsado(false);

        return tokenRepository.save(qrToken);
    }

    @Override
    public Optional<QrToken> validarToken(String token) {
        return tokenRepository.findById(token)
                .filter(qr -> !qr.isUsado() && LocalDateTime.now().isBefore(qr.getExpiracion()));
    }

    @Override
    public void marcarComoUsado(String token) {
        tokenRepository.findById(token).ifPresent(qr -> {
            qr.setUsado(true);
            tokenRepository.save(qr);
        });
    }
    
    @Scheduled(fixedRate = 600000) // Cada 10 minutos (600,000 ms)
    public void eliminarTokensExpirados() {
        List<QrToken> tokens = tokenRepository.findAll();

        long eliminados = tokens.stream()
            .filter(qr -> qr.getExpiracion().isBefore(LocalDateTime.now()))
            .map(qr -> {
                tokenRepository.delete(qr);
                return 1;
            }).count();

        System.out.println("🧹 Tokens expirados eliminados: " + eliminados);
    }
    
    
    // PARA GENERAR TOKEN, EL USUARIO SOLO SCANERARÁ SIN NECESIDAD DE AUTENTICARSE SOLUCION (2)
    private final String SECRET_KEY = "super-clave-secreta-para-qr"; // Guardala segura

    public String generarJwt(String tipoEvento, String username, Duration duracion) {
        Instant ahora = Instant.now();
        Instant expiracion = ahora.plus(duracion);

        return Jwts.builder()
            .setSubject(username)
            .claim("tipoEvento", tipoEvento)
            .setIssuedAt(Date.from(ahora))
            .setExpiration(Date.from(expiracion))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }
    
    
    // PARA GENERAR TOKEN, EL USUARIO SOLO SCANERARÁ SIN NECESIDAD DE AUTENTICARSE SOLUCION (3)
    @Override
    public String validarJwtYExtraerUsuario(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();

            return claims.getSubject(); // normalmente el username está aquí
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado");
        }
    }
    
    
}
