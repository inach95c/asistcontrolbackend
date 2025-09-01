package com.sistema.examenes.servicios;

import com.sistema.examenes.entidades.Invitacion;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.entidades.Evento;

import java.util.List;
import java.util.Optional;

public interface InvitacionService {
    Invitacion guardarInvitacion(Invitacion invitacion);
    Optional<Invitacion> obtenerPorCodigoQR(String codigoQR);
    List<Invitacion> listarPorEvento(Evento evento);
    List<Invitacion> listarPorContacto(Contacto contacto);
    List<Invitacion> listarConfirmadas(Boolean estado);
    void eliminarInvitacion(Long id);
}
