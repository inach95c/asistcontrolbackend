package com.sistema.examenes.controladores;

import com.sistema.examenes.configuraciones.JwtUtils;
import com.sistema.examenes.dto.HorasTrabajadasDTO;
import com.sistema.examenes.dto.ToleranciaDTO;
import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Horario;
import com.sistema.examenes.entidades.HorarioMasivoRequest;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.servicios.AsistenciaService;
import com.sistema.examenes.servicios.HorarioService;
import com.sistema.examenes.servicios.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/horarios")
@CrossOrigin("*")  
//@CrossOrigin(origins = "http://localhost:4200")

public class HorarioController {
	
	 @Autowired
	    private AsistenciaService asistenciaService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping
    public ResponseEntity<Horario> crearHorario(@RequestBody Horario horario) {
        return ResponseEntity.ok(horarioService.crearHorario(horario));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Horario>> listarHorariosUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(horarioService.obtenerHorariosPorUsuario(usuarioId));
    }

    @DeleteMapping("/{horarioId}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long horarioId) {
        horarioService.eliminarHorario(horarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}/todos")
    public ResponseEntity<Void> eliminarTodosHorariosUsuario(@PathVariable Long usuarioId) {
        horarioService.eliminarTodosPorUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{horarioId}")
    public ResponseEntity<Horario> actualizarHorario(@PathVariable Long horarioId, @RequestBody Horario horario) {
        horario.setId(horarioId);

        if (horarioService.existeConflictoDeHorario(horario)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(horarioService.crearHorario(horario));
    }

    @PostMapping("/asignar-multiple")
    public ResponseEntity<?> asignarHorarioMultiple(@RequestBody HorarioMasivoRequest request) {
        List<Long> usuariosIds = request.getUsuariosIds();
        Horario base = request.getHorario();
        boolean dividirPorDias = request.isDividirPorDias();
        List<String> errores = new ArrayList<>();

        for (Long id : usuariosIds) {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

            if (dividirPorDias && base.getFechaInicio() != null && base.getFechaFin() != null) {
                LocalDate desde = base.getFechaInicio();
                LocalDate hasta = base.getFechaFin();
                for (LocalDate fecha = desde; !fecha.isAfter(hasta); fecha = fecha.plusDays(1)) {
                    Horario diario = clonarHorario(base, usuario, fecha);
                    diario.setFechaInicio(fecha);
                    diario.setFechaFin(fecha);
                    try {
                        horarioService.crearHorario(diario);
                    } catch (Exception e) {
                        errores.add("Usuario ID " + id + ": " + e.getMessage());
                    }
                }
            } else {
                Horario bloque = clonarHorario(base, usuario, null);
                bloque.setFechaInicio(base.getFechaInicio());
                bloque.setFechaFin(base.getFechaFin());
                try {
                    horarioService.crearHorario(bloque);
                } catch (Exception e) {
                    errores.add("Usuario ID " + id + ": " + e.getMessage());
                }
            }
        }

        if (!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errores);
        }

        return ResponseEntity.ok("Horarios asignados correctamente");
    }

  /*  @GetMapping("/listado-completo")
    public ResponseEntity<List<Horario>> obtenerTodosConUsuario() {
        return ResponseEntity.ok(horarioService.obtenerTodosHorariosConUsuario());
    }
  */
    
    @GetMapping("/listado-completo")
    public ResponseEntity<?> obtenerTodosConUsuario() {
        try {
            List<Horario> lista = horarioService.obtenerTodosHorariosConUsuario();
            System.out.println("✅ Horarios cargados: " + lista.size());
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace(); // 👈 Mira tu consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    // 👇 Método privado para clonar un horario base
    private Horario clonarHorario(Horario base, Usuario usuario, LocalDate fecha) {
        Horario nuevo = new Horario(
            base.getHoraEntrada(),
            base.getHoraSalida(),
            usuario
        );
//        nuevo.setTurno(base.getTurno());

        if (fecha != null) {
            nuevo.setFechaInicio(fecha);
            nuevo.setFechaFin(fecha);
        }

        return nuevo;
    }
   
    // para resumen de horarios 
    @GetMapping("/horarios-por-usuario")
    public ResponseEntity<Map<Usuario, List<Horario>>> obtenerHorariosAgrupados() {
        List<Horario> horarios = horarioService.obtenerTodosHorariosConUsuario();
        Map<Usuario, List<Horario>> agrupados = horarios.stream()
            .collect(Collectors.groupingBy(Horario::getUsuario));
        return ResponseEntity.ok(agrupados);
    }

    @GetMapping("/actual")
    public ResponseEntity<?> obtenerHorarioDelDiaActual(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            String username = jwtUtils.extractUsername(jwt); // ✅ extrae el username del subject
            Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username); // 👈 nuevo método

            Horario horarioHoy = horarioService.obtenerHorarioDelDia(usuario, LocalDate.now());

            return horarioHoy != null
                ? ResponseEntity.ok(horarioHoy)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay horario para hoy");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: " + e.getMessage());
        }
    }
    
        
   


   
    
    
    @GetMapping("/tolerancia-entrada/{usuarioId}/{fecha}")
    public ResponseEntity<?> obtenerToleranciaEntrada(
            @PathVariable Long usuarioId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            Integer tolerancia = horarioService.obtenerToleranciaEntradaPorFecha(usuarioId, fecha);
            return ResponseEntity.ok(Map.of("toleranciaEntrada", tolerancia));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró tolerancia para ese usuario en la fecha indicada");
        }
    }
    
    
    @PostMapping("/tolerancia")
    public ResponseEntity<Void> guardarTolerancia(@RequestBody ToleranciaDTO dto) {
        horarioService.actualizarTolerancia(dto.getToleranciaEntrada(), dto.getToleranciaSalida());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tolerancia")
    public ResponseEntity<ToleranciaDTO> obtenerTolerancia() {
        ToleranciaDTO dto = horarioService.obtenerTolerancia();
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/actual-multiple")
    public ResponseEntity<?> obtenerHorariosDelDiaActual(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            String username = jwtUtils.extractUsername(jwt);
            Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);

            List<Horario> horariosHoy = horarioService.obtenerHorariosDelDia(usuario.getId(), LocalDate.now());

            return horariosHoy.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay horarios para hoy")
                : ResponseEntity.ok(horariosHoy);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: " + e.getMessage());
        }
    }


