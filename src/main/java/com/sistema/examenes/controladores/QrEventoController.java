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
            // 1. Generar token y URL
            String token = qrTokenEventoService.generarTokenParaEvento(eventoId);
            String contenidoQR = "https://asistcontrol.netlify.app/checkin?token=" + token + "&eventoId=" + eventoId;

            // 2. Generar imagen QR
            BufferedImage qrImage = jqrGenerator.generarImagenQR(contenidoQR);

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
