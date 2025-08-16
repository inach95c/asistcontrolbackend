package com.sistema.examenes.controladores;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sistema.examenes.entidades.QrToken;
import com.sistema.examenes.servicios.QrCodeService;
import com.sistema.examenes.servicios.QrTokenService;
import java.util.Optional;

import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/qr")
//@RequestMapping("/api/qr") // 👈 Ahora sí responde a /api/qr/...
//@CrossOrigin("*")
@CrossOrigin(origins = "https://asistcontrol.netlify.app", allowCredentials = "true")

public class QrTokenController {

    @Autowired
    private QrTokenService tokenService;
    @Autowired
    private QrCodeService qrCodeService;

    @GetMapping("/generar-token/{tipoEvento}")
    public ResponseEntity<byte[]> generarQrConToken(@PathVariable String tipoEvento) {
        QrToken token = tokenService.generarToken(tipoEvento, Duration.ofMinutes(1));
       // String url = "http://TU_DOMINIO/asistencia/registrar-por-token?token=" + token.getToken();
    //    String url = "http://192.168.1.100/asistencia/registrar-por-token?token=" + token.getToken();

       String dominio = "https://asistcontrol.netlify.app"; // o tu dominio real iba 192.168.1.100
        String url = dominio + "/asistencia/registrar-por-token?token=" + token.getToken();
        /* * String url = ServletUriComponentsBuilder.fromCurrentContextPath()
        	    .path("/asistencia/registrar-por-token")
        	    .queryParam("token", token.getToken())                // estas lineas sustituyen las de arriba si no quiero dir estatica
        	    .toUriString(); * */



        
        byte[] qr = qrCodeService.generateQRCode(url, 250, 250);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qr);
    }

 
 // PARA GENERAR TOKEN, EL USUARIO SOLO SCANERARÁ SIN NECESIDAD DE AUTENTICARSE SOLUCION (2)
    @GetMapping("/generar-jwt/{username}/{tipoEvento}")
    public ResponseEntity<byte[]> generarQrJwt(@PathVariable String username, @PathVariable String tipoEvento) {
        String jwt = tokenService.generarJwt(tipoEvento, username, Duration.ofMinutes(2));
        //String url = "http://TU_DOMINIO/asistencia/registrar-por-jwt?token=" + jwt;
         /* String url = "http://192.168.1.100/asistencia/registrar-por-jwt?token=" + jwt;*/
        
    /* *    String url = "https://asistcontrol.netlify.app/asistencia/registrar-por-jwt?token=" + jwt; * */
          String url = ServletUriComponentsBuilder.fromCurrentContextPath()
        		    .path("/asistencia/registrar-por-jwt")
        		    .queryParam("token", jwt)
        		    .toUriString();

        
        byte[] qr = qrCodeService.generateQRCode(url, 250, 250);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qr);
    }
    
    
    // PARA GENERAR TOKEN, EL USUARIO SOLO SCANERARÁ SIN NECESIDAD DE AUTENTICARSE SOLUCION, codigo qr PERSONALIZADO (3)
    @GetMapping("/generar-qr-jwt-para-usuario")
    public ResponseEntity<byte[]> generarQrParaUsuarioAutenticado(Authentication auth) {
        String username = auth.getName(); // 👤 Usuario desde sesión
        String jwt = tokenService.generarJwt("ENTRADA", username, Duration.ofMinutes(2));
        
        /*String url = "http://192.168.1.100/asistencia/registrar-por-jwt?token=" + jwt;*/
   /* *     String url = "https://asistcontrol.netlify.app/asistencia/registrar-por-jwt?token=" + jwt; * */
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
        	    .path("/asistencia/registrar-por-jwt")
        	    .queryParam("token", jwt)
        	    .toUriString();

        byte[] qr = qrCodeService.generateQRCode(url, 250, 250);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qr);
    }


    
    
}
