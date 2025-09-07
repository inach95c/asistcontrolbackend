package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.entidades.Turno;
import com.sistema.examenes.repositorios.TurnoRepository;
import com.sistema.examenes.servicios.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;

    @Autowired
    public TurnoServiceImpl(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @Override
    public List<Turno> obtenerTodos() {
        return turnoRepository.findAll();
    }

    @Override
    public Turno crearTurno(Turno turno) {
        if (turnoRepository.existsByNombre(turno.getNombre())) {
            throw new IllegalArgumentException("Ya existe un turno con ese nombre");
        }

        validarHora(turno.getHoraEntrada());
        validarHora(turno.getHoraSalida());

        return turnoRepository.save(turno);
    }

    @Override
    public void eliminarTurno(Long id) {
        if (!turnoRepository.existsById(id)) {
            throw new NoSuchElementException("Turno no encontrado");
        }
        turnoRepository.deleteById(id);
    }

    private void validarHora(String hora) {
        if (!hora.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
            throw new IllegalArgumentException("Formato de hora inválido: " + hora);
        }
    }
    
    @Override
    public Turno actualizarTurno(Long id, Turno turnoActualizado) {
        Turno existente = turnoRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Turno no encontrado"));

        if (!existente.getNombre().equals(turnoActualizado.getNombre()) &&
            turnoRepository.existsByNombre(turnoActualizado.getNombre())) {
            throw new IllegalArgumentException("Ya existe otro turno con ese nombre");
        }

        validarHora(turnoActualizado.getHoraEntrada());
        validarHora(turnoActualizado.getHoraSalida());

        existente.setNombre(turnoActualizado.getNombre());
        existente.setHoraEntrada(turnoActualizado.getHoraEntrada());
        existente.setHoraSalida(turnoActualizado.getHoraSalida());
        existente.setDescripcion(turnoActualizado.getDescripcion());

        return turnoRepository.save(existente);
    }

}
