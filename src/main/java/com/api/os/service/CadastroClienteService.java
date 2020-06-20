package com.api.os.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.os.exception.NegocioException;
import com.api.os.model.Cliente;
import com.api.os.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public Cliente salvar (Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}
		
		
		return clienteRepository.save(cliente);
	}
	
	public void deletar(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
}
