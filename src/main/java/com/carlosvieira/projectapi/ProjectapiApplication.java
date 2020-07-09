package com.carlosvieira.projectapi;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carlosvieira.projectapi.domain.Categoria;
import com.carlosvieira.projectapi.domain.Cidade;
import com.carlosvieira.projectapi.domain.Cliente;
import com.carlosvieira.projectapi.domain.Endereco;
import com.carlosvieira.projectapi.domain.Estado;
import com.carlosvieira.projectapi.domain.Produto;
import com.carlosvieira.projectapi.domain.enums.TipoCliente;
import com.carlosvieira.projectapi.repositories.CategoriaRepository;
import com.carlosvieira.projectapi.repositories.CidadeRepository;
import com.carlosvieira.projectapi.repositories.ClienteRepository;
import com.carlosvieira.projectapi.repositories.EnderecoRepository;
import com.carlosvieira.projectapi.repositories.EstadoRepository;
import com.carlosvieira.projectapi.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjectapiApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Escritório");
		Categoria cat2 = new Categoria(null, "Informática");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Papel Sulfite A4", 20.00);
		Produto p5 = new Produto(null, "Teclado", 100.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p2, p4));
		cat2.getProdutos().addAll(Arrays.asList(p1, p2, p3, p5));
		
		p1.getCategorias().addAll(Arrays.asList(cat2));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat2));
		p4.getCategorias().addAll(Arrays.asList(cat1));
		p5.getCategorias().addAll(Arrays.asList(cat2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Belo Horizonte", est1);
		Cidade c2 = new Cidade(null, "Campinas", est2);
		Cidade c3 = new Cidade(null, "São Paulo", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Carlos Henrique", "carlos@mail.com", "12345678901", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("3132564789", "31998523149"));
		
		Endereco e1 = new Endereco(null, "Rua Diamante", "300", "Cobertura", "Centro", c1, "30200300", cli1);
		Endereco e2 = new Endereco(null, "Rua Ruby", "900", "Sala 1000", "Centro", c3, "20300200", cli1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}
}
