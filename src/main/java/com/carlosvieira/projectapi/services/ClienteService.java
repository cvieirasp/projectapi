package com.carlosvieira.projectapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosvieira.projectapi.domain.Cliente;
import com.carlosvieira.projectapi.repositories.ClienteRepository;
import com.carlosvieira.projectapi.services.exceptions.EntityNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;

	public Cliente get(Integer id) {
		Optional<Cliente> entity = repository.findById(id);
		return entity.orElseThrow(() -> new EntityNotFoundException(String.format("Entidade não encontrada! Id: %d, Tipo: %s", id, Cliente.class.getName()))); 
	}
}
