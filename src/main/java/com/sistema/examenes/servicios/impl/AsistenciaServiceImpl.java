/* ok con un solo registro package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.repositorios.AsistenciaRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import com.sistema.examenes.servicios.AsistenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.LocalDate;
import java.util.List;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario obtenerUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return usuarioRepository.findByUsername(auth.getName());
    }

    @Override
    public void registrarEntrada() {
        Usuario usuario = obtenerUsuarioAutenticado();
        LocalDateTime ahora = LocalDateTime.now();

        boolean tieneEntradaActiva = asistenciaRepository
            .findFirstByUsuarioAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuario)
            .isPresent();

        if (!tieneEntradaActiva) {
            Asistencia nueva = new Asistencia(ahora, usuario);
            asistenciaRepository.save(nueva);
        }
    }

    @Override
    public void registrarSalida() {
        Usuario usuario = obtenerUsuarioAutenticado();
        asistenciaRepository
            .findFirstByUsuarioAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuario)
            .ifPresent(asistencia -> {
                asistencia.setHoraSalida(LocalDateTime.now());
                asistenciaRepository.save(asistencia);
            });
    }

    @Override
    public List<Asistencia> obtenerAsistenciasDelMes(Usuario usuario, YearMonth mes) {
        LocalDateTime inicio = mes.atDay(1).atStartOfDay();
        LocalDateTime fin = mes.atEndOfMonth().atTime(23, 59, 59);
        return asistenciaRepository.findByUsuarioAndHoraEntradaBetween(usuario, inicio, fin);
    }

    @Override
    public Asistencia registrarHora(String username, String tipoRegistro) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        LocalDateTime ahora = LocalDateTime.now();

        if ("ENTRADA".equalsIgnoreCase(tipoRegistro)) {
            boolean tieneEntradaActiva = asistenciaRepository
                .findFirstByUsuarioAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuario)
                .isPresent();

            if (tieneEntradaActiva) {
                throw new RuntimeException("Ya existe una entrada activa sin salida.");
            }

            Asistencia nueva = new Asistencia(ahora, usuario);
            return asistenciaRepository.save(nueva);

        } else if ("SALIDA".equalsIgnoreCase(tipoRegistro)) {
            return asistenciaRepository
                .findFirstByUsuarioAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuario)
                .map(asistencia -> {
                    asistencia.setHoraSalida(ahora);
                    return asistenciaRepository.save(asistencia);
                })
                .orElseThrow(() -> new RuntimeException("No se encontró una entrada activa para registrar salida."));
        }

        throw new RuntimeException("Tipo de registro inválido. Usa 'ENTRADA' o 'SALIDA'.");
    }

    @Override
    public Object obtenerAsistencia(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return asistenciaRepository.findByUsuarioOrderByHoraEntradaDesc(usuario);
    }
    
    @Override
    public long calcularMinutosTrabajados(String username, YearMonth mes) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado.");

        List<Asistencia> asistencias = obtenerAsistenciasDelMes(usuario, mes);

        return asistencias.stream()
            .filter(a -> a.getHoraEntrada() != null && a.getHoraSalida() != null)
            .mapToLong(a -> java.time.Duration.between(a.getHoraEntrada(), a.getHoraSalida()).toMinutes())
            .sum();
    }

}

*/

