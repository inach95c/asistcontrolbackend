package com.sistema.examenes.servicios;

import java.time.Duration;

public interface QrTokenEventoService {
    String generarToken(Long eventoId, Duration duracion);
    boolean validarToken(String token, Long eventoId);
    void marcarComoUsado(String token);
    void eliminarExpirados();
}
