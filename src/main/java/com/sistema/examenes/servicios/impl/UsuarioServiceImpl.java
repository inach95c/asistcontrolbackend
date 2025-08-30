package com.sistema.examenes.servicios.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.entidades.UsuarioRol;
import com.sistema.examenes.excepciones.UsuarioFoundException;
import com.sistema.examenes.repositorios.RolRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import com.sistema.examenes.servicios.UsuarioService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Autowired
	private RolRepository rolRepository;


	@Override
	public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
		Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());
		if(usuarioLocal != null) {
			System.out.println("El usuario ya existe");
			throw new UsuarioFoundException ("El usuario ya está presente");
		}
		else {
			for(UsuarioRol usuarioRol:usuarioRoles) {
				rolRepository.save(usuarioRol.getRol());				
			}
			usuario.getUsuariosRoles().addAll(usuarioRoles);
			usuarioLocal = usuarioRepository.save(usuario);
			
		}
		return usuarioLocal;
	}


	@Override
	public Usuario obtenerUsuario(String username) {
			return usuarioRepository.findByUsername(username);
	}


	/*@Override
    public void eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }*/
	
	@Override
	public void eliminarUsuario(Long id) {
	    if (id == 1L) {
	        throw new RuntimeException("⚠️ No está permitido eliminar el usuario administrador con ID 1.");
	    }

	    if (usuarioRepository.existsById(id)) {
	        usuarioRepository.deleteById(id);
	    } else {
	        throw new RuntimeException("Usuario no encontrado con ID: " + id);
	    }
	}

	
	
	@Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll(); // Retorna la lista completa de usuarios
    }
	
	@Override
    public List<Usuario> buscarUsuariosPorNombre(String username) {
        return usuarioRepository.findByUsernameContaining(username);
    }
	
	// para actualizar
	@Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Usuario existente = usuarioRepository.findById(usuario.getId()).orElse(null);
        if (existente != null) {
            existente.setUsername(usuario.getUsername());
            existente.setNombre(usuario.getNombre());
            existente.setApellido(usuario.getApellido());
            existente.setEmail(usuario.getEmail());
            existente.setTelefono(usuario.getTelefono());
            existente.setCtlc(usuario.getCtlc());

            return usuarioRepository.save(existente);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuario.getId());
        }
    }
	
	//asistencia
	@Override
	public void actualizarHoraSalida(Long id, LocalDateTime horaSalida) {
	    Usuario usuario = usuarioRepository.findById(id).orElse(null);
	    
	    if (usuario != null) {
	        usuario.setHoraSalida(horaSalida);
	        usuarioRepository.save(usuario);
	    } else {
	        throw new RuntimeException("Usuario no encontrado con ID: " + id);
	    }
	}

	@Override
	public void actualizarHoraEntrada(Long id, LocalDateTime horaEntrada) {
	    Usuario usuario = usuarioRepository.findById(id).orElse(null);
	    
	    if (usuario != null) {
	        usuario.setHoraEntrada(horaEntrada);
	        usuarioRepository.save(usuario);
	    } else {
	        throw new RuntimeException("Usuario no encontrado con ID: " + id);
	    }
	}

//-------------------------------------------------------------------------------------
	// Marcar hora de entrada para el usuario autenticado
	public void registrarHoraEntradaActual() {
	    String username = obtenerUsernameActual();
	    Usuario usuario = usuarioRepository.findByUsername(username);
	    usuario.setHoraEntrada(LocalDateTime.now());
	    usuarioRepository.save(usuario);
	}

	// Marcar hora de salida para el usuario autenticado
	public void registrarHoraSalidaActual() {
	    String username = obtenerUsernameActual();
	    Usuario usuario = usuarioRepository.findByUsername(username);
	    usuario.setHoraSalida(LocalDateTime.now());
	    usuarioRepository.save(usuario);
	}

	// Ayudante para obtener el usuario logueado
	private String obtenerUsernameActual() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    return auth.getName();
	}
//----------------------------------------------------------------------------------------
	
	@Override
	public Usuario obtenerUsuarioPorId(Long id) {
	    return usuarioRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
	}
	
	
	//  para la foto no lo uso
	@Override
	public Usuario guardarUsuarioConFoto(Usuario usuario, Set<UsuarioRol> roles, MultipartFile foto) throws IOException {
	    if (foto != null && !foto.isEmpty()) {
	        String nombreArchivo = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
	        Path ruta = Paths.get("uploads").resolve(nombreArchivo);
	        Files.createDirectories(ruta.getParent());
	        Files.copy(foto.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
	        usuario.setFoto(nombreArchivo);
	    }

	    try {
			return this.guardarUsuario(usuario, roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	@Override
	public Usuario obtenerUsuarioPorUsername(String username) {
	    return usuarioRepository.findByUsername(username);
	}

	



}
