package com.sistema.examenes.servicios.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.examenes.entidades.Examen;
import com.sistema.examenes.entidades.Pregunta;
import com.sistema.examenes.repositorios.PreguntaRepository;
//import com.sistema.examenes.servicios.PregunaService;
import com.sistema.examenes.servicios.PreguntaService;

@Service
public class PreguntaServiceImpl implements PreguntaService {
	
	
	@Autowired
	private PreguntaRepository preguntaRepository;
	

	@Override
	public Pregunta agregarPregunta(Pregunta pregunta) {
		// TODO Auto-generated method stub
		return preguntaRepository.save(pregunta);
	}

	@Override
	public Pregunta actualizarPregunta(Pregunta pregunta) {
		// TODO Auto-generated method stub
		return preguntaRepository.save(pregunta);
	}

	@Override
	public Set<Pregunta> obtenerPreguntas() {
		// TODO Auto-generated method stub
		return (Set<Pregunta>) preguntaRepository.findAll();
	}

	@Override
	public Pregunta obtenerPregunta(Long preguntaId) {
		// TODO Auto-generated method stub
		return preguntaRepository.findById(preguntaId).get();
	}

	@Override
	public Set<Pregunta> obtenerPreguntasDelExamen(Examen examen) {
		// TODO Auto-generated method stub
		return preguntaRepository.findByExamen(examen);
	}

	@Override
	public void eliminarPregunta(Long preguntaId) {
		// TODO Auto-generated method stub
		Pregunta pregunta = new Pregunta();		
		pregunta.setPreguntaId(preguntaId);
		preguntaRepository.delete(pregunta);
	}

	@Override
	public Pregunta listarPregunta(Long preguntaId) {
		return this.preguntaRepository.getOne(preguntaId);
	}
 
	  ////////////////////////////////////////////// txd ///////////////////////////////////
	
	public void actualizarEstado(Long id, String estadoDeCalificacionDeLosCentros) {
        Pregunta pregunta = preguntaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con id: " + id));
        pregunta.setEstadoDeCalificacionDeLosCentros(estadoDeCalificacionDeLosCentros);
        preguntaRepository.save(pregunta);
    }
	  ////////////////////////////////////////////// txd ///////////////////////////////////	

 
}
