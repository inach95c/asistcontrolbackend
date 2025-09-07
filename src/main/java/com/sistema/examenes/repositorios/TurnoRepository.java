package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    boolean existsByNombre(String nombre);
}

