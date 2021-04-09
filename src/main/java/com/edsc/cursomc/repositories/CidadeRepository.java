package com.edsc.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edsc.cursomc.domain.Cidade;
import com.edsc.cursomc.domain.Cliente;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	
}
