package com.sistema.examenes.repositorios;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sistema.examenes.entidades.Examen;

import com.sistema.examenes.entidades.Categoria;


public interface ExamenRepository extends JpaRepository<Examen, Long>{
	
	List<Examen> findByCategoria(Categoria categoria);               //este codigo funciona si no hay paginacion
	
	List<Examen> findByActivo(Boolean estado);
	
	List<Examen> findByCategoriaAndActivo(Categoria categoria, Boolean estado);
	
	
		@Query("SELECT e FROM Examen e WHERE " +
		       "LOWER(CONCAT(COALESCE(e.titulo, ''), ' ', COALESCE(e.organismo, ''), ' ', " +
		       "COALESCE(e.descripcion, ''), ' ', COALESCE(e.velocidad, ''), ' ', " +
		       "COALESCE(e.prioridad, ''), ' ', COALESCE(e.seguimiento, ''), ' ', " +
		       "COALESCE(e.estadoDelServicio, ''))) " +
		       "LIKE LOWER(CONCAT('%', :keyword, '%'))")

	
	    List<Examen> buscar(@Param("keyword") String keyword); 
		
		//contar estatico
		@Query("SELECT COUNT(e) FROM Examen e WHERE e.estadoDelServicio = 'DI' ")  //si quiero incluir la busqueda de otra mas con la DI debo poner un AND y lo mismo
		Long contarExamenesConEstadoDI();
		
		@Query("SELECT COUNT(e) FROM Examen e WHERE e.estadoDelServicio = 'Solicitud_Nueva'")  //test
		Long contarExamenesConEstadoCon_OS_Siprec();                                                   //esto cuenta Solicitud_Nueva y asi sucesivamente en el controlador , el servicio
		
		//contar dinamico
		@Query("SELECT COUNT(e) FROM Examen e WHERE e.estadoDelServicio = :estado")
	    Long contarExamenesPorEstado(@Param("estado") String estado);
		
		//caso ok
		@Query("SELECT e.categoria.titulo, COUNT(e) FROM Examen e WHERE e.estadoDelServicio = 'Solicitud_Nueva' GROUP BY e.categoria.titulo")
		List<Object[]> contarExamenesPorCategoriaConEstadoCon_OS_Siprec();                 //esto cuenta Solicitud_Nueva y asi sucesivamente en el controlador , el servicio

		//caso busqueda por ctlc para notificacion superior
/*		@Query("SELECT e.usuario.ctlc, COUNT(e) FROM Examen e WHERE e.estadoDelServicio = 'Solicitud_Nueva' GROUP BY e.usuario.ctlc")
		List<Object[]> contarExamenesPorUsuarioConEstadoCon_OS_Siprec();
*/
		
		//caso notificacion
		@Query("SELECT COUNT(e) FROM Examen e WHERE e.estadoDelServicio = 'Solicitud_Nueva' AND e.usuario.ctlc = :ctlc")
	    int contarExamenesPorUsuarioCTLC(@Param("ctlc") String ctlc);
		
	
	//-------------------------------------------------------------------------------------

	/*  ASISTENCIA */   

			
	 

}


//////////////////////////********************************************/////////////////////////////////
/*
package com.sistema.examenes.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sistema.examenes.entidades.Examen;
import com.sistema.examenes.entidades.Categoria;

public interface ExamenRepository extends JpaRepository<Examen, Long> {

    Page<Examen> findByCategoria(Categoria categoria, Pageable pageable);

    Page<Examen> findByActivo(Boolean estado, Pageable pageable);

    Page<Examen> findByCategoriaAndActivo(Categoria categoria, Boolean estado, Pageable pageable);

    @Query("SELECT e FROM Examen e WHERE LOWER(CONCAT(COALESCE(e.titulo, ''), ' ', COALESCE(e.organismo, ''), ' ', " +
           "COALESCE(e.descripcion, ''), ' ', COALESCE(e.velocidad, ''), ' ', COALESCE(e.prioridad, ''), ' ', " +
           "COALESCE(e.seguimiento, ''), ' ', COALESCE(e.estadoDelServicio, ''))) " +
           "LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Examen> buscar(@Param("keyword") String keyword, Pageable pageable);
}

*/