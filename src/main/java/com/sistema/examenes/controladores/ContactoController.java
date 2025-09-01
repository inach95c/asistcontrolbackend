package com.sistema.examenes.controladores;



import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.servicios.ContactoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/contactos")
//@CrossOrigin(origins = "https://asistcontrol.netlify.app")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping
    public ResponseEntity<List<Contacto>> listarTodos() {
        return ResponseEntity.ok(contactoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contacto> obtenerPorId(@PathVariable Long id) {
        return contactoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contacto> crear(@RequestBody Contacto contacto) {
        return ResponseEntity.ok(contactoService.guardar(contacto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizar(@PathVariable Long id, @RequestBody Contacto contactoActualizado) {
        return ResponseEntity.ok(contactoService.actualizar(id, contactoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contactoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Filtros

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Contacto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(contactoService.buscarPorNombre(nombre));
    }

    @GetMapping("/buscar/ciudad")
    public ResponseEntity<List<Contacto>> buscarPorCiudad(@RequestParam String ciudad) {
        return ResponseEntity.ok(contactoService.buscarPorCiudad(ciudad));
    }

    @PostMapping("/filtrar/preferencias")
    public ResponseEntity<List<Contacto>> filtrarPorPreferencias(@RequestBody List<String> preferencias) {
        return ResponseEntity.ok(contactoService.filtrarPorPreferencias(preferencias));
    }
}
