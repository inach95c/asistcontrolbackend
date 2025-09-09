package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.QrTokenEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QrTokenEventoRepository extends JpaRepository<QrTokenEvento, String> {
    Optional<QrTokenEvento> findByTokenAndEventoId(String token, Long eventoId);
}
