package com.sistema.examenes.dto;

import java.time.OffsetDateTime;

public class EventoDTO {
    private String username;
    private String tipo; // ENTRADA o SALIDA

    public EventoDTO() {}

    public EventoDTO(String username, String tipo) {
        this.username = username;
        this.tipo = tipo;
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

   // esto se me agrego luefo de estar corrigiendo la hora internacionel
	public OffsetDateTime getFechaHora() {
		// TODO Auto-generated method stub
		return null;
	}
}
