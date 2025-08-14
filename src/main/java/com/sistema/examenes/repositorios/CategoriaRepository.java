package com.sistema.examenes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistema.examenes.entidades.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
