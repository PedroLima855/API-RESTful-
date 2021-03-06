package com.api.os.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.os.model.Cliente;
import com.api.os.repository.ClienteRepository;
import com.api.os.service.CadastroClienteService;
@RequestMapping("clientes")
@RestController
public class ClienteController {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	CadastroClienteService cadastroCliente;
	
	// retorna uma lista de registros, por padrão já retorna o cod 200 ok
	@GetMapping
	public List<Cliente> listar(){
		return clienteRepository.findAll();
	}
	
	// Retorna um registro por Id
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscarId(@PathVariable Long clienteId) {
		
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	// Salva um registro 
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@Valid @RequestBody Cliente cliente) {
		return cadastroCliente.salvar(cliente);
	}
	
	// Edita um registro 
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> editar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){
		
		// essa condição verifica se há algum registro salvo
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
		cliente = cadastroCliente.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
		
		
	}
	
	// Deleta um registro atravez do Id
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deletar(@PathVariable Long clienteId) {
		
		// essa condição verifica se há algum registro
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroCliente.deletar(clienteId);
		
		return ResponseEntity.noContent().build();
	}

}
