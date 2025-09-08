package com.sistema.examenes.dto;

import java.time.OffsetDateTime;

public class EventoDTO {

    private Long eventoId;
    private Long contactoId;
    private String username;
    private String tipo; // ENTRADA o SALIDA
    private OffsetDateTime fechaHora;

    public EventoDTO() {}

    public EventoDTO(Long eventoId, Long contactoId, String username, String tipo, OffsetDateTime fechaHora) {
        this.eventoId = eventoId;
        this.contactoId = contactoId;
        this.username = username;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public Long getContactoId() {
        return contactoId;
    }

    public void setContactoId(Long contactoId) {
        this.contactoId = contactoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
