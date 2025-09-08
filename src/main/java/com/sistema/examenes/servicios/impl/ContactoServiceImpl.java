package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.repositorios.ContactoRepository;
import com.sistema.examenes.servicios.ContactoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactoServiceImpl implements ContactoService {
	
	

    @Autowired
    private ContactoRepository contactoRepository;

    @Override
    public List<Contacto> listarTodos() {
        return contactoRepository.findAll();
    }

    @Override
    public Optional<Contacto> obtenerPorId(Long id) {
        return contactoRepository.findById(id);
    }

    @Override
    public Contacto guardar(Contacto contacto) {
        contacto.setFechaRegistro(LocalDateTime.now());
        return contactoRepository.save(contacto);
    }

    @Override
    public Contacto actualizar(Long id, Contacto contactoActualizado) {
        return contactoRepository.findById(id).map(c -> {
            c.setNombre(contactoActualizado.getNombre());
            c.setCorreo(contactoActualizado.getCorreo());
            c.setCiudad(contactoActualizado.getCiudad());
            c.setPreferencias(contactoActualizado.getPreferencias());
            c.setObservaciones(contactoActualizado.getObservaciones());
            return contactoRepository.save(c);
        }).orElseThrow(() -> new EntityNotFoundException("Contacto no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        contactoRepository.deleteById(id);
    }

    // Filtros
    @Override
    public List<Contacto> buscarPorNombre(String nombre) {
        return contactoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Contacto> buscarPorCiudad(String ciudad) {
        return contactoRepository.findByCiudadIgnoreCase(ciudad);
    }

    @Override
    public List<Contacto> filtrarPorPreferencias(List<String> preferencias) {
        return contactoRepository.findByPreferenciasIn(preferencias);
    }
    
    

    @Override
    public Contacto crearSiNoExiste(String nombre, String correo) {
        Optional<Contacto> existente = contactoRepository.findByNombreAndCorreo(nombre, correo);

        if (existente.isPresent()) {
            return existente.get();
        }

        Contacto nuevo = new Contacto();
        nuevo.setNombre(nombre);
        nuevo.setCorreo(correo);
        // puedes agregar otros campos por defecto si lo deseas

        return contactoRepository.save(nuevo);
    }

    
}
