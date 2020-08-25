package com.carlosvieira.projectapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosvieira.projectapi.domain.Categoria;
import com.carlosvieira.projectapi.repositories.CategoriaRepository;
import com.carlosvieira.projectapi.services.exceptions.EntityNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;

	public Categoria get(Integer id) {
		Optional<Categoria> entity = repository.findById(id);
		return entity.orElseThrow(() -> new EntityNotFoundException(String.format("Entidade não encontrada! Id: %d, Tipo: %s", id, Categoria.class.getName()))); 
	}
	
	public Categoria insert(Categoria entity) {
		entity.setId(null);
		return repository.save(entity);
	}
	
	public Categoria update(Categoria entity) {
		get(entity.getId());
		return repository.save(entity);
	}
}
