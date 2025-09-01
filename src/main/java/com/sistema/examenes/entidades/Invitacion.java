package com.sistema.examenes.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invitaciones")
public class Invitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoQR; // UUID o hash único para escaneo

    @ManyToOne
    @JoinColumn(name = "contacto_id", nullable = false)
    private Contacto contacto;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    private LocalDateTime fechaGeneracion;

    private Boolean confirmada = false;

    @Column(length = 50)
    private String canal; // Ej: "email", "whatsapp", "manual"

    @PrePersist
    protected void onCreate() {
        this.fechaGeneracion = LocalDateTime.now();
    }

    // Constructor vacío
    public Invitacion() {}

    // Constructor útil
    public Invitacion(String codigoQR, Contacto contacto, Evento evento, String canal) {
        this.codigoQR = codigoQR;
        this.contacto = contacto;
        this.evento = evento;
        this.canal = canal;
        this.confirmada = false;
        this.fechaGeneracion = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Boolean getConfirmada() {
        return confirmada;
    }

    public void setConfirmada(Boolean confirmada) {
        this.confirmada = confirmada;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }
}

