package com.sistema.examenes.controladores;

import com.sistema.examenes.entidades.Invitacion;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.entidades.Evento;
import com.sistema.examenes.servicios.InvitacionService;
import com.sistema.examenes.servicios.ContactoService;
import com.sistema.examenes.servicios.EventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/invitaciones")
@CrossOrigin(origins = "*")
public class InvitacionController {

    @Autowired
    private InvitacionService invitacionService;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private EventoService eventoService;

    // 🎯 Crear invitación con QR
    @PostMapping("/crear")
    public ResponseEntity<Invitacion> crearInvitacion(@RequestParam Long contactoId,
                                                      @RequestParam Long eventoId,
                                                      @RequestParam String canal) {
        Optional<Contacto> contactoOpt = contactoService.obtenerPorId(contactoId);
        Optional<Evento> eventoOpt = eventoService.obtenerPorId(eventoId);

        if (contactoOpt.isEmpty() || eventoOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String codigoQR = UUID.randomUUID().toString(); // 🔐 QR único

        Invitacion invitacion = new Invitacion(codigoQR, contactoOpt.get(), eventoOpt.get(), canal);
        Invitacion guardada = invitacionService.guardarInvitacion(invitacion);

        return ResponseEntity.ok(guardada);
    }

    // 🔍 Buscar por código QR
    @GetMapping("/buscar")
    public ResponseEntity<Invitacion> buscarPorCodigo(@RequestParam String codigoQR) {
        return invitacionService.obtenerPorCodigoQR(codigoQR)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 📋 Listar por evento
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<Invitacion>> listarPorEvento(@PathVariable Long eventoId) {
        Optional<Evento> eventoOpt = eventoService.obtenerPorId(eventoId);
        return eventoOpt.map(evento -> ResponseEntity.ok(invitacionService.listarPorEvento(evento)))
                        .orElse(ResponseEntity.notFound().build());
    }

    // 📋 Listar por contacto
    @GetMapping("/contacto/{contactoId}")
    public ResponseEntity<List<Invitacion>> listarPorContacto(@PathVariable Long contactoId) {
        Optional<Contacto> contactoOpt = contactoService.obtenerPorId(contactoId);
        return contactoOpt.map(contacto -> ResponseEntity.ok(invitacionService.listarPorContacto(contacto)))
                          .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Confirmar asistencia
    @PutMapping("/confirmar/{codigoQR}")
    public ResponseEntity<Invitacion> confirmar(@PathVariable String codigoQR) {
        Optional<Invitacion> invitacionOpt = invitacionService.obtenerPorCodigoQR(codigoQR);
        if (invitacionOpt.isEmpty()) return ResponseEntity.notFound().build();

        Invitacion invitacion = invitacionOpt.get();
        invitacion.setConfirmada(true);
        return ResponseEntity.ok(invitacionService.guardarInvitacion(invitacion));
    }

    // 🧮 Filtrar por estado
    @GetMapping("/estado")
    public ResponseEntity<List<Invitacion>> listarPorEstado(@RequestParam Boolean confirmada) {
        return ResponseEntity.ok(invitacionService.listarConfirmadas(confirmada));
    }
}
