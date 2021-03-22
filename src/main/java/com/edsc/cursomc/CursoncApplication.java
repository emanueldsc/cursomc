package com.edsc.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edsc.cursomc.domain.Categoria;
import com.edsc.cursomc.domain.Cidade;
import com.edsc.cursomc.domain.Cliente;
import com.edsc.cursomc.domain.Endereco;
import com.edsc.cursomc.domain.Estado;
import com.edsc.cursomc.domain.Produto;
import com.edsc.cursomc.domain.enums.TipoCliente;
import com.edsc.cursomc.repositories.CategoriaRepository;
import com.edsc.cursomc.repositories.CidadeRepository;
import com.edsc.cursomc.repositories.ClienteRepository;
import com.edsc.cursomc.repositories.EnderecoRepository;
import com.edsc.cursomc.repositories.EstadoRepository;
import com.edsc.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoncApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursoncApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 4000.00);
		Produto p2 = new Produto(null, "Impressora", 500.50);
		Produto p3 = new Produto(null, "Monitor", 1500.50);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlância", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Emanuel Douglas", "emanuel@email.com", "000.000.000-00",
				TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("85987469393", "1234567899"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "60764-595", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "400", "Casa 3", "Centro", "60761-600", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

	}

}
