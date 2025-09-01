package com.sistema.examenes.servicios;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.entidades.Evento;

import java.util.List;

public interface SeguimientoService {
    Asistencia registrarAsistenciaQR(String codigoQR, String origen);
    List<Evento> recomendarEventos(Contacto contacto);
}
