package com.sistema.examenes.entidades;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class RegistroAsistenciaQR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private LocalDateTime fechaEscaneo;

    private String estado;

    // Getters y setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public LocalDateTime getFechaEscaneo() { return fechaEscaneo; }
    public void setFechaEscaneo(LocalDateTime fechaEscaneo) { this.fechaEscaneo = fechaEscaneo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
