package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByCiudadIgnoreCase(String ciudad);
    List<Evento> findByActivoTrue();
    List<Evento> findByTemasIn(List<String> temas);
}
