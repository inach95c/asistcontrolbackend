package com.sistema.examenes.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table (name = "usuarios")
public class Usuario implements UserDetails {
	
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String password;
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private boolean enabled = true;
	private String perfil;
	private String ctlc;
	@Column(nullable = true)
	private LocalDateTime horaEntrada;
	@Column(nullable = true)
	private LocalDateTime horaSalida;
	@Column(length = 20)
	private String dni;
	private String direccion;
	private String puesto;
	private String tarifaPorHora;
	private String tarifaHoraExtra;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
    private LocalDate fechaDeContrato;
	
	

	// para la foto no lo uso
	private String foto;
	
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnore // ✅ evita el bucle
	private Set<Horario> horarios;

	
	
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "usuario")
	@JsonIgnore
	private Set<UsuarioRol> usuariosRoles = new HashSet<>();
	
	//esto es mio  +++
		    public Usuario(Long id, String username, String password, String nombre, String apellido, String email, String telefono, boolean enabled, String perfil, String ctlc) {
	//        this.Id = id;
		    	this.id=id;
	        this.username = username;
	        this.password = password;                       //****************************************//
	        this.nombre = nombre;                           // todo este codigo lo agregue pero no estaba//
	        this.apellido = apellido;                       //****************************************// 
	        this.email = email;
	        this.telefono = telefono;
	        this.enabled = enabled;
	        this.perfil = perfil;
	        this.setCtlc(ctlc);
	    }
  //  ---
		    
   /* **************  agregado de asistencia ******************************************* */
		    
		    //  Aquí empieza la nueva relación hacia la entidad Asistencia
		    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
		    @JsonIgnore
		    private Set<Asistencia> asistencias = new HashSet<>();

		    public Set<Asistencia> getAsistencias() {
		        return asistencias;
		    }

		    public void setAsistencias(Set<Asistencia> asistencias) {
		        this.asistencias = asistencias;
		    }    
   /*-----------------------fin agregado de asistencia --------------------------------------*/		    

	/*public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}
*/
		    
		    public Long getId() {
		        return id;
		    }

		    public void setId(Long id) {
		        this.id = id;
		    }


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override                                                  //este metodo fue copiado
    public boolean isAccountNonExpired() {
        return true;
    }
	
	 @Override                                                 //este metodo fue copiado
	 public boolean isAccountNonLocked() {
	    return true;
	}
	 
	@Override                                                //este metodo fue copiado
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getPerfil() {
		return perfil;
	}


	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}


	public Set<UsuarioRol> getUsuariosRoles() {
		return usuariosRoles;
	}


	public void setUsuariosRoles(Set<UsuarioRol> usuariosRoles) {
		this.usuariosRoles = usuariosRoles;
	}
	
	public Usuario() {           //constructor vacio
		
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> autoridades = new HashSet<>();
		this.usuariosRoles.forEach(usuarioRol ->{
			autoridades.add(new Authority(usuarioRol.getRol().getNombre()));
		});
		return autoridades;
	}

	public String getCtlc() {
		return ctlc;
	}

	public void setCtlc(String ctlc) {
		this.ctlc = ctlc;
	}
	
	//asistencia
	public LocalDateTime getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(LocalDateTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public LocalDateTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(LocalDateTime horaSalida) {
		this.horaSalida = horaSalida;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public LocalDate getFechaDeContrato() {
		return fechaDeContrato;
	}

	public void setFechaDeContrato(LocalDate fechaDeContrato) {
		this.fechaDeContrato = fechaDeContrato;
	}

	public String getTarifaPorHora() {
		return tarifaPorHora;
	}

	public void setTarifaPorHora(String tarifaPorHora) {
		this.tarifaPorHora = tarifaPorHora;
	}

	public String getTarifaHoraExtra() {
		return tarifaHoraExtra;
	}

	public void setTarifaHoraExtra(String tarifaHoraExtra) {
		this.tarifaHoraExtra = tarifaHoraExtra;
	}
	

}//fin
