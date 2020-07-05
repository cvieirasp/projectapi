package com.carlosvieira.projectapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosvieira.projectapi.domain.Categoria;
import com.carlosvieira.projectapi.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;

	public Categoria get(Integer id) {
		Optional<Categoria> entity = repository.findById(id);
		return entity.orElse(null);
	}
}
