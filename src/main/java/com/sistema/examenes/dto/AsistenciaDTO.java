package com.sistema.examenes.dto;
/*
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

*/




import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;

public class AsistenciaDTO {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") // ← acepta zona horaria
    private OffsetDateTime fechaHora;

    private String tipo;

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

