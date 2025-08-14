package com.sistema.examenes.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "examenes")
public class Examen {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long examenId;
	
	private String titulo;
	private String descripcion;
	private String puntosMaximos;
	private String numeroDePreguntas;
	private boolean activo = false;
		
	@ManyToOne(fetch = FetchType.EAGER)
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.EAGER)             // esto lo agregue despues cuidado si algo no funciona      ojo
	private Usuario usuario;                        //antes me daba error al intentar realizar la notificacion
	
	
	@OneToMany(mappedBy = "examen",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Pregunta> preguntas = new HashSet<>();
	
	//*************************************inicio variables de txd**********************************************//
	
	private String prioridad;
	private String categoriaDelCliente;
	private String organismo;
	private String numeroDeSolicitud;
	private String seguimiento;
	private String municipio;              //no lo uso. Uso categoria
	private String consejoPopular;
	private String direccion;
	private String telefonoDeContacto;
	private String solicitud;
	private String velocidad;
	private String enlace;
	private String noAdsl;     //
	private String cuota;
	private String costoDeInstalacion;
	private String estadoDelServicio;              
	private String tipoDeServicio;	
	private String programaProyecto;
	private String instalada;
	
	
	//@Column(name = "fecha_de_solicitud")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")           
    private LocalDate fechaDeSolicitud;                 // comercial
	
	
	/*OPERACIONES*/
	private String estadoDeCalificacionDeLosCentros;
	private String observacion;
	private String evaluacion;
	private String observacionesEspecialistaDeOperaciones;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaRespuestaCalificacionOperaciones;                      //operacioes
	
	// Inversiones
	private String propuestaDeSoluionTecnica;
	private String tipoDeRecursosADemandar;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")           //inversiones
    private LocalDate fechaDeEjecucionEstimadaAProponer;
	private String observacionEspInversiones;
	
	/* Asistencia  */

	
	

	//*****************************************fin variables txd******************************************//
	
	//get an set

	public Long getExamenId() {
		return examenId;
	}

	public void setExamenId(Long examenId) {
		this.examenId = examenId;
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

	public String getPuntosMaximos() {
		return puntosMaximos;
	}

	public void setPuntosMaximos(String puntosMaximos) {
		this.puntosMaximos = puntosMaximos;
	}

	public String getNumeroDePreguntas() {
		return numeroDePreguntas;
	}

	public void setNumeroDePreguntas(String numeroDePreguntas) {
		this.numeroDePreguntas = numeroDePreguntas;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Set<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}


	// Constructor vacio
	public Examen() {
		
	}

	//***********************************variables de txd fueron agregadas luego del loco**************************************//
	
	
	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getCategoriaDelCliente() {
		return categoriaDelCliente;
	}

	public void setCategoriaDelCliente(String categoriaDelCliente) {
		this.categoriaDelCliente = categoriaDelCliente;
	}

	public String getOrganismo() {
		return organismo;
	}

	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}

	public String getNumeroDeSolicitud() {
		return numeroDeSolicitud;
	}

	public void setNumeroDeSolicitud(String numeroDeSolicitud) {
		this.numeroDeSolicitud = numeroDeSolicitud;
	}

	public String getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(String seguimiento) {
		this.seguimiento = seguimiento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getConsejoPopular() {
		return consejoPopular;
	}

	public void setConsejoPopular(String consejoPopular) {
		this.consejoPopular = consejoPopular;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefonoDeContacto() {
		return telefonoDeContacto;
	}

	public void setTelefonoDeContacto(String telefonoDeContacto) {
		this.telefonoDeContacto = telefonoDeContacto;
	}

	
	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public String getNoAdsl() {
		return noAdsl;
	}

	public void setNoAdsl(String noAdsl) {
		this.noAdsl = noAdsl;
	}

	public String getCuota() {
		return cuota;
	}

	public void setCuota(String cuota) {
		this.cuota = cuota;
	}

	public String getCostoDeInstalacion() {
		return costoDeInstalacion;
	}

	public void setCostoDeInstalacion(String costoDeInstalacion) {
		this.costoDeInstalacion = costoDeInstalacion;
	}

	public String getEstadoDelServicio() {
		return estadoDelServicio;
	}

	public void setEstadoDelServicio(String estadoDelServicio) {
		this.estadoDelServicio = estadoDelServicio;
	}

	public String getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(String velocidad) {
		this.velocidad = velocidad;
	}

	public String getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}

	public LocalDate getFechaDeSolicitud() {
		return fechaDeSolicitud;
	}

	public void setFechaDeSolicitud(LocalDate fechaDeSolicitud) {
		this.fechaDeSolicitud = fechaDeSolicitud;
	}

	public String getEstadoDeCalificacionDeLosCentros() {
		return estadoDeCalificacionDeLosCentros;
	}

	public void setEstadoDeCalificacionDeLosCentros(String estadoDeCalificacionDeLosCentros) {
		this.estadoDeCalificacionDeLosCentros = estadoDeCalificacionDeLosCentros;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getObservacionesEspecialistaDeOperaciones() {
		return observacionesEspecialistaDeOperaciones;
	}

	public void setObservacionesEspecialistaDeOperaciones(String observacionesEspecialistaDeOperaciones) {
		this.observacionesEspecialistaDeOperaciones = observacionesEspecialistaDeOperaciones;
	}

	public LocalDate getFechaRespuestaCalificacionOperaciones() {
		return fechaRespuestaCalificacionOperaciones;
	}

	public void setFechaRespuestaCalificacionOperaciones(LocalDate fechaRespuestaCalificacionOperaciones) {
		this.fechaRespuestaCalificacionOperaciones = fechaRespuestaCalificacionOperaciones;
	}

	public String getPropuestaDeSoluionTecnica() {
		return propuestaDeSoluionTecnica;
	}

	public void setPropuestaDeSoluionTecnica(String propuestaDeSoluionTecnica) {
		this.propuestaDeSoluionTecnica = propuestaDeSoluionTecnica;
	}

	public String getTipoDeRecursosADemandar() {
		return tipoDeRecursosADemandar;
	}

	public void setTipoDeRecursosADemandar(String tipoDeRecursosADemandar) {
		this.tipoDeRecursosADemandar = tipoDeRecursosADemandar;
	}

	public String getObservacionEspInversiones() {
		return observacionEspInversiones;
	}

	public void setObservacionEspInversiones(String observacionEspInversiones) {
		this.observacionEspInversiones = observacionEspInversiones;
	}

	

	public LocalDate getFechaDeEjecucionEstimadaAProponer() {
		return fechaDeEjecucionEstimadaAProponer;
	}

	public void setFechaDeEjecucionEstimadaAProponer(LocalDate fechaDeEjecucionEstimadaAProponer) {
		this.fechaDeEjecucionEstimadaAProponer = fechaDeEjecucionEstimadaAProponer;
	}

	public String getTipoDeServicio() {
		return tipoDeServicio;
	}

	public void setTipoDeServicio(String tipoDeServicio) {
		this.tipoDeServicio = tipoDeServicio;
	}

	public String getProgramaProyecto() {
		return programaProyecto;
	}

	public void setProgramaProyecto(String programaProyecto) {
		this.programaProyecto = programaProyecto;
	}

	public String getInstalada() {
		return instalada;
	}

	public void setInstalada(String instalada) {
		this.instalada = instalada;
	}

	

	

	

} //fin
