package com.sistema.examenes.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contactos")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String telefono;
    private String ciudad;

    @Enumerated(EnumType.STRING)
    private Etiqueta etiqueta; // Ej: VIP, INVITADO, RECURRENTE

    private Boolean activo = true;

    private LocalDateTime ultimaInteraccion;
    
    private LocalDateTime fechaRegistro; // 🗓️ Nuevo campo

    private Integer edad;

    @Column(length = 10)
    private String sexo; // Ej: "M", "F", "Otro"
    
    @Column(length = 255)
    private String observaciones; // 📝 Nuevo campo

 // 🕓 Historial de eventos asistidos
    @OneToMany(mappedBy = "contacto", cascade = CascadeType.ALL)
    private List<Asistencia> asistencias;

    // 🎯 Preferencias temáticas
    @ElementCollection
    @CollectionTable(name = "contacto_preferencias", joinColumns = @JoinColumn(name = "contacto_id"))
    @Column(name = "preferencia")
    private List<String> preferencias;
   
    // Constructor vacío
    public Contacto() {}

    // Constructor útil para pruebas
    public Contacto(String nombre, String correo, String telefono, String ciudad, Etiqueta etiqueta, Integer edad, String sexo) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.etiqueta = etiqueta;
        this.edad = edad;
        this.sexo = sexo;
        this.activo = true;
        this.ultimaInteraccion = LocalDateTime.now();
        this.setFechaRegistro(LocalDateTime.now()); // ⏱️ Inicialización automática
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getUltimaInteraccion() {
        return ultimaInteraccion;
    }

    public void setUltimaInteraccion(LocalDateTime ultimaInteraccion) {
        this.ultimaInteraccion = ultimaInteraccion;
    }
    
 

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }
    
   

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
    
    public List<String> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(List<String> preferencias) {
        this.preferencias = preferencias;
    }

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public String getObservaciones() {
	    return observaciones;
	}

	public void setObservaciones(String observaciones) {
	    this.observaciones = observaciones;
	}

}
