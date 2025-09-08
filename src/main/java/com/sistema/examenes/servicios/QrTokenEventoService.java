package com.sistema.examenes.servicios;

public interface QrTokenEventoService {
    String generarTokenParaEvento(Long eventoId);
    boolean validarToken(String token, Long eventoId);
}
