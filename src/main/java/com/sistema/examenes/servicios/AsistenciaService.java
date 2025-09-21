/* ok con un solo registro package com.sistema.examenes.servicios;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Usuario;

import java.time.YearMonth;
import java.util.List;

public interface AsistenciaService {
    void registrarEntrada();
    void registrarSalida();
    List<Asistencia> obtenerAsistenciasDelMes(Usuario usuario, YearMonth mes);
	Asistencia registrarHora(String username, String tipoRegistro);
	Object obtenerAsistencia(String username);
	long calcularMinutosTrabajados(String username, YearMonth mes);

}
*/

package com.sistema.examenes.servicios;

import com.sistema.examenes.dto.AsistenciaDTO;
import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Usuario;

import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.List;

public interface AsistenciaService {
  
    
 // Registro por usuario
    void registrarEvento(String tipo, Usuario usuario);
    void registrarEvento(String tipo, Usuario usuario, OffsetDateTime fechaHora);
    boolean registrarDesdeQr(String username, String tipo);
    boolean registrarEventoGenerico(String username, String tipoEvento);

    // Registro por evento y contacto
    Asistencia registrarAsistenciaPorQr(Long eventoId, String token, Long contactoId);
    Asistencia registrarAsistenciaAnonima(Long eventoId, String token);
    boolean yaRegistrado(Long eventoId, Long contactoId);

    // Validación de token
    boolean validarTokenQr(String token);
    String registrarDesdeTokenQr(String token);

    // Historial y administración
    List<Asistencia> obtenerHistorial(Usuario usuario);
    List<Asistencia> obtenerTodas();
    List<Asistencia> filtrarAsistencias(String nombre, String fecha);
    void actualizarAsistencia(Long id, AsistenciaDTO dto);
    void eliminarAsistencia(Long id);

    // Cálculo de horas
    double calcularHorasTrabajadasPorMes(String username, YearMonth mes);

    
}





