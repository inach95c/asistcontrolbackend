package com.sistema.examenes.entidades;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "preguntas")
public class Pregunta {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long preguntaId;
	  
	
	@Column(length = 5000)
	private String contenido;
	private String imagen;
	private String opcion1;
	private String opcion2;
	private String opcion3;
	private String opcion4;
		
	
	@Transient
	private String respuestaDada;
	
	private String respuesta;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Examen examen;
	
	//--------------------------------INICIO ENTIDADES DE OPERACIONES E INVERSIONES-------------------------------------------------------------
	private String estadoDeCalificacionDeLosCentros;
	private String observacion;
	private String evaluacion;
	private String observacionesEspecialistaDeOperaciones;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaRespuestaCalificacionOperaciones;
	//--------------------------------FIN ENTIDADES DE OPERACIONES E INVERSIONES-------------------------------------------------------------
	
	//get and set

	public Long getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(Long preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getOpcion1() {
		return opcion1;
	}

	public void setOpcion(String opcion1) {
		this.opcion1 = opcion1;
	}

	public String getOpcion2() {
		return opcion2;
	}

	public void setOpcion2(String opcion2) {
		this.opcion2 = opcion2;
	}

	public String getOpcion3() {
		return opcion3;
	}

	public void setOpcion3(String opcion3) {
		this.opcion3 = opcion3;
	}

	public String getOpcion4() {
		return opcion4;
	}

	public void setOpcion4(String opcion4) {
		this.opcion4 = opcion4;
	}

	public String getRespuestaDada() {
		return respuestaDada;
	}

	public void setRespuestaDada(String respuestaDada) {
		this.respuestaDada = respuestaDada;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	// constructor vacio
	public Pregunta() {
		
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
//-------------------------------get and set txd---------------------------------------------------------------------------
	
	
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

	public LocalDate getFechaRespuestaCalificacionOperciones() {
		return fechaRespuestaCalificacionOperaciones;
	}

	public void setFechaRespuestaCalificacionOperciones(LocalDate fechaRespuestaCalificacionOperaciones) {
		this.fechaRespuestaCalificacionOperaciones = fechaRespuestaCalificacionOperaciones;
	}
	
	
	
} //fin
