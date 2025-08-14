package com.sistema.examenes.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sistema.examenes.servicios.QrCodeService;

import java.util.Map;

@RestController
@RequestMapping("/qr")
@CrossOrigin("*")
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    // ✅ Ajustado para incluir tipo en el contenido del QR
    @GetMapping("/generar/{username}/{tipo}")
    public ResponseEntity<byte[]> generarQr(
        @PathVariable String username,
        @PathVariable String tipo
    ) {
        //String contenido = "asistencia:" + username + ":" + tipo.toUpperCase();
    	
    	/*String contenido = "http://192.168.1.100:8080/asistencia/registrar-por-qr?usuario="    // se cambio por el string de abajo
    		    + username + "&tipoEvento=" + tipo.toUpperCase();*/
    	
    	String contenido = ServletUriComponentsBuilder.fromCurrentContextPath()
    	        .path("/asistencia/registrar-por-qr")
    	        .queryParam("usuario", username)
    	        .queryParam("tipoEvento", tipo.toUpperCase())
    	        .toUriString();


        byte[] qr = qrCodeService.generateQRCode(contenido, 250, 250);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qr);
    }

    // ✅ Mantiene registro del escaneo desde frontend
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarEscaneo(@RequestBody Map<String, String> datos) {
        String username = datos.get("username");
        String estado = datos.get("estado");

        qrCodeService.registrarEscaneo(username, estado);

        return ResponseEntity.ok("Escaneo registrado exitosamente");
    }
}
