package com.carlosvieira.projectapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carlosvieira.projectapi.domain.Cidade;
import com.carlosvieira.projectapi.domain.Cliente;
import com.carlosvieira.projectapi.domain.Endereco;
import com.carlosvieira.projectapi.domain.enums.TipoCliente;
import com.carlosvieira.projectapi.dto.ClienteDTO;
import com.carlosvieira.projectapi.dto.ClienteNewDTO;
import com.carlosvieira.projectapi.repositories.ClienteRepository;
import com.carlosvieira.projectapi.repositories.EnderecoRepository;
import com.carlosvieira.projectapi.services.exceptions.DataIntegrityException;
import com.carlosvieira.projectapi.services.exceptions.EntityNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	@Autowired
	private EnderecoRepository endRepository;

	public Cliente get(Integer id) {
		Optional<Cliente> entity = repository.findById(id);
		return entity.orElseThrow(() -> new EntityNotFoundException(String.format("Cliente não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getName()))); 
	}
	
	public List<Cliente> list() {
		return repository.findAll();
	}
	
	@Transactional
	public Cliente insert(Cliente entity) {
		entity.setId(null);
		repository.save(entity);
		endRepository.saveAll(entity.getEnderecos());
		return entity;
	}
	
	public Cliente update(Cliente entity) {
		Cliente entityToUpdate = get(entity.getId());
		updateData(entityToUpdate, entity);
		return repository.save(entity);
	}
	
	public void delete(Integer id) {
		get(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(String.format("Não é possível excluir um Cliente que possua Pedidos cadastrados! Id: %d, Tipo: %s", id, Cliente.class.getName()));
		}
	}
	
	public Page<Cliente> listPage(Integer page, Integer linesPerPage, String orderBy, Boolean isDesc) {
		Direction direction = (isDesc ? Direction.DESC : Direction.ASC);
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, direction, orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente cliente = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCadastro(), TipoCliente.toEnum(dto.getTipo()));
		Cidade cidade = new Cidade(dto.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), cidade, dto.getCep(), cliente);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(dto.getTelefone1());
		if (dto.getTelefone2() != null) { cliente.getTelefones().add(dto.getTelefone2()); }
		if (dto.getTelefone3() != null) { cliente.getTelefones().add(dto.getTelefone3()); }
		return cliente;
	}
	
	private void updateData(Cliente entityToUpdate, Cliente entity) {
		entityToUpdate.setNome(entity.getNome());
		entityToUpdate.setEmail(entity.getEmail());
	}
}
