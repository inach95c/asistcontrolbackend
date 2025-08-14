package com.sistema.examenes.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sistema.examenes.entidades.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);
	

    List<Usuario> findAll(); // Método para obtener todos los usuarios
    
    // Método para buscar usuarios cuyo nombre de usuario contenga una cadena específica
    @Query("SELECT u FROM Usuario u WHERE u.username LIKE %:username%")
    List<Usuario> findByUsernameContaining(@Param("username") String username);
    
    


}
