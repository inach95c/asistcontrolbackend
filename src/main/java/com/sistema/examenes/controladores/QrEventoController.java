package com.sistema.examenes.controladores;

import com.sistema.examenes.servicios.QrTokenEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/qr-evento")
@CrossOrigin(origins = "*")
public class QrEventoController {

    @Autowired
    private QrTokenEventoService qrTokenEventoService;

    @GetMapping("/generar")
    public ResponseEntity<Map<String, String>> generarQrToken(@RequestParam Long eventoId) {
        String token = qrTokenEventoService.generarTokenParaEvento(eventoId);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("url", "https://asistcontrol.com/checkin?token=" + token + "&eventoId=" + eventoId);

        return ResponseEntity.ok(response);
    }
}
