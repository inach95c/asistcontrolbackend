package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findByNombreContainingIgnoreCase(String nombre);
    List<Contacto> findByCiudadIgnoreCase(String ciudad);
    List<Contacto> findByPreferenciasIn(List<String> preferencias);
}
