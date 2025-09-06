/* ok con un solo registro package com.sistema.examenes.controladores;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.servicios.AsistenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarHora(@RequestParam String username,
                                           @RequestParam String tipoRegistro) {
        if (!tipoRegistro.equalsIgnoreCase("ENTRADA") && !tipoRegistro.equalsIgnoreCase("SALIDA")) {
            return ResponseEntity.badRequest().body("Tipo de registro inválido: debe ser 'ENTRADA' o 'SALIDA'");
        }

        try {
            Asistencia asistencia = asistenciaService.registrarHora(username, tipoRegistro);
            return ResponseEntity.ok(asistencia);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> obtenerAsistencia(@PathVariable String username) {
        Object asistencia = asistenciaService.obtenerAsistencia(username);
        if (asistencia != null) {
            return ResponseEntity.ok(asistencia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/historial/{username}/{anio}/{mes}")
    public ResponseEntity<List<Asistencia>> obtenerHistorialMensual(@PathVariable String username,
                                                                    @PathVariable int anio,
                                                                    @PathVariable int mes) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        YearMonth periodo = YearMonth.of(anio, mes);
        List<Asistencia> historial = asistenciaService.obtenerAsistenciasDelMes(usuario, periodo);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/horas-trabajadas/{username}/{anio}/{mes}")
    public ResponseEntity<?> obtenerHorasTrabajadas(@PathVariable String username,
                                                    @PathVariable int anio,
                                                    @PathVariable int mes) {
        try {
            long totalMinutos = asistenciaService.calcularMinutosTrabajados(username, YearMonth.of(anio, mes));
            long horas = totalMinutos / 60;
            long minutos = totalMinutos % 60;

            String resumen = "Total trabajado en " + anio + "-" + String.format("%02d", mes) + ": " +
                             horas + " horas y " + minutos + " minutos.";

            return ResponseEntity.ok(resumen);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

*/

package com.sistema.examenes.controladores;


import com.sistema.examenes.dto.AsistenciaDTO;
import com.sistema.examenes.dto.EventoDTO;
import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Horario;
import com.sistema.examenes.entidades.QrToken;
import com.sistema.examenes.entidades.RegistroRespuestaDTO;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.repositorios.HorarioRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import com.sistema.examenes.servicios.AsistenciaService;
import com.sistema.examenes.servicios.QrTokenService;
import com.sistema.examenes.servicios.UsuarioService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@RestController
@RequestMapping("/asistencia")
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")                  //ORIGINAL
@CrossOrigin(origins = "*") 
//@CrossOrigin("*")
public class AsistenciaController {

	 
	
	@Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private QrTokenService qrTokenService;
    
    @Autowired
    private QrTokenService tokenService;
    
    @Autowired
    private HorarioRepository horarioRepository;





