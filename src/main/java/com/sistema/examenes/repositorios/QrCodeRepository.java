package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.RegistroAsistenciaQR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrCodeRepository extends JpaRepository<RegistroAsistenciaQR, Long> {
    Optional<RegistroAsistenciaQR> findTopByUsernameOrderByFechaEscaneoDesc(String username);
}
