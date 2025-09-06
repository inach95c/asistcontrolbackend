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
    void registrarEvento(String tipo, Usuario usuario);
    List<Asistencia> obtenerHistorial(Usuario usuario);
    
    //admin
    // Nuevos métodos para el administrador
    List<Asistencia> obtenerTodas();
    List<Asistencia> filtrarAsistencias(String nombre, String fecha); // Ambos parámetros opcionales
    
    void actualizarAsistencia(Long id, AsistenciaDTO dto);
    
    void eliminarAsistencia(Long id);
    
 // Para escaneo desde QR
    boolean registrarDesdeQr(String username, String tipo);
    
    //para vacaciones u otros
    
    boolean registrarEventoGenerico(String username, String tipoEvento);

    
    boolean validarTokenQr(String token);
    
    String registrarDesdeTokenQr(String token);


    double calcularHorasTrabajadasPorMes(String username, YearMonth mes);
   
    
    void registrarEvento(String tipo, Usuario usuario, OffsetDateTime fechaHora);

    
}





