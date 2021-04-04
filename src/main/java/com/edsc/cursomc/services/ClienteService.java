package com.edsc.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edsc.cursomc.domain.Cidade;
import com.edsc.cursomc.domain.Cliente;
import com.edsc.cursomc.domain.Endereco;
import com.edsc.cursomc.domain.enums.TipoCliente;
import com.edsc.cursomc.dto.ClienteDTO;
import com.edsc.cursomc.dto.ClienteNewDTO;
import com.edsc.cursomc.repositories.CidadeRepository;
import com.edsc.cursomc.repositories.ClienteRepository;
import com.edsc.cursomc.repositories.EnderecoRepository;
import com.edsc.cursomc.services.exceptions.DataIntegrityException;
import com.edsc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo:" + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			this.repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel deletar o clinte com itens associados.");
		}
	}

	public List<Cliente> findAll() {
		List<Cliente> categorias = this.repo.findAll();
		return categorias;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	@Transactional
	public Cliente fromDto(ClienteNewDTO dto) {
		Cliente cli = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(), TipoCliente.toEnum(dto.getTipo()));
		Cidade cid = cidadeRepository.findById(dto.getCidadeId()).get();
		Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCep(), cli, cid);
		
		cli.getEnderecos().add(endereco);
		cli.getTelefones().add(dto.getTelefone1());
		
		if (dto.getTelefone2() != null)
			cli.getTelefones().add(dto.getTelefone2());
		
		if (dto.getTelefone3() != null)
			cli.getTelefones().add(dto.getTelefone3());
		
		return cli;
	}
	
	public Cliente fromDto(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
