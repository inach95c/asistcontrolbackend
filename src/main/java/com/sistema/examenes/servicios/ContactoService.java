package com.sistema.examenes.servicios;
import java.util.List;
import java.util.Optional;
import com.sistema.examenes.entidades.Contacto;

public interface ContactoService {
    List<Contacto> listarTodos();
    Optional<Contacto> obtenerPorId(Long id);
    Contacto guardar(Contacto contacto);
    Contacto actualizar(Long id, Contacto contactoActualizado);
    void eliminar(Long id);

    // Filtros CRM
    List<Contacto> buscarPorNombre(String nombre);
    List<Contacto> buscarPorCiudad(String ciudad);
    List<Contacto> filtrarPorPreferencias(List<String> preferencias);
    
    //para qr crm
    Contacto crearSiNoExiste(String nombre, String correo);
    
    Contacto crearDesdeRegistroRapido(String nombre, String correo, String telefono, String ciudad, Integer edad, String sexo, List<String> preferencias);


}
