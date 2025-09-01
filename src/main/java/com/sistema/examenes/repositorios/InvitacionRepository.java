package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.Invitacion;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitacionRepository extends JpaRepository<Invitacion, Long> {
    Optional<Invitacion> findByCodigoQR(String codigoQR);
    List<Invitacion> findByEvento(Evento evento);
    List<Invitacion> findByContacto(Contacto contacto);
    List<Invitacion> findByConfirmada(Boolean confirmada);
}
