package com.sistema.examenes.controladores;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.entidades.Evento;
import com.sistema.examenes.servicios.ContactoService;
import com.sistema.examenes.servicios.SeguimientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seguimiento")
@CrossOrigin(origins = "*")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @Autowired
    private ContactoService contactoService;

    // ✅ Registrar asistencia por QR
    @PostMapping("/asistencia")
    public ResponseEntity<Asistencia> registrarAsistencia(@RequestParam String codigoQR,
                                                          @RequestParam String origen) {
        try {
            Asistencia asistencia = seguimientoService.registrarAsistenciaQR(codigoQR, origen);
            return ResponseEntity.ok(asistencia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 🎯 Recomendaciones personalizadas
    @GetMapping("/recomendaciones/{contactoId}")
    public ResponseEntity<List<Evento>> recomendarEventos(@PathVariable Long contactoId) {
        Optional<Contacto> contactoOpt = contactoService.obtenerPorId(contactoId);
        if (contactoOpt.isEmpty()) return ResponseEntity.notFound().build();

        List<Evento> recomendados = seguimientoService.recomendarEventos(contactoOpt.get());
        return ResponseEntity.ok(recomendados);
    }
}
