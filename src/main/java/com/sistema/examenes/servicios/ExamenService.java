package com.sistema.examenes.servicios;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.sistema.examenes.entidades.Categoria;
import com.sistema.examenes.entidades.Examen;

public interface ExamenService {
	
	Examen agregarExamen(Examen examen);
	
	Examen actualizarExamen(Examen examen);
	
	Set<Examen> obtenerExamenes();
	
	Examen obtenerExamen(Long examenId);
	
	void eliminarExamen(Long examenId);
	
	List<Examen> listarExamenesDeUnaCategoria(Categoria categoria);

    List<Examen> obtenerExamenesActivos();

    List<Examen> obtenerExamenesActivosDeUnaCategoria(Categoria categoria);
    
   public List<Examen> buscar(String keyword);
   
  //contar estatico
   Long obtenerCantidadExamenesDI();
 //contar estatico
   Long obtenerCantidadExamenesCon_OS_Siprec();
 
   
   //contar dinamico
  // Long cantidadDI = examenRepository.contarExamenesPorEstado("DI");
 //  Long cantidadOK = examenRepository.contarExamenesPorEstado("OK");
//   Long cantidadNueva = examenRepository.contarExamenesPorEstado("SOLICITUD_NUEVA");
Long contarExamenesPorEstado(String estado);



List<Object[]> obtenerCantidadExamenesPorCategoriaCon_OS_Siprec();

//caso notificacion

    int obtenerCantidadExamenesCTLC(String ctlc);
    
    /*Asistencia*/
   

}




/*
package com.sistema.examenes.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sistema.examenes.entidades.Categoria;
import com.sistema.examenes.entidades.Examen;

public interface ExamenService {

    Examen agregarExamen(Examen examen);
    Examen actualizarExamen(Examen examen);
    Examen obtenerExamen(Long examenId);
    void eliminarExamen(Long examenId);

    Page<Examen> listarExamenesDeUnaCategoria(Categoria categoria, Pageable pageable);
    Page<Examen> obtenerExamenesActivos(Pageable pageable);
    Page<Examen> obtenerExamenesActivosDeUnaCategoria(Categoria categoria, Pageable pageable);
    Page<Examen> buscar(String keyword, Pageable pageable);
}
*/
