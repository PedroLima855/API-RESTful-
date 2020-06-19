package com.api.os.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.os.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	//List<Cliente> findByName(String nome); // metodo de busca por nome especifico
	//List<Cliente> findByNameContaining(String nome); // metodo de busca com parte do nome 

}
