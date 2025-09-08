package com.sistema.examenes.entidades;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime fechaHora; // UTC

    private String tipo; // "ENTRADA", "SALIDA", "CHECKIN"

    private String origen; // "web", "mobile", "kiosk", "qr"

    @Column(length = 20)
    private String estado; // "NORMAL", "TARDÍA", etc.

    @Column(length = 20)
    private String canal; // "QR_DINAMICO", "QR_ESTATICO", "WHATSAPP", "EMAIL", "MANUAL"

    @Column(length = 100)
    private String tokenEscaneado; // opcional, para trazabilidad

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "contacto_id")
    private Contacto contacto;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public Asistencia() {}
    
    

    public Asistencia(OffsetDateTime fechaHora, String tipo, Usuario usuario) {
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

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

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getTokenEscaneado() {
        return tokenEscaneado;
    }

    public void setTokenEscaneado(String tokenEscaneado) {
        this.tokenEscaneado = tokenEscaneado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    // Utilidad para distinguir el tipo de asistente
    public String getTipoAsistente() {
        if (usuario != null) return "USUARIO";
        if (contacto != null) return "CONTACTO";
        return "DESCONOCIDO";
    }

    public boolean esEscaneoQr() {
        return "QR_DINAMICO".equalsIgnoreCase(canal) || "QR_ESTATICO".equalsIgnoreCase(canal);
    }
}
