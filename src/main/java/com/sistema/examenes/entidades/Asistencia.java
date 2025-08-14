

/* ok con un solo registro   package com.sistema.examenes.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Asistencia() {}

    public Asistencia(LocalDateTime horaEntrada, Usuario usuario) {
        this.horaEntrada = horaEntrada;
        this.usuario = usuario;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
*/

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
    
/*    @Column(nullable = false)
    private String tipoEvento; // Puedes renombrarlo a tipoEvento si deseas

    // Getter y setter
    public String getTipoEcvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
*/
    
}


