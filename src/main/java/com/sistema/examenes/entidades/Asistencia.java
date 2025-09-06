
/*
package com.sistema.examenes.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    private String tipo; // "ENTRADA" o "SALIDA"
    
    private String origen; // Ej: "kiosk", "web", "mobile"


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Asistencia() {}

    public Asistencia(LocalDateTime fechaHora, String tipo, Usuario usuario) {
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.usuario = usuario;
    }
    
  

	@Column(length = 20)
    private String estado; // NORMAL o TARDÍA


    // Getters y setters
	
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }

	
    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}
   
    
} */



package com.sistema.examenes.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // private LocalDateTime fechaHora;
    
    private OffsetDateTime fechaHora; // ← antes era LocalDateTime

    private String tipo; // "ENTRADA" o "SALIDA"

    private String origen; // Ej: "kiosk", "web", "mobile"

    @Column(length = 20)
    private String estado; // "NORMAL", "TARDÍA", etc.

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

    public void setFechaHora(OffsetDateTime localDateTime) {
        this.fechaHora = localDateTime;
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
}
