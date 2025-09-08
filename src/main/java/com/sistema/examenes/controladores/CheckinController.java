package com.sistema.examenes.controladores;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.servicios.AsistenciaService;
import com.sistema.examenes.servicios.ContactoService;
import com.sistema.examenes.servicios.QrTokenEventoService;
import com.sistema.examenes.utilidades.JqrGenerator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

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
    
    @Autowired
    private JqrGenerator jqrGenerator;


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
    
    
    @GetMapping("/evento/{eventoId}/qr")
    public ResponseEntity<Map<String, String>> generarQrVisual(@PathVariable Long eventoId) {
        try {
            // 1. Generar token y URL
            String token = qrTokenEventoService.generarTokenParaEvento(eventoId);
            String contenidoQR = "https://asistcontrol.com/checkin?token=" + token + "&eventoId=" + eventoId;

            // 2. Generar imagen QR
            BufferedImage qrImage = jqrGenerator.generarImagenQR(contenidoQR); // ZXing o tu clase

            // 3. Convertir a base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());

            // 4. Devolver como imagen embebida
            Map<String, String> response = new HashMap<>();
            response.put("url", "data:image/png;base64," + base64);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "No se pudo generar el QR"));
        }
    }


    
}

