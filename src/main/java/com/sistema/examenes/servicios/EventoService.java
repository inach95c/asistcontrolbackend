package com.sistema.examenes.servicios;

import com.sistema.examenes.entidades.Evento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventoService {

    Evento guardar(Evento evento);

    Evento actualizar(Long id, Evento eventoActualizado);

    void eliminar(Long id);

    Optional<Evento> obtenerPorId(Long id);

    List<Evento> listarTodos();

    List<Evento> buscarPorCiudad(String ciudad);

    List<Evento> filtrarPorTemas(List<String> temas);

    List<Evento> listarActivos();

    List<Evento> obtenerEventosEnCurso(String zonaHoraria, LocalDateTime now);
}
