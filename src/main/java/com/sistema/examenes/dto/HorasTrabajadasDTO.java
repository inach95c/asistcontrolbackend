package com.sistema.examenes.dto;

public class HorasTrabajadasDTO {

    private Long usuarioId;
    private String nombreUsuario;
    private Double horasNormales;
    private Double horasExtras;

    public HorasTrabajadasDTO() {
    }

    public HorasTrabajadasDTO(Long usuarioId, String nombreUsuario, Double horasNormales, Double horasExtras) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.horasNormales = horasNormales;
        this.horasExtras = horasExtras;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Double getHorasNormales() {
        return horasNormales;
    }

    public void setHorasNormales(Double horasNormales) {
        this.horasNormales = horasNormales;
    }

    public Double getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Double horasExtras) {
        this.horasExtras = horasExtras;
    }
}