   /* @GetMapping("/horas-trabajadas")
    public ResponseEntity<List<HorasTrabajadasDTO>> obtenerHorasTrabajadasPorMes(
            @RequestParam("mes") String mes) {
        try {
            List<HorasTrabajadasDTO> resumen = horarioService.calcularHorasTrabajadasPorMes(mes);
            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }*/
    
    
    @GetMapping("/horas-trabajadas")
    public ResponseEntity<List<HorasTrabajadasDTO>> obtenerHorasTrabajadasPorMes(@RequestParam("mes") String mes) {
        try {
            List<HorasTrabajadasDTO> resumen = horarioService.calcularHorasTrabajadasPorMes(mes);

            if (resumen == null || resumen.isEmpty()) {
                System.out.println("⚠️ No se encontraron registros de asistencia para el mes: " + mes);
            } else {
                System.out.println("📦 Registros procesados: " + resumen.size());
            }

            return ResponseEntity.ok(resumen);

        } catch (Exception e) {
            System.out.println("❌ Error al calcular horas trabajadas para el mes " + mes + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ArrayList<>()); // ← devuelve lista vacía en caso de error
        }
    }
  
    
    
    @GetMapping("/horas-normales")
    public ResponseEntity<?> obtenerHorasNormalesPorDia() {
        try {
            Double valor = horarioService.obtenerHorasNormalesPorDia();
            return ResponseEntity.ok(Map.of("horasNormalesPorDia", valor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener horas normales por día: " + e.getMessage());
        }
    }

    @PostMapping("/horas-normales")
    public ResponseEntity<?> guardarHorasNormalesPorDia(@RequestBody Map<String, Double> datos) {
        try {
            Double valor = datos.get("horasNormalesPorDia");
            horarioService.actualizarHorasNormalesPorDia(valor);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar horas normales por día: " + e.getMessage());
        }
    }

    


    
}
