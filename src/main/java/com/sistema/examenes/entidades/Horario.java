package com.sistema.examenes.entidades;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "horarios")
public class Horario {

  /*  public enum Turno {
    	MANIANA, TARDE, NOCHE
    }
*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 /*   @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Turno turno;
*/
    @Column
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaFin;

    @Column(nullable = false)
    private LocalTime horaEntrada;

    @Column(nullable = false)
    private LocalTime horaSalida;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
   */ 
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    
    
    private int toleranciaEntrada;
    private int toleranciaSalida;
    
    
    private int horasNormalesPorDia;
    
    
    
    
  //  @Column(name = "es_configuracion")
  //  private Boolean esConfiguracion;
    
    @Column(nullable = false)
    private boolean esConfiguracion = false;






    public boolean isEsConfiguracion() {
		return esConfiguracion;
	}

	public void setEsConfiguracion(boolean esConfiguracion) {
		this.esConfiguracion = esConfiguracion;
	}

	public Horario() {}

    public Horario(LocalTime horaEntrada, LocalTime horaSalida, Usuario usuario) {
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

 /*   public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }
*/
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public LocalTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalTime horaEntrada) { this.horaEntrada = horaEntrada; }

    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

	public int getToleranciaEntrada() {
		return toleranciaEntrada;
	}

	public void setToleranciaEntrada(int toleranciaEntrada) {
		this.toleranciaEntrada = toleranciaEntrada;
	}

	public int getToleranciaSalida() {
		return toleranciaSalida;
	}

	public void setToleranciaSalida(int toleranciaSalida) {
		this.toleranciaSalida = toleranciaSalida;
	}

	public int getHorasNormalesPorDia() {
		return horasNormalesPorDia;
	}

	public void setHorasNormalesPorDia(int horasNormalesPorDia) {
		this.horasNormalesPorDia = horasNormalesPorDia;
	}
}
