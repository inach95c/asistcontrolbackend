package com.sistema.examenes.controladores;

import com.sistema.examenes.servicios.QrTokenEventoService;
import com.sistema.examenes.utilidades.JqrGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("/qr-evento")
@CrossOrigin(origins = "*")
public class QrEventoController {

    @Autowired
    private QrTokenEventoService qrTokenEventoService;

    @Autowired
    private JqrGenerator jqrGenerator;

    @GetMapping("/generar")
    public ResponseEntity<Map<String, String>> generarQrToken(@RequestParam Long eventoId) {
        String token = qrTokenEventoService.generarTokenParaEvento(eventoId);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("url", "https://asistcontrol.netlify.app/checkin?token=" + token + "&eventoId=" + eventoId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/imagen")
    public ResponseEntity<Map<String, String>> generarQrImagen(@RequestParam Long eventoId) {
        try {
            // 1. Validar eventoId
            if (eventoId == null || eventoId <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "eventoId inválido"));
            }

            // 2. Generar token y contenido del QR
            String token = qrTokenEventoService.generarTokenParaEvento(eventoId);
            String contenidoQR = "https://asistcontrol.netlify.app/checkin?token=" + token + "&eventoId=" + eventoId;

            // 3. Generar imagen QR
            BufferedImage qrImage = jqrGenerator.generarImagenQR(contenidoQR);
            if (qrImage == null) {
                return ResponseEntity.status(500).body(Map.of("error", "No se pudo generar la imagen QR"));
            }

            // 4. Convertir imagen a base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            baos.flush();
            String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            baos.close();

            // 5. Preparar respuesta
            Map<String, String> response = new HashMap<>();
            response.put("url", "data:image/png;base64," + base64);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace(); // Para depuración en consola
            return ResponseEntity.status(500).body(Map.of("error", "Error al generar QR: " + e.getMessage()));
        }
    }
}
