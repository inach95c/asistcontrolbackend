package com.sistema.examenes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sistema.examenes.entidades.Rol;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.entidades.UsuarioRol;
import com.sistema.examenes.excepciones.UsuarioFoundException;
import com.sistema.examenes.servicios.UsuarioService;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling     // agregada para eliminar los token del Qr


public class SistemaExamenesBackendSanApplication implements CommandLineRunner {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SistemaExamenesBackendSanApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		/* *****sqlite*******
		  // Copiar el archivo .db desde resources a una ruta accesible
	    Path targetPath = Paths.get("data/sistema_examenes.db");
	    if (!Files.exists(targetPath)) {
	        try (InputStream in = getClass().getResourceAsStream("/db/sistema_examenes.db")) {
	            if (in == null) {
	                System.err.println("No se encontró el archivo sistema_examenes.db en /db dentro de resources.");
	                return;
	            }
	            Files.createDirectories(targetPath.getParent());
	            Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("Base de datos copiada a: " + targetPath.toAbsolutePath());
	        } catch (IOException e) {
	            System.err.println("Error al copiar la base de datos: " + e.getMessage());
	        }
	    } else {
	        System.out.println("Base de datos ya existe en: " + targetPath.toAbsolutePath());
	    }
		
		**********************************/
		
	/*	try {
			Usuario usuario = new Usuario();
			
			usuario.setNombre("santodgc");
			usuario.setApellido("Girandy");
			usuario.setUsername("santodgc");
			usuario.setPassword(bCryptPasswordEncoder.encode("543321.asd"));
			usuario.setEmail("santos.columbie@etecsa.cu");
			usuario.setTelefono("59888006");
			usuario.setPerfil("foto.pnp");
			
			
			Rol rol = new Rol();
			rol.setRolId(1L);
			rol.setNombre("ADMIN");
			
			Set<UsuarioRol> usuarioRoles = new HashSet<>();
			UsuarioRol usuarioRol = new UsuarioRol();  
			usuarioRol.setRol(rol);
			usuarioRol.setUsuario(usuario);
			usuarioRoles.add(usuarioRol);
			
			Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
			System.out.println(usuarioGuardado.getUsername()); 
		}catch(UsuarioFoundException exception) {
			exception.printStackTrace();
		}
	*/	
		
//    /*
		Usuario usuario = new Usuario();
		
		usuario.setNombre("admin1");
		usuario.setApellido("admin1");
		usuario.setUsername("admin1");
		usuario.setPassword(bCryptPasswordEncoder.encode("admin1"));
		usuario.setEmail("admin1c@gmail.com");
		usuario.setTelefono("59888006");
		usuario.setPerfil("foto.pnp");
		
		
		Rol rol = new Rol();
		rol.setRolId(1L);
		rol.setNombre("ADMIN");
		
		Set<UsuarioRol> usuarioRoles = new HashSet<>();
		UsuarioRol usuarioRol = new UsuarioRol();  
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);
		usuarioRoles.add(usuarioRol);
		
		Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
		System.out.println(usuarioGuardado.getUsername()); 
//	*/	
	}

}
