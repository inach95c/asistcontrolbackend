package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.entidades.QrTokenEvento;
import com.sistema.examenes.repositorios.QrTokenEventoRepository;
import com.sistema.examenes.servicios.QrTokenEventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class QrTokenEventoServiceImpl implements QrTokenEventoService {

    @Autowired
    private QrTokenEventoRepository repository;

    @Override
    public String generarToken(Long eventoId, Duration duracion) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiracion = LocalDateTime.now().plus(duracion);

        QrTokenEvento qr = new QrTokenEvento();
        qr.setToken(token);
        qr.setEventoId(eventoId);
        qr.setExpiracion(expiracion);
        qr.setUsado(false);

        repository.save(qr);
        return token;
    }

    @Override
    public boolean validarToken(String token, Long eventoId) {
        return repository.findByTokenAndEventoId(token, eventoId)
                .filter(qr -> !qr.isUsado())
                .filter(qr -> LocalDateTime.now().isBefore(qr.getExpiracion()))
                .isPresent();
    }

    @Override
    public void marcarComoUsado(String token) {
        repository.findById(token).ifPresent(qr -> {
            qr.setUsado(true);
            repository.save(qr);
        });
    }

    @Override
    @Scheduled(fixedRate = 600000) // Cada 10 minutos
    public void eliminarExpirados() {
        List<QrTokenEvento> tokens = repository.findAll();
        tokens.stream()
            .filter(qr -> qr.getExpiracion().isBefore(LocalDateTime.now()))
            .forEach(repository::delete);
    }
    
    
    @Override
    public String generarTokenParaEvento(Long eventoId) {
        return generarToken(eventoId, Duration.ofMinutes(5));
    }

    
}
