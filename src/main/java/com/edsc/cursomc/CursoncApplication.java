package com.edsc.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edsc.cursomc.domain.Categoria;
import com.edsc.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CursoncApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursoncApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Analise");
		Categoria cat4 = new Categoria(null, "Matemática");

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));

	}

}
