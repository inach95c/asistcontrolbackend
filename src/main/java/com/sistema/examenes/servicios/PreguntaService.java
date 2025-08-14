package com.sistema.examenes.servicios;

import java.util.List;
import java.util.Set;

import com.sistema.examenes.entidades.Examen;
import com.sistema.examenes.entidades.Pregunta;

public interface PreguntaService {

    Pregunta agregarPregunta(Pregunta pregunta);
	
	Pregunta actualizarPregunta(Pregunta pregunta);
	
	Set<Pregunta> obtenerPreguntas();
	
	Pregunta obtenerPregunta(Long preguntaId);
	
	Set<Pregunta> obtenerPreguntasDelExamen(Examen examen);
	
	void eliminarPregunta(Long preguntaId);
	
	Pregunta listarPregunta(Long preguntaId);

	  ////////////////////////////////////////////// txd ///////////////////////////////////
	void actualizarEstado(Long id, String estadoDeCalificacionDeLosCentros);   // txd
	
	  ////////////////////////////////////////////// txd ///////////////////////////////////

	
}
