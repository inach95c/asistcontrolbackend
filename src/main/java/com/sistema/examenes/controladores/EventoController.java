package com.sistema.examenes.controladores;

import com.sistema.examenes.entidades.Evento;
import com.sistema.examenes.servicios.EventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "*")

public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<Evento>> listarTodos() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Evento> crear(@RequestBody Evento evento) {
        return ResponseEntity.ok(eventoService.guardar(evento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizar(@PathVariable Long id, @RequestBody Evento eventoActualizado) {
        return ResponseEntity.ok(eventoService.actualizar(id, eventoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/ciudad")
    public ResponseEntity<List<Evento>> buscarPorCiudad(@RequestParam String ciudad) {
        return ResponseEntity.ok(eventoService.buscarPorCiudad(ciudad));
    }

    @PostMapping("/filtrar/temas")
    public ResponseEntity<List<Evento>> filtrarPorTemas(@RequestBody List<String> temas) {
        return ResponseEntity.ok(eventoService.filtrarPorTemas(temas));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Evento>> listarActivos() {
        return ResponseEntity.ok(eventoService.listarActivos());
    }
}
