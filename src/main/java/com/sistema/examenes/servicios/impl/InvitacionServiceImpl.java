package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.entidades.Invitacion;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.entidades.Evento;
import com.sistema.examenes.repositorios.InvitacionRepository;
import com.sistema.examenes.servicios.InvitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvitacionServiceImpl implements InvitacionService {

    @Autowired
    private InvitacionRepository invitacionRepository;

    @Override
    public Invitacion guardarInvitacion(Invitacion invitacion) {
        return invitacionRepository.save(invitacion);
    }

    @Override
    public Optional<Invitacion> obtenerPorCodigoQR(String codigoQR) {
        return invitacionRepository.findByCodigoQR(codigoQR);
    }

    @Override
    public List<Invitacion> listarPorEvento(Evento evento) {
        return invitacionRepository.findByEvento(evento);
    }

    @Override
    public List<Invitacion> listarPorContacto(Contacto contacto) {
        return invitacionRepository.findByContacto(contacto);
    }

    @Override
    public List<Invitacion> listarConfirmadas(Boolean estado) {
        return invitacionRepository.findByConfirmada(estado);
    }

    @Override
    public void eliminarInvitacion(Long id) {
        invitacionRepository.deleteById(id);
    }
}
