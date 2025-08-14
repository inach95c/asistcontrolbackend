package com.sistema.examenes.dto;

import java.time.LocalDateTime;

public class AsistenciaDTO {
    private LocalDateTime fechaHora;
    private String tipo;

    // Getters y setters
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
