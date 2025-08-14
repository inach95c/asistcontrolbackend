package com.sistema.examenes.servicios;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.entidades.UsuarioRol;

public interface UsuarioService {
	
	
	public Usuario guardarUsuario(Usuario usuario , Set<UsuarioRol> usuarioRoles) throws Exception;
	
	public Usuario obtenerUsuario(String username);
	
	public void eliminarUsuario(Long Id);
	
	List<Usuario> obtenerTodosLosUsuarios();
	
	// Nuevo método de búsqueda
    List<Usuario> buscarUsuariosPorNombre(String username);
    
     // Nuevo método para actualizar
    Usuario actualizarUsuario(Usuario usuario);
    
    //asistencia
    void actualizarHoraSalida(Long id, LocalDateTime horaSalida);
    void actualizarHoraEntrada(Long id, LocalDateTime horaentrada);
    
    void registrarHoraEntradaActual();
    void registrarHoraSalidaActual();
    
 //   Usuario obtenerUsuarioPorId(Long usuarioId);  //de la clase horario
    
    Usuario obtenerUsuarioPorId(Long id);
  
    // para la foto no lo uso
    Usuario guardarUsuarioConFoto(Usuario usuario, Set<UsuarioRol> roles, MultipartFile foto) throws IOException;


    Usuario obtenerUsuarioPorUsername(String username);


}
