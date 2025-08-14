package com.sistema.examenes.repositorios;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.examenes.entidades.Examen;
import com.sistema.examenes.entidades.Pregunta;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long>{

	
	Set<Pregunta> findByExamen(Examen examen);
	
	
}
 