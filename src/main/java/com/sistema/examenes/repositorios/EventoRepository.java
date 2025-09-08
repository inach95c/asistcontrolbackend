package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.Evento;
import com.sistema.examenes.entidades.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByCiudadIgnoreCase(String ciudad);

    List<Evento> findByActivoTrue();

    List<Evento> findByTemasIn(List<String> temas);

    List<Evento> findByZonaHorariaAndActivoTrue(String zonaHoraria);

    List<Evento> findByTipoAndCiudadIgnoreCase(TipoEvento tipo, String ciudad);

    @Query("SELECT e FROM Evento e WHERE e.activo = true AND e.zonaHoraria = :zonaHoraria AND :now BETWEEN e.fechaInicio AND e.fechaFin")
    List<Evento> findEventosEnCurso(@Param("zonaHoraria") String zonaHoraria, @Param("now") LocalDateTime now);
}