    @PostMapping
    public ResponseEntity<?> registrarAsistencia(@RequestParam String tipo, Principal principal) {
        Usuario usuario = usuarioService.obtenerUsuario(principal.getName());
        asistenciaService.registrarEvento(tipo, usuario);
        return ResponseEntity.ok("Evento registrado exitosamente.");
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Asistencia>> obtenerHistorial(Principal principal) {
        Usuario usuario = usuarioService.obtenerUsuario(principal.getName());
        List<Asistencia> historial = asistenciaService.obtenerHistorial(usuario);
        return ResponseEntity.ok(historial);
    }
    
    //admin
 //   @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/todas")
    public ResponseEntity<List<Asistencia>> listarTodas() {
        return ResponseEntity.ok(asistenciaService.obtenerTodas());
    }

  //  @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/filtrar")
    public ResponseEntity<List<Asistencia>> filtrar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String fecha) {
        return ResponseEntity.ok(asistenciaService.filtrarAsistencias(nombre, fecha));
    }
    
    
  
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarAsistencia(@PathVariable Long id, @RequestBody AsistenciaDTO dto) {
        asistenciaService.actualizarAsistencia(id, dto);
        return ResponseEntity.ok().build(); // 200 OK sin cuerpo
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsistencia(@PathVariable Long id) {
        asistenciaService.eliminarAsistencia(id);
        return ResponseEntity.noContent().build(); // o ok().build()
    }
   
    
 // Para escaneo desde QR
    @GetMapping("/registrar-por-qr")
    public ResponseEntity<String> registrarDesdeQr(
        @RequestParam String usuario,
        @RequestParam String tipoEvento
    ) {
        boolean registrado = asistenciaService.registrarDesdeQr(usuario, tipoEvento);

        Usuario userObj = usuarioRepository.findByUsername(usuario);
        String nombre = (userObj != null && userObj.getNombre() != null)
            ? userObj.getNombre()
            : usuario.toUpperCase();

        String horaRegistro = java.time.LocalTime.now().withNano(0).toString();
        String tipoTexto = tipoEvento != null ? tipoEvento.toUpperCase() : "DESCONOCIDO";
        String icono = tipoTexto.equals("ENTRADA") ? "🔓" : tipoTexto.equals("SALIDA") ? "🔒" : "📝";
        String redirectUrl = "http://localhost:4200";

        String mensajeHtml;

        if (registrado) {
            mensajeHtml = "<html><head><title>Registro exitoso</title>" +
                "<meta http-equiv='refresh' content='5;url=" + redirectUrl + "'>" +
                "<style>body{font-family:sans-serif;text-align:center;margin-top:50px;}</style>" +
                "</head><body>" +
                "<h2 style='color:green;'>" + icono + " Registro exitoso</h2>" +
                "<p><strong>" + nombre + "</strong>, tu evento <strong>" + tipoTexto +
                "</strong> fue registrado correctamente.</p>" +
                "<p>Hora del registro: <strong>" + horaRegistro + "</strong></p>" +
                "<p>Serás redirigido en 5 segundos...</p>" +
                "<a href='" + redirectUrl + "'>Haz clic aquí si no eres redirigido</a>" +
                "</body></html>";

            return ResponseEntity.ok().body(mensajeHtml);
        } else {
            mensajeHtml = "<html><head><title>Error de registro</title>" +
                "<style>body{font-family:sans-serif;text-align:center;margin-top:50px;}</style>" +
                "</head><body>" +
                "<h2 style='color:red;'>⚠️ Registro fallido</h2>" +
                "<p>No se pudo registrar el evento de <strong>" + nombre + "</strong>.</p>" +
                "<a href='" + redirectUrl + "'>Volver al inicio</a>" +
                "</body></html>";

            return ResponseEntity.badRequest().body(mensajeHtml);
        }
    }


    
    @PostMapping("/validar-qr")
    public ResponseEntity<RegistroRespuestaDTO> validarQr(@RequestBody Map<String, String> payload, Principal principal) {
        String token = payload.get("token");

        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<QrToken> qrTokenOpt = qrTokenService.validarToken(token);
        if (qrTokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        QrToken qrToken = qrTokenOpt.get();
        Usuario usuario = usuarioService.obtenerUsuario(principal.getName());
        asistenciaService.registrarEvento(qrToken.getTipoEvento(), usuario);
        qrTokenService.marcarComoUsado(token);

        RegistroRespuestaDTO dto = new RegistroRespuestaDTO();
        dto.setNombre(usuario.getNombre());
        dto.setEvento(qrToken.getTipoEvento());
        dto.setHora(LocalTime.now().withNano(0).toString());
        dto.setMensaje("Registro exitoso");

        return ResponseEntity.ok(dto);
    }
    
    
 // PARA GENERAR TOKEN, EL USUARIO SOLO SCANERARÁ SIN NECESIDAD DE AUTENTICARSE SOLUCION (2)
    @PostMapping("/registrar-por-jwt")
    public ResponseEntity<String> registrarPorJwt(@RequestBody Map<String, String> payload) {
        try {
            String token = payload.get("token");
            String fechaHoraStr = payload.get("fechaHora");

            Claims claims = Jwts.parser()
                .setSigningKey("super-clave-secreta-para-qr")
                .parseClaimsJws(token)
                .getBody();

            String username = claims.getSubject();
            String tipoEvento = claims.get("tipoEvento", String.class);

            Usuario usuario = usuarioService.obtenerUsuario(username);
            OffsetDateTime fechaHora = OffsetDateTime.parse(fechaHoraStr);

            asistenciaService.registrarEvento(tipoEvento, usuario, fechaHora);

            return ResponseEntity.ok("Registro automático exitoso: " + tipoEvento + " para " + username);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token inválido o error en el formato de fecha.");
        }
    }


    
    // PARA GENERAR TOKEN, EL USUARIO SOLO SCANERARÁ SIN NECESIDAD DE AUTENTICARSE SOLUCION, para validar toker personal (3) 
    @GetMapping("/asistencia/registrar-por-jwt")
    public ResponseEntity<?> registrarAsistencia(@RequestParam("token") String token) {
        String username = tokenService.validarJwtYExtraerUsuario(token);
        // Guardás el registro de asistencia usando ese usuario
        return ResponseEntity.ok("Asistencia registrada para " + username);
    }


 
    
    /*@PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrarEvento(@RequestBody EventoDTO dto) {
        Map<String, String> respuesta = new HashMap<>();

        try {
            Usuario usuario = usuarioService.obtenerUsuario(dto.getUsername());
            LocalDateTime ahora = LocalDateTime.now();
            LocalTime horaActual = ahora.toLocalTime();
            LocalDate hoy = ahora.toLocalDate();

            // Validar entrada tardía
            if ("ENTRADA".equalsIgnoreCase(dto.getTipo())) {
                List<Horario> turnos = horarioRepository.obtenerHorariosActivosParaFechaAsistencia(usuario, hoy);
                boolean entradaTardia = turnos.stream().anyMatch(h ->
                    horaActual.isAfter(h.getHoraEntrada().plusMinutes(10))
                );

                if (entradaTardia) {
                    respuesta.put("advertencia", "Registro fuera del margen de tolerancia (+10 minutos).");
                }
            }

            asistenciaService.registrarEvento(dto.getTipo(), usuario);
            respuesta.put("mensaje", "Evento registrado exitosamente.");
            return ResponseEntity.ok(respuesta);

        } catch (IllegalStateException e) {
            respuesta.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(respuesta);
        }
    }
*/
    
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrarEvento(@RequestBody EventoDTO dto) {
        Map<String, String> respuesta = new HashMap<>();

        try {
            Usuario usuario = usuarioService.obtenerUsuario(dto.getUsername());
            OffsetDateTime fechaHora = dto.getFechaHora();
            LocalDate hoy = fechaHora.toLocalDate();
            LocalTime horaActual = fechaHora.toLocalTime();

            if ("ENTRADA".equalsIgnoreCase(dto.getTipo())) {
                List<Horario> turnos = horarioRepository.obtenerHorariosActivosParaFechaAsistencia(usuario, hoy);
                boolean entradaTardia = turnos.stream().anyMatch(h ->
                    horaActual.isAfter(h.getHoraEntrada().plusMinutes(10))
                );
                if (entradaTardia) {
                    respuesta.put("advertencia", "Registro fuera del margen de tolerancia (+10 minutos).");
                }
            }

            asistenciaService.registrarEvento(dto.getTipo(), usuario, fechaHora);
            respuesta.put("mensaje", "Evento registrado exitosamente.");
            return ResponseEntity.ok(respuesta);

        } catch (IllegalStateException e) {
            respuesta.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> manejarError(IllegalStateException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(respuesta);
    }


    
    @GetMapping("/horas-trabajadas")
    public ResponseEntity<Double> calcularHoras(
        @RequestParam int anio,
        @RequestParam int mes,
        Principal principal
    ) {
        String username = principal.getName();
        YearMonth periodo = YearMonth.of(anio, mes);
        double horas = asistenciaService.calcularHorasTrabajadasPorMes(username, periodo);
        return ResponseEntity.ok(horas);
    }


    

}

