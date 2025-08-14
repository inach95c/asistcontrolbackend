package com.sistema.examenes.servicios.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.examenes.entidades.Categoria;
import com.sistema.examenes.repositorios.CategoriaRepository;
import com.sistema.examenes.servicios.CategoriaService;

@Service 
public class CategoriaServiceImpl implements CategoriaService{
	
	
	@Autowired
	private CategoriaRepository categoriaRepository;	
		
	

	@Override
	public Categoria agregarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return categoriaRepository.save(categoria);
	}

	@Override
	public Categoria actualizarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return categoriaRepository.save(categoria);
	}

	@Override
	public Set<Categoria> obtenerCategorias() {
		// TODO Auto-generated method stub
		return new LinkedHashSet<Categoria>(categoriaRepository.findAll());
	}

	@Override
	public Categoria obtenerCategoria(Long categoriaId) {
		// TODO Auto-generated method stub
		return categoriaRepository.findById(categoriaId).get();
	}

	@Override
	public void eliminarCategoria(Long categoriaId) {
		// TODO Auto-generated method stub
		Categoria categoria = new Categoria();
		categoria.setCategoriaId(categoriaId);
		categoriaRepository.delete(categoria); 
		
	}

}
