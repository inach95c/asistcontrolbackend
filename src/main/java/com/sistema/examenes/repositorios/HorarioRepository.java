package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.Horario;
import com.sistema.examenes.entidades.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;



public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByUsuarioId(Long usuarioId);

    @Query("SELECT h FROM Horario h WHERE h.usuario.id = :usuarioId " +
           "AND h.horaEntrada < :horaSalida AND h.horaSalida > :horaEntrada")
    List<Horario> findHorariosSolapados(Long usuarioId, LocalTime horaEntrada, LocalTime horaSalida);

    @Query("SELECT h FROM Horario h JOIN FETCH h.usuario")
    List<Horario> obtenerTodosConUsuario();
    
    @Query("SELECT h FROM Horario h WHERE h.usuario = :usuario AND :fecha BETWEEN h.fechaInicio AND h.fechaFin")
    List<Horario> obtenerHorariosActivosParaFechaAsistencia(@Param("usuario") Usuario usuario, @Param("fecha") LocalDate fecha);
    
    @Query("SELECT h FROM Horario h WHERE h.usuario.id = :usuarioId AND :fecha BETWEEN h.fechaInicio AND h.fechaFin")
    List<Horario> obtenerHorariosActivosParaFecha(@Param("usuarioId") Long usuarioId, @Param("fecha") LocalDate fecha);

    
    Optional<Horario> findByEsConfiguracionTrue();
    
    @Query("SELECT h FROM Horario h WHERE h.usuario.id = :usuarioId AND :fecha BETWEEN h.fechaInicio AND h.fechaFin")
    List<Horario> findByUsuarioIdAndFecha(@Param("usuarioId") Long usuarioId, @Param("fecha") LocalDate fecha);


    List<Horario> findByFechaInicioBetween(LocalDate inicio, LocalDate fin);


}


