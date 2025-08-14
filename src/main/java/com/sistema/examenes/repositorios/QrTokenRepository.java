package com.sistema.examenes.repositorios;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.examenes.entidades.QrToken;




public interface QrTokenRepository extends JpaRepository<QrToken, String> {
	Optional<QrToken> findByToken(String token);
}
