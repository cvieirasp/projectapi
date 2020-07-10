package com.carlosvieira.projectapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosvieira.projectapi.domain.Pedido;
import com.carlosvieira.projectapi.repositories.PedidoRepository;
import com.carlosvieira.projectapi.services.exceptions.EntityNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;

	public Pedido get(Integer id) {
		Optional<Pedido> entity = repository.findById(id);
		return entity.orElseThrow(() -> new EntityNotFoundException(String.format("Entidade não encontrada! Id: %d, Tipo: %s", id, Pedido.class.getName()))); 
	}
}
