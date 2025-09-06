package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.dto.HorasTrabajadasDTO;
import com.sistema.examenes.dto.ToleranciaDTO;
import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Horario;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.excepciones.ConflictException;
import com.sistema.examenes.repositorios.AsistenciaRepository;
import com.sistema.examenes.repositorios.HorarioRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import com.sistema.examenes.servicios.HorarioService;
import com.sistema.examenes.servicios.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HorarioServiceImpl implements HorarioService {

	@Autowired
    private AsistenciaRepository asistenciaRepository;
	
	@Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Horario crearHorario(Horario horario) {
        if (existeConflictoDeHorario(horario)) {
            throw new ConflictException("El horario se solapa con otro existente");
        }
        return horarioRepository.save(horario);
    }

    @Override
    public List<Horario> obtenerHorariosPorUsuario(Long usuarioId) {
        return horarioRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public void eliminarHorario(Long horarioId) {
        horarioRepository.deleteById(horarioId);
    }

    @Override
    public void eliminarTodosPorUsuario(Long usuarioId) {
        List<Horario> horarios = horarioRepository.findByUsuarioId(usuarioId);
        horarioRepository.deleteAll(horarios);
    }

    @Override
    public boolean existeConflictoDeHorario(Horario horario) {
        List<Horario> solapados = horarioRepository.findHorariosSolapados(
            horario.getUsuario().getId(),
            horario.getHoraEntrada(),
            horario.getHoraSalida()
        );

        if (horario.getId() != null) {
            solapados.removeIf(h -> h.getId().equals(horario.getId()));
        }

        return !solapados.isEmpty();
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @Override
    public List<Horario> obtenerTodosHorariosConUsuario() {
        return horarioRepository.obtenerTodosConUsuario();
    }
    
    public Horario obtenerHorarioDelDia(Usuario usuario, LocalDate fecha) {
        List<Horario> horarios = horarioRepository.obtenerHorariosActivosParaFecha(usuario.getId(), fecha);
        return horarios.isEmpty() ? null : horarios.get(0);
    }

   


     
    @Override
    public Integer obtenerToleranciaEntradaPorFecha(Long usuarioId, LocalDate fecha) {
        List<Horario> horarios = horarioRepository.obtenerHorariosActivosParaFecha(usuarioId, fecha);

        if (horarios.isEmpty()) {
            throw new RuntimeException("No hay configuración de horario para el usuario en la fecha dada.");
        }

        Horario horario = horarios.get(0); // Puedes ajustar si hay más de uno
        return horario.getToleranciaEntrada(); // Suponiendo que es tipo `Integer`
    }
    
    @Override
    public void actualizarTolerancia(int entrada, int salida) {
        Horario config = horarioRepository.findByEsConfiguracionTrue()
            .orElseGet(() -> {
                Horario nuevo = new Horario();
                nuevo.setHoraEntrada(LocalTime.of(8, 0)); // valor por defecto
                nuevo.setHoraSalida(LocalTime.of(17, 0));
                nuevo.setFechaInicio(LocalDate.now());
                nuevo.setFechaFin(LocalDate.now());
                nuevo.setEsConfiguracion(true);
                nuevo.setUsuario(usuarioService.obtenerUsuarioPorId(1L)); // 👈 asegurarse de que el usuario exista
                return nuevo;
            });

        config.setToleranciaEntrada(entrada);
        config.setToleranciaSalida(salida);
        horarioRepository.save(config);
    }


    @Override
    public ToleranciaDTO obtenerTolerancia() {
        Horario config = horarioRepository.findByEsConfiguracionTrue()
            .orElseThrow(() -> new RuntimeException("Configuración de tolerancia no encontrada"));

        ToleranciaDTO dto = new ToleranciaDTO();
        dto.setToleranciaEntrada(config.getToleranciaEntrada());
        dto.setToleranciaSalida(config.getToleranciaSalida());
        return dto;
    }
    
    
    
    @Override
    public List<Horario> obtenerHorariosDelDia(Long usuarioId, LocalDate fecha) {
        return horarioRepository.findByUsuarioIdAndFecha(usuarioId, fecha);
    }

    
    @Override
    public List<HorasTrabajadasDTO> calcularHorasTrabajadasPorUsuario() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<HorasTrabajadasDTO> resultado = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            List<Horario> horarios = horarioRepository.findByUsuarioId(usuario.getId());

            double horasNormales = 0.0;
            double horasExtras = 0.0;

            // Aquí irá la lógica para sumar horas normales y extras

            HorasTrabajadasDTO dto = new HorasTrabajadasDTO(
                usuario.getId(),
                usuario.getNombre(),
                horasNormales,
                horasExtras
            );

            resultado.add(dto);
        }

        return resultado;
    }


  /*  @Override
    public List<HorasTrabajadasDTO> calcularHorasTrabajadasPorMes(String mes) {
        YearMonth yearMonth = YearMonth.parse(mes);
        LocalDate inicioMes = yearMonth.atDay(1);
        LocalDate finMes = yearMonth.atEndOfMonth();

        List<Horario> horarios = horarioRepository.findByFechaInicioBetween(inicioMes, finMes);

        Map<Usuario, List<Horario>> agrupados = horarios.stream()
            .collect(Collectors.groupingBy(Horario::getUsuario));

        List<HorasTrabajadasDTO> resultado = new ArrayList<>();

        for (Map.Entry<Usuario, List<Horario>> entry : agrupados.entrySet()) {
            Usuario usuario = entry.getKey();
            List<Horario> lista = entry.getValue();

            Double horasPorDia = obtenerHorasNormalesPorDia();


            Map<LocalDate, List<Horario>> porDia = lista.stream()
                .collect(Collectors.groupingBy(Horario::getFechaInicio));

            double horasNormales = 0;
            double horasExtras = 0;

            for (Map.Entry<LocalDate, List<Horario>> dia : porDia.entrySet()) {
                double totalDia = 0;

                for (Horario h : dia.getValue()) {
                    LocalTime entrada = h.getHoraEntrada();
                    LocalTime salida = h.getHoraSalida();
                    if (entrada != null && salida != null) {
                        totalDia += Duration.between(entrada, salida).toMinutes() / 60.0;
                    }
                }

                if (totalDia <= horasPorDia) {
                    horasNormales += totalDia;
                } else {
                    horasNormales += horasPorDia;
                    horasExtras += totalDia - horasPorDia;
                }
            }

            resultado.add(new HorasTrabajadasDTO(
                usuario.getId(),
                usuario.getNombre(),
                Math.round(horasNormales * 100.0) / 100.0,
                Math.round(horasExtras * 100.0) / 100.0
            ));
        }

        return resultado;
    }*/
    
    
  /*  @Override
    public List<HorasTrabajadasDTO> calcularHorasTrabajadasPorMes(String mes) {
        YearMonth yearMonth = YearMonth.parse(mes);
        LocalDate inicioMes = yearMonth.atDay(1);
        LocalDate finMes = yearMonth.atEndOfMonth();

        List<Horario> horarios = horarioRepository.findByFechaInicioBetween(inicioMes, finMes);

        Map<Usuario, List<Horario>> agrupados = horarios.stream()
            .collect(Collectors.groupingBy(Horario::getUsuario));

        List<HorasTrabajadasDTO> resultado = new ArrayList<>();
        Double horasPorDia = obtenerHorasNormalesPorDia();

        for (Map.Entry<Usuario, List<Horario>> entry : agrupados.entrySet()) {
            Usuario usuario = entry.getKey();
            List<Horario> lista = entry.getValue();

            Map<LocalDate, List<Horario>> porDia = lista.stream()
                .collect(Collectors.groupingBy(Horario::getFechaInicio));

            double horasNormales = 0;
            double horasExtras = 0;

            for (Map.Entry<LocalDate, List<Horario>> dia : porDia.entrySet()) {
                double totalDia = 0;

                for (Horario h : dia.getValue()) {
                    LocalTime entradaTime = h.getHoraEntrada();
                    LocalTime salidaTime = h.getHoraSalida();
                    LocalDate fecha = h.getFechaInicio();

                    if (entradaTime != null && salidaTime != null) {
                        LocalDateTime entrada = LocalDateTime.of(fecha, entradaTime);
                        LocalDateTime salida = LocalDateTime.of(fecha, salidaTime);

                        if (salida.isAfter(entrada)) {
                            double horas = Duration.between(entrada, salida).toMinutes() / 60.0;
                            totalDia += horas;
                            
                            System.out.println("👤 " + usuario.getNombre() +
                            	    " | Día: " + fecha +
                            	    " | Entrada: " + entradaTime +
                            	    " | Salida: " + salidaTime +
                            	    " | Horas: " + (Duration.between(entrada, salida).toMinutes() / 60.0));

                            
                        }
                    }
                }

                if (totalDia <= horasPorDia) {
                    horasNormales += totalDia;
                } else {
                    horasNormales += horasPorDia;
                    horasExtras += totalDia - horasPorDia;
                }
            }

            resultado.add(new HorasTrabajadasDTO(
                usuario.getId(),
                usuario.getNombre(),
                Math.round(horasNormales * 100.0) / 100.0,
                Math.round(horasExtras * 100.0) / 100.0
            ));
        }

        return resultado;
    }*/

    @Override
    public List<HorasTrabajadasDTO> calcularHorasTrabajadasPorMes(String mes) {
        YearMonth yearMonth = YearMonth.parse(mes);
        LocalDate inicioMes = yearMonth.atDay(1);
        LocalDate finMes = yearMonth.atEndOfMonth();

        List<Asistencia> asistencias = asistenciaRepository.findByFechaHoraBetween(
            inicioMes.atStartOfDay(), finMes.atTime(LocalTime.MAX)
        );

        Map<Usuario, List<Asistencia>> agrupadas = asistencias.stream()
            .collect(Collectors.groupingBy(Asistencia::getUsuario));

        Double horasPorDia = obtenerHorasNormalesPorDia();
        List<HorasTrabajadasDTO> resultado = new ArrayList<>();

        for (Map.Entry<Usuario, List<Asistencia>> entry : agrupadas.entrySet()) {
            Usuario usuario = entry.getKey();
            List<Asistencia> registros = entry.getValue();

            Map<LocalDate, List<Asistencia>> porDia = registros.stream()
                .collect(Collectors.groupingBy(a -> a.getFechaHora().toLocalDate()));

            double horasNormales = 0;
            double horasExtras = 0;

            for (Map.Entry<LocalDate, List<Asistencia>> dia : porDia.entrySet()) {
                List<Asistencia> eventos = dia.getValue();

                LocalDateTime entrada = eventos.stream()
                    .filter(e -> "ENTRADA".equalsIgnoreCase(e.getTipo()))
                    .map(e -> e.getFechaHora().toLocalDateTime()) // ← conversión aquí
                    .findFirst()
                    .orElse(null);

                LocalDateTime salida = eventos.stream()
                    .filter(e -> "SALIDA".equalsIgnoreCase(e.getTipo()))
                    .map(e -> e.getFechaHora().toLocalDateTime()) // ← conversión aquí
                    .reduce((first, second) -> second)
                    .orElse(null);


                if (entrada != null && salida != null && salida.isAfter(entrada)) {
                    double horas = Duration.between(entrada, salida).toMinutes() / 60.0;

                    if (horas <= horasPorDia) {
                        horasNormales += horas;
                    } else {
                        horasNormales += horasPorDia;
                        horasExtras += horas - horasPorDia;
                    }

                    // 👀 Log para depuración
                    System.out.println("👤 " + usuario.getNombre() +
                        " | Día: " + dia.getKey() +
                        " | Entrada: " + entrada +
                        " | Salida: " + salida +
                        " | Horas: " + horas);
                }
            }

            resultado.add(new HorasTrabajadasDTO(
                usuario.getId(),
                usuario.getNombre(),
                Math.round(horasNormales * 100.0) / 100.0,
                Math.round(horasExtras * 100.0) / 100.0
            ));
        }

        return resultado;
    }

    
    
    @Override
    public Double obtenerHorasNormalesPorDia() {
        return horarioRepository.findByEsConfiguracionTrue()
            .map(h -> (double) h.getHorasNormalesPorDia())
            .orElse(8.0); // valor por defecto si no hay configuración
    }

    @Override
    @Transactional
    public void actualizarHorasNormalesPorDia(Double valor) {
        if (valor == null || valor < 1 || valor > 24) {
            throw new IllegalArgumentException("Las horas normales por día deben estar entre 1 y 24.");
        }

        Horario config = horarioRepository.findByEsConfiguracionTrue()
            .orElseGet(() -> {
                Horario nuevo = new Horario();
                nuevo.setHoraEntrada(LocalTime.of(8, 0));
                nuevo.setHoraSalida(LocalTime.of(17, 0));
                nuevo.setFechaInicio(LocalDate.now());
                nuevo.setFechaFin(LocalDate.now());
                nuevo.setEsConfiguracion(true);
                nuevo.setUsuario(usuarioService.obtenerUsuarioPorId(1L)); // Asegúrate que el usuario exista
                return nuevo;
            });

        config.setHorasNormalesPorDia(valor.intValue());
        horarioRepository.save(config);
    }




}
