package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.entidades.Evento;
import com.sistema.examenes.entidades.Invitacion;
import com.sistema.examenes.repositorios.AsistenciaRepository;
import com.sistema.examenes.repositorios.EventoRepository;
import com.sistema.examenes.repositorios.InvitacionRepository;
//import com.sistema.examenes.repositorios.EventoRepository;
import com.sistema.examenes.servicios.SeguimientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeguimientoServiceImpl implements SeguimientoService {

    @Autowired
    private InvitacionRepository invitacionRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public Asistencia registrarAsistenciaQR(String codigoQR, String origen) {
        Optional<Invitacion> invitacionOpt = invitacionRepository.findByCodigoQR(codigoQR);

        if (invitacionOpt.isEmpty()) {
            throw new IllegalArgumentException("Invitación no encontrada");
        }

        Invitacion invitacion = invitacionOpt.get();
        invitacion.setConfirmada(true);
        invitacionRepository.save(invitacion);

        Contacto contacto = invitacion.getContacto();
        Evento evento = invitacion.getEvento();

        Asistencia asistencia = new Asistencia();
        asistencia.setFechaHora(LocalDateTime.now());
        asistencia.setTipo("ENTRADA");
        asistencia.setOrigen(origen);
        asistencia.setEstado("NORMAL");
        asistencia.setContacto(contacto);
        asistencia.setEvento(evento);

        contacto.setUltimaInteraccion(LocalDateTime.now());

        return asistenciaRepository.save(asistencia);
    }

    @Override
    public List<Evento> recomendarEventos(Contacto contacto) {
        List<String> preferencias = contacto.getPreferencias();
        if (preferencias == null || preferencias.isEmpty()) return List.of();

        return eventoRepository.findAll().stream()
                .filter(e -> e.getTemas().stream().anyMatch(preferencias::contains))
                .collect(Collectors.toList());
    }
}
