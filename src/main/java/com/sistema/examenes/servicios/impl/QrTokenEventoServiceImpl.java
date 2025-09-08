package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.servicios.QrTokenEventoService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Service
public class QrTokenEventoServiceImpl implements QrTokenEventoService {

    @Override
    public String generarTokenParaEvento(Long eventoId) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC).withSecond(0).withNano(0);
        String raw = eventoId + "-" + now.toString();
        return hash(raw);
    }

    @Override
    public boolean validarToken(String token, Long eventoId) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC).withSecond(0).withNano(0);
        String expected = hash(eventoId + "-" + now.toString());
        return expected.equals(token);
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(encoded);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash QR", e);
        }
    }
}
