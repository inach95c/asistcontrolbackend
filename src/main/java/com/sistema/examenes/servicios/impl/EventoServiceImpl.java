package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.entidades.Evento;
import com.sistema.examenes.repositorios.EventoRepository;
import com.sistema.examenes.servicios.EventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public Evento guardar(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public Evento actualizar(Long id, Evento eventoActualizado) {
        return eventoRepository.findById(id).map(e -> {
            e.setTitulo(eventoActualizado.getTitulo());
            e.setDescripcion(eventoActualizado.getDescripcion());
            e.setCiudad(eventoActualizado.getCiudad());
            e.setFechaInicio(eventoActualizado.getFechaInicio());
            e.setFechaFin(eventoActualizado.getFechaFin());
            e.setTipo(eventoActualizado.getTipo());
            e.setTemas(eventoActualizado.getTemas());
            e.setActivo(eventoActualizado.getActivo());
            return eventoRepository.save(e);
        }).orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        eventoRepository.deleteById(id);
    }

    @Override
    public Optional<Evento> obtenerPorId(Long id) {
        return eventoRepository.findById(id);
    }

    @Override
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    @Override
    public List<Evento> buscarPorCiudad(String ciudad) {
        return eventoRepository.findByCiudadIgnoreCase(ciudad);
    }

    @Override
    public List<Evento> filtrarPorTemas(List<String> temas) {
        return eventoRepository.findByTemasIn(temas);
    }

    @Override
    public List<Evento> listarActivos() {
        return eventoRepository.findByActivoTrue();
    }
}

