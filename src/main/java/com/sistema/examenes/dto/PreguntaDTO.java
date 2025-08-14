  ////////////////////////////////////////////// txd ///////////////////////////////////

//clase creada para actualizar datos desde el backend

package com.sistema.examenes.dto;

public class PreguntaDTO {
    private Long id;
    private String estadoDeCalificacionDeLosCentros;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadoDeCalificacionDeLosCentros() {
        return estadoDeCalificacionDeLosCentros;
    }

    public void setEstadoDeCalificacionDeLosCentros(String estadoDeCalificacionDeLosCentros) {
        this.estadoDeCalificacionDeLosCentros = estadoDeCalificacionDeLosCentros;
    }
}

////////////////////////////////////////////// txd ///////////////////////////////////