package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.dto.AsistenciaDTO;
import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Contacto;
import com.sistema.examenes.entidades.Evento;
import com.sistema.examenes.entidades.Horario;
import com.sistema.examenes.entidades.QrToken;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.repositorios.AsistenciaRepository;
import com.sistema.examenes.repositorios.ContactoRepository;
import com.sistema.examenes.repositorios.EventoRepository;
import com.sistema.examenes.repositorios.HorarioRepository;
import com.sistema.examenes.repositorios.QrTokenRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import com.sistema.examenes.servicios.AsistenciaService;
import com.sistema.examenes.servicios.QrTokenEventoService;
import com.sistema.examenes.servicios.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private QrTokenRepository qrTokenRepository;

    @Autowired
    private UsuarioService usuarioService;
    
    //crm
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private QrTokenEventoService qrTokenEventoService;

    @Override
    public void registrarEvento(String tipo, Usuario usuario, OffsetDateTime fechaHora) {
        LocalDate hoy = fechaHora.toLocalDate();
        LocalTime horaActual = fechaHora.toLocalTime();

        List<Horario> turnos = horarioRepository.obtenerHorariosActivosParaFechaAsistencia(usuario, hoy);
        if (turnos.isEmpty()) {
            throw new IllegalStateException("No tienes un horario asignado para el día de hoy.");
        }

        boolean esTardia = "ENTRADA".equalsIgnoreCase(tipo) &&
            turnos.stream().anyMatch(h -> horaActual.isAfter(h.getHoraEntrada().plusMinutes(10)));

        List<Asistencia> historial = asistenciaRepository.findByUsuarioOrderByFechaHoraDesc(usuario);
        Asistencia ultimoEventoHoy = historial.stream()
            .filter(a -> a.getFechaHora().toLocalDate().equals(hoy))
            .findFirst()
            .orElse(null);

        if (ultimoEventoHoy != null && tipo.equalsIgnoreCase(ultimoEventoHoy.getTipo())) {
            throw new IllegalStateException("Ya existe una " + tipo + " registrada previamente. No puede repetir el evento.");
        }

        boolean tieneEntrada = historial.stream().anyMatch(a ->
            "ENTRADA".equalsIgnoreCase(a.getTipo()) &&
            a.getFechaHora().toLocalDate().equals(hoy)
        );

        if ("SALIDA".equalsIgnoreCase(tipo) && !tieneEntrada) {
            throw new IllegalStateException("No se puede registrar SALIDA sin una ENTRADA previa.");
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setFechaHora(fechaHora);
        asistencia.setTipo(tipo.toUpperCase());
        asistencia.setUsuario(usuario);
        asistencia.setEstado(esTardia ? "TARDÍA" : "NORMAL");

        asistenciaRepository.save(asistencia);
    }

    @Override
    public List<Asistencia> obtenerHistorial(Usuario usuario) {
        return asistenciaRepository.findByUsuarioOrderByFechaHoraDesc(usuario);
    }

    @Override
    public List<Asistencia> obtenerTodas() {
        return asistenciaRepository.findAll();
    }

    @Override
    public List<Asistencia> filtrarAsistencias(String nombre, String fecha) {
        if ((nombre == null || nombre.trim().isEmpty()) && (fecha == null || fecha.trim().isEmpty())) {
            return obtenerTodas();
        }
        return asistenciaRepository.buscarPorNombreYFecha(
            (nombre == null || nombre.isBlank()) ? null : nombre,
            (fecha == null || fecha.isBlank()) ? null : fecha
        );
    }

    @Override
    public void actualizarAsistencia(Long id, AsistenciaDTO dto) {
        Asistencia asistencia = asistenciaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));

        asistencia.setFechaHora(dto.getFechaHora());
        asistencia.setTipo(dto.getTipo());

        asistenciaRepository.save(asistencia);
    }

    @Override
    public void eliminarAsistencia(Long id) {
        if (!asistenciaRepository.existsById(id)) {
            throw new IllegalArgumentException("Asistencia no encontrada con ID: " + id);
        }
        asistenciaRepository.deleteById(id);
    }

    @Override
    public boolean registrarDesdeQr(String username, String tipoEvento) {
        if (username == null || tipoEvento == null) return false;

        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) return false;

        OffsetDateTime ahora = OffsetDateTime.now(ZoneOffset.UTC);

        List<Asistencia> historial = asistenciaRepository.findByUsuarioOrderByFechaHoraDesc(usuario);
        Asistencia ultima = historial.stream().findFirst().orElse(null);

        boolean yaRegistradoHoy = historial.stream().anyMatch(a ->
            tipoEvento.equalsIgnoreCase(a.getTipo()) &&
            a.getFechaHora().toLocalDate().equals(ahora.toLocalDate())
        );
        if (yaRegistradoHoy) return false;

        if (tipoEvento.equalsIgnoreCase("SALIDA")) {
            boolean tieneEntradaPrevia = historial.stream().anyMatch(a ->
                a.getTipo().equalsIgnoreCase("ENTRADA") &&
                a.getFechaHora().toLocalDate().equals(ahora.toLocalDate())
            );
            if (!tieneEntradaPrevia) return false;
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setFechaHora(ahora);
        asistencia.setTipo(tipoEvento.toUpperCase());
        asistencia.setUsuario(usuario);
        asistencia.setOrigen("kiosk");

        asistenciaRepository.save(asistencia);
        return true;
    }

    @Override
    public boolean registrarEventoGenerico(String username, String tipoEvento) {
        if (username == null || tipoEvento == null) return false;

        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) return false;

        OffsetDateTime ahora = OffsetDateTime.now(ZoneOffset.UTC);

        List<Asistencia> historial = asistenciaRepository.findByUsuarioOrderByFechaHoraDesc(usuario);
        boolean yaRegistradoHoy = historial.stream().anyMatch(a ->
            tipoEvento.equalsIgnoreCase(a.getTipo()) &&
            a.getFechaHora().toLocalDate().equals(ahora.toLocalDate())
        );
        if (yaRegistradoHoy) return false;

        if (tipoEvento.equalsIgnoreCase("SALIDA")) {
            boolean tieneEntrada = historial.stream().anyMatch(a ->
                a.getTipo().equalsIgnoreCase("ENTRADA") &&
                a.getFechaHora().toLocalDate().equals(ahora.toLocalDate())
            );
            if (!tieneEntrada) return false;
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setUsuario(usuario);
        asistencia.setTipo(tipoEvento.toUpperCase());
        asistencia.setFechaHora(ahora);
        asistencia.setOrigen("generico");

        asistenciaRepository.save(asistencia);
        return true;
    }

    @Override
    public boolean validarTokenQr(String token) {
        Optional<QrToken> tokenEncontrado = qrTokenRepository.findByToken(token);
        return tokenEncontrado.map(QrToken::estaVigente).orElse(false);
    }

    @Override
    public String registrarDesdeTokenQr(String token) {
        Optional<QrToken> tokenEncontrado = qrTokenRepository.findByToken(token);
        if (tokenEncontrado.isEmpty()) return "QR no encontrado.";

        QrToken qr = tokenEncontrado.get();
        if (!qr.estaVigente()) return "QR expirado o ya usado.";

        String username = qr.getToken().split("-")[0];
        Usuario usuario = usuarioService.obtenerUsuario(username);
        if (usuario == null) return "Usuario no encontrado para este QR.";

        OffsetDateTime ahora = OffsetDateTime.now(ZoneOffset.UTC);

        Asistencia asistencia = new Asistencia();
        asistencia.setTipo(qr.getTipoEvento());
        asistencia.setUsuario(usuario);
        asistencia.setFechaHora(ahora);
        asistencia.setOrigen("qr");

        asistenciaRepository.save(asistencia);

        qr.setUsado(true);
        qrTokenRepository.save(qr);

        return "QR registrado con éxito para " + username + " (" + qr.getTipoEvento().toUpperCase() + ")";
    }

    @Override
    public double calcularHorasTrabajadasPorMes(String username, YearMonth mes) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado: " + username);

        LocalDate inicio = mes.atDay(1);
        LocalDate fin = mes.atEndOfMonth();

        List<Asistencia> asistencias = asistenciaRepository.findByUsuarioOrderByFechaHoraDesc(usuario);

        List<Asistencia> delMes = asistencias.stream()
            .filter(a -> {
                LocalDate fecha = a.getFechaHora().toLocalDate();
                return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
            })
            .sorted(Comparator.comparing(Asistencia::getFechaHora))
            .collect(Collectors.toList());

        Map<LocalDate, List<Asistencia>> porDia = delMes.stream()
            .collect(Collectors.groupingBy(a -> a.getFechaHora().toLocalDate()));

        long totalMinutos = 0;

        for (Map.Entry<LocalDate, List<Asistencia>> entry : porDia.entrySet()) {
            List<Asistencia> eventos = entry.getValue();

            OffsetDateTime entrada = eventos.stream()
                .filter(e -> "ENTRADA".equalsIgnoreCase(e.getTipo()))
                .map(Asistencia::getFechaHora)
                .findFirst()
                .orElse(null);

            OffsetDateTime salida = eventos.stream()
                .filter(e -> "SALIDA".equalsIgnoreCase(e.getTipo()))
                .map(Asistencia::getFechaHora)
                .reduce((first, second) -> second)
                .orElse(null);

            if (entrada != null && salida != null && salida.isAfter(entrada)) {
                totalMinutos += Duration.between(entrada, salida).toMinutes();
            }
        }

        return totalMinutos / 60.0;
    }

	
    @Override
    public void registrarEvento(String tipo, Usuario usuario) {
        registrarEvento(tipo, usuario, OffsetDateTime.now(ZoneOffset.UTC));
    }

