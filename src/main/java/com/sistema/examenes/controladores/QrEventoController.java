package com.sistema.examenes.controladores;

import com.sistema.examenes.servicios.QrTokenEventoService;
import com.sistema.examenes.utilidades.JqrGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/qr-evento")
@CrossOrigin(origins = "*")
public class QrEventoController {

    @Autowired
    private QrTokenEventoService qrTokenEventoService;

    @Autowired
    private JqrGenerator jqrGenerator;

    @GetMapping("/imagen")
    public ResponseEntity<Map<String, String>> generarQrImagen(@RequestParam Long eventoId) {
        try {
            String token = qrTokenEventoService.generarToken(eventoId, Duration.ofMinutes(5));
            String contenidoQR = "https://asistcontrol.netlify.app/checkin?token=" + token + "&eventoId=" + eventoId;

            BufferedImage qrImage = jqrGenerator.generarImagenQR(contenidoQR);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());

            Map<String, String> response = new HashMap<>();
            response.put("url", "data:image/png;base64," + base64);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "No se pudo generar el QR"));
        }
    }
}
