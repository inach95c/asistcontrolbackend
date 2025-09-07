

package com.sistema.examenes.servicios;

import com.sistema.examenes.entidades.Turno;
import java.util.List;

public interface TurnoService {
    List<Turno> obtenerTodos();
    Turno crearTurno(Turno turno);
    void eliminarTurno(Long id);
    Turno actualizarTurno(Long id, Turno turnoActualizado);

}
