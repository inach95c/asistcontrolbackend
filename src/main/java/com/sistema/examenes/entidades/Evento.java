package com.sistema.examenes.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 500)
    private String descripcion;

    private String ciudad;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private Boolean activo = true;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipo; // Ej: CONFERENCIA, TALLER, LANZAMIENTO

    @ElementCollection
    @CollectionTable(name = "evento_temas", joinColumns = @JoinColumn(name = "evento_id"))
    @Column(name = "tema")
    private List<String> temas;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Asistencia> asistencias;

    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor vacío
    public Evento() {}

    // Constructor útil
    public Evento(String titulo, String descripcion, String ciudad, LocalDateTime fechaInicio, LocalDateTime fechaFin, TipoEvento tipo, List<String> temas) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
        this.temas = temas;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvento tipo) {
        this.tipo = tipo;
    }

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
