
package com.sistema.examenes.servicios;



import java.time.Duration;
import java.util.Optional;

import com.sistema.examenes.entidades.QrToken;

public interface QrTokenService {

    QrToken generarToken(String tipoEvento, Duration duracion);

    Optional<QrToken> validarToken(String token);

    void marcarComoUsado(String token);

 // PARA GENERAR TOKEN, EL USUARIO SOLO SCANERARÁ SIN NECESIDAD DE AUTENTICARSE SOLUCION (2)
    String generarJwt(String tipoEvento, String username, Duration ofMinutes);
    
  //  QrToken generarToken(String tipoEvento, Duration duracion, String username);
 // PARA GENERAR TOKEN, EL USUARIO SOLO SCANERARÁ SIN NECESIDAD DE AUTENTICARSE SOLUCION (3) 
    String validarJwtYExtraerUsuario(String token);

}