// CMR
    @Override
    public Asistencia registrarAsistenciaPorQr(Long eventoId, String token, Long contactoId) {
        if (!qrTokenEventoService.validarToken(token, eventoId)) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }

        Evento evento = eventoRepository.findById(eventoId)
            .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));

        Contacto contacto = contactoRepository.findById(contactoId)
            .orElseThrow(() -> new IllegalArgumentException("Contacto no encontrado"));

        List<Asistencia> previas = asistenciaRepository.findByEventoIdAndContactoId(eventoId, contactoId);
        if (!previas.isEmpty()) {
            throw new IllegalStateException("Ya se registró asistencia para este evento");
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setFechaHora(OffsetDateTime.now(ZoneOffset.UTC));
        asistencia.setTipo("CHECKIN");
        asistencia.setEvento(evento);
        asistencia.setContacto(contacto);
        asistencia.setCanal("QR_DINAMICO");
        asistencia.setTokenEscaneado(token);
        asistencia.setEstado("NORMAL");
        asistencia.setOrigen("web");

        return asistenciaRepository.save(asistencia);
    }

    @Override
    public Asistencia registrarAsistenciaAnonima(Long eventoId, String token) {
        if (!qrTokenEventoService.validarToken(token, eventoId)) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }

        Evento evento = eventoRepository.findById(eventoId)
            .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));

        Asistencia asistencia = new Asistencia();
        asistencia.setFechaHora(OffsetDateTime.now(ZoneOffset.UTC));
        asistencia.setTipo("CHECKIN");
        asistencia.setEvento(evento);
        asistencia.setContacto(null); // anónimo
        asistencia.setCanal("QR_DINAMICO");
        asistencia.setTokenEscaneado(token);
        asistencia.setEstado("NORMAL");
        asistencia.setOrigen("web");

        return asistenciaRepository.save(asistencia);
    }

    @Override
    public boolean yaRegistrado(Long eventoId, Long contactoId) {
        return asistenciaRepository.existsByEventoIdAndContactoId(eventoId, contactoId);
    }

    

}
