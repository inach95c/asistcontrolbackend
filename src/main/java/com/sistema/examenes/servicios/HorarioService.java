package com.sistema.examenes.servicios;

import com.sistema.examenes.dto.HorasTrabajadasDTO;
import com.sistema.examenes.dto.ToleranciaDTO;
import com.sistema.examenes.entidades.Horario;
import com.sistema.examenes.entidades.Usuario;

import java.time.LocalDate;
import java.util.List;

public interface HorarioService {
    Horario crearHorario(Horario horario);
    List<Horario> obtenerHorariosPorUsuario(Long usuarioId);
    void eliminarHorario(Long horarioId);
    void eliminarTodosPorUsuario(Long usuarioId);
    boolean existeConflictoDeHorario(Horario horario);
    Usuario obtenerUsuarioPorId(Long id);
    List<Horario> obtenerTodosHorariosConUsuario();
    
    Horario obtenerHorarioDelDia(Usuario usuario, LocalDate fecha);
    
  
    void actualizarTolerancia(int entrada, int salida);
    ToleranciaDTO obtenerTolerancia();
    
    
    Integer obtenerToleranciaEntradaPorFecha(Long usuarioId, LocalDate fecha);

    List<Horario> obtenerHorariosDelDia(Long usuarioId, LocalDate fecha);

    List<HorasTrabajadasDTO> calcularHorasTrabajadasPorUsuario(); 
    
    List<HorasTrabajadasDTO> calcularHorasTrabajadasPorMes(String mes);
    
    Double obtenerHorasNormalesPorDia();

    void actualizarHorasNormalesPorDia(Double valor);

  


}
