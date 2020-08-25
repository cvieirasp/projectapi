package com.carlosvieira.projectapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.carlosvieira.projectapi.domain.Categoria;
import com.carlosvieira.projectapi.dto.CategoriaDTO;
import com.carlosvieira.projectapi.repositories.CategoriaRepository;
import com.carlosvieira.projectapi.services.exceptions.DataIntegrityException;
import com.carlosvieira.projectapi.services.exceptions.EntityNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;

	public Categoria get(Integer id) {
		Optional<Categoria> entity = repository.findById(id);
		return entity.orElseThrow(() -> new EntityNotFoundException(String.format("Entidade não encontrada! Id: %d, Tipo: %s", id, Categoria.class.getName()))); 
	}
	
	public List<Categoria> list() {
		return repository.findAll();
	}
	
	public Categoria insert(Categoria entity) {
		entity.setId(null);
		return repository.save(entity);
	}
	
	public Categoria update(Categoria entity) {
		get(entity.getId());
		return repository.save(entity);
	}
	
	public void delete(Integer id) {
		get(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(String.format("Não é possível excluir uma Categoria que possua Produtos cadastrados! Id: %d, Tipo: %s", id, Categoria.class.getName()));
		}
	}
	
	public Page<Categoria> listPage(Integer page, Integer linesPerPage, String orderBy, Boolean isDesc) {
		Direction direction = (isDesc ? Direction.DESC : Direction.ASC);
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, direction, orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}
