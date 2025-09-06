/* ok con un solo registro package com.sistema.examenes.repositorios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Usuario;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    List<Asistencia> findByUsuarioAndHoraEntradaBetween(Usuario usuario, LocalDateTime inicio, LocalDateTime fin);

    List<Asistencia> findByUsuarioOrderByHoraEntradaDesc(Usuario usuario);

    Optional<Asistencia> findFirstByUsuarioAndHoraSalidaIsNullOrderByHoraEntradaDesc(Usuario usuario);
}
*/

package com.sistema.examenes.repositorios;

import com.sistema.examenes.entidades.Asistencia;
import com.sistema.examenes.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByUsuarioOrderByFechaHoraDesc(Usuario usuario);
  
    //admin 
    @Query("SELECT a FROM Asistencia a WHERE " +
    	       "(:nombre IS NULL OR LOWER(a.usuario.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
    	       "(:fecha IS NULL OR FUNCTION('DATE', a.fechaHora) = :fecha) " +
    	       "ORDER BY a.fechaHora DESC")
    	List<Asistencia> buscarPorNombreYFecha(@Param("nombre") String nombre, @Param("fecha") String fecha);
    
   
    
   // List<Asistencia> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Y si usas filtros por fecha:  esto es nuevo lo inclui con los arreglos de la fecha
    @Query("SELECT a FROM Asistencia a WHERE " +
    	       "(:inicio IS NULL OR a.fechaHora >= :inicio) AND " +
    	       "(:fin IS NULL OR a.fechaHora < :fin)")
    	List<Asistencia> buscarPorRango(@Param("inicio") OffsetDateTime inicio, @Param("fin") OffsetDateTime fin);

    
    List<Asistencia> findByFechaHoraBetween(LocalDateTime localDateTime, LocalDateTime localDateTime2);

}
