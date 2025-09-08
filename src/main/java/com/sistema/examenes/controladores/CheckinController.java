package com.sistema.examenes.controladores;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.servicios.AsistenciaService;
import com.sistema.examenes.servicios.ContactoService;
import com.sistema.examenes.servicios.QrTokenEventoService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
@CrossOrigin(origins = "*")
public class CheckinController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private QrTokenEventoService qrTokenEventoService;
    
    @Autowired
    private ContactoService contactoService;

    /**
     * Escaneo anónimo desde QR dinámico
     * Ejemplo: /checkin?token=abc123&eventoId=456
     */
    @PostMapping
    public ResponseEntity<String> registrarAsistenciaAnonima(
            @RequestParam String token,
            @RequestParam Long eventoId) {

        try {
            Asistencia asistencia = asistenciaService.registrarAsistenciaAnonima(eventoId, token);
            return ResponseEntity.ok("✅ Asistencia registrada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("⚠️ Error al registrar asistencia");
        }
    }

    /**
     * Escaneo autenticado (con contacto)
     * Ejemplo: /checkin/contacto?token=abc123&eventoId=456&contactoId=789
     */
    @PostMapping("/contacto")
    public ResponseEntity<String> registrarAsistenciaPorContacto(
            @RequestParam String token,
            @RequestParam Long eventoId,
            @RequestParam Long contactoId) {

        try {
            Asistencia asistencia = asistenciaService.registrarAsistenciaPorQr(eventoId, token, contactoId);
            return ResponseEntity.ok("✅ Asistencia registrada para contacto");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body("⚠️ " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("⚠️ Error al registrar asistencia");
        }
    }
    
    
    @PostMapping("/manual")
    public ResponseEntity<String> registrarManual(@RequestBody Map<String, String> datos) {
        String nombre = datos.get("nombre");
        String correo = datos.get("correo");
        Long eventoId = Long.valueOf(datos.get("eventoId"));

        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre obligatorio");
        }

        Contacto contacto = contactoService.crearSiNoExiste(nombre, correo);
        asistenciaService.registrarAsistenciaPorQr(eventoId, "QR_ESTATICO", contacto.getId());

        return ResponseEntity.ok("✅ Asistencia registrada para " + nombre);
    }

    
}

