package com.sistema.examenes.controladores;

//import java.awt.PageAttributes.MediaType;
import org.springframework.http.MediaType;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import javax.annotation.Resource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sistema.examenes.entidades.Rol;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.entidades.UsuarioRol;
import com.sistema.examenes.servicios.UsuarioService;



@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/****************IMPORTANTE SI QUIERO AGREGAR UN ADMIN activar (2)  y desactivar (1) **********************/	
	
	//  (1) este es el metodo original, se crean solo usuarios
	
	@PostMapping("/")         
	public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception {
		usuario.setPerfil("defaul.png");
		
		usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));
		
		Set<UsuarioRol> usuarioRoles = new HashSet<>();
		
		Rol rol = new Rol();
		rol.setRolId(2L);
		rol.setNombre("NORMAL");
		
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setUsuario(usuario);
		usuarioRol.setRol(rol);
		
		usuarioRoles.add(usuarioRol);
		return usuarioService.guardarUsuario(usuario, usuarioRoles);
		
	}
	
	
	
	/*
	  
	 // (2) En este ejemplo, cada usuario que se cree recibirá tanto el rol "NORMAL" como el rol "ADMIN".
	@PostMapping("/")   
	public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception {
	    usuario.setPerfil("default.png");
	    usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));

	    Set<UsuarioRol> usuarioRoles = new HashSet<>();

	    // Rol NORMAL
	    Rol rolNormal = new Rol();
	    rolNormal.setRolId(2L);
	    rolNormal.setNombre("NORMAL");

	    UsuarioRol usuarioRolNormal = new UsuarioRol();
	    usuarioRolNormal.setUsuario(usuario);
	    usuarioRolNormal.setRol(rolNormal);

	    usuarioRoles.add(usuarioRolNormal);

	    // Rol ADMIN (ejemplo de segundo rol)
	    Rol rolAdmin = new Rol();
	    rolAdmin.setRolId(1L);
	    rolAdmin.setNombre("ADMIN");

	    UsuarioRol usuarioRolAdmin = new UsuarioRol();
	    usuarioRolAdmin.setUsuario(usuario);
	    usuarioRolAdmin.setRol(rolAdmin);

	    usuarioRoles.add(usuarioRolAdmin);

	    return usuarioService.guardarUsuario(usuario, usuarioRoles);
	}
*/
	
	
	
	
	@GetMapping("/{username}")
	public Usuario obtenerUsuario(@PathVariable("username") String username) {
		return usuarioService.obtenerUsuario(username);
	}
	
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/")
	public List<Usuario> obtenerUsuarios() {
	    return usuarioService.obtenerTodosLosUsuarios();
	}
	
	// Nuevo endpoint de búsqueda
    @GetMapping("/buscar/{username}")
    public List<Usuario> buscarUsuarios(@PathVariable String username) {
        return usuarioService.buscarUsuariosPorNombre(username);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }
    
    //asistevcia
    @PutMapping("/{id}/hora-entrada")
    public ResponseEntity<?> actualizarHoraEntrada(@PathVariable Long id, @RequestBody Map<String, String> request) {
        LocalDateTime horaEntrada = LocalDateTime.parse(request.get("hora"));
        usuarioService.actualizarHoraEntrada(id, horaEntrada);
        return ResponseEntity.ok("Hora de entrada registrada correctamente");
    }

    @PutMapping("/{id}/hora-salida")
    public ResponseEntity<?> actualizarHoraSalida(@PathVariable Long id, @RequestBody Map<String, String> request) {
        LocalDateTime horaSalida = LocalDateTime.parse(request.get("hora"));
        usuarioService.actualizarHoraSalida(id, horaSalida);
        return ResponseEntity.ok("Hora de salida registrada correctamente");
    }

    //---------------------------------------------------------------------------------------------------
    @PutMapping("/asistencia/entrada")
    public ResponseEntity<?> marcarHoraEntrada() {
        usuarioService.registrarHoraEntradaActual();
        return ResponseEntity.ok("Hora de entrada registrada para el usuario actual");
    }

    @PutMapping("/asistencia/salida")
    public ResponseEntity<?> marcarHoraSalida() {
        usuarioService.registrarHoraSalidaActual();
        return ResponseEntity.ok("Hora de salida registrada para el usuario actual");
    }

    //---------------------------------------------------------------------------------------------------

	// para la foto  no lo uso
    @PostMapping(value = "/", consumes = { "multipart/form-data" })
    public ResponseEntity<?> guardarUsuarioConImagen(
        @RequestPart("user") Usuario usuario,
        @RequestPart(value = "foto", required = false) MultipartFile foto) throws Exception {

        usuario.setPerfil("default.png");
        usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));

        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        Rol rol = new Rol();
        rol.setRolId(2L);
        rol.setNombre("NORMAL");

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        usuarioRoles.add(usuarioRol);

        Usuario usuarioGuardado = usuarioService.guardarUsuarioConFoto(usuario, usuarioRoles, foto);

        return ResponseEntity.ok(usuarioGuardado);
    }

    // igual para la foto no lo uso
    @GetMapping("/uploads/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) throws MalformedURLException {
        Path ruta = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = new UrlResource(ruta.toUri());

        if (!recurso.exists() || !recurso.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)  // usamos fijo image/jpeg por simplicidad
            .body(recurso);
    }


    
	
}// fin
