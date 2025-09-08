package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findByNombreContainingIgnoreCase(String nombre);
    List<Contacto> findByCiudadIgnoreCase(String ciudad);
    List<Contacto> findByPreferenciasIn(List<String> preferencias);
    
    //crm
    Optional<Contacto> findByNombreAndCorreo(String nombre, String correo);

}
