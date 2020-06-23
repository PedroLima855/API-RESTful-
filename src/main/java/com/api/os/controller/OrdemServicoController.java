package com.api.os.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.os.model.OrdemServico;
import com.api.os.repository.OrdemServicoRepository;
import com.api.os.representation.model.OrdemServicoInput;
import com.api.os.representation.model.OrdemServicoModel;
import com.api.os.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired 
	private ModelMapper modelmapper;
	
	// Consulta todos os registros
	@GetMapping
	public List<OrdemServicoModel> listar(){
		return toCollectionModel(ordemServicoRepository.findAll());
		
	}
	
	// Consulta registro por Id
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
		
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if (ordemServico.isPresent()) {
			OrdemServicoModel OrdemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(OrdemServicoModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	// Salva um registro passando pelo service
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		
		return toModel( gestaoOrdemServico.criar(ordemServico));
		
	}
	
	// metodo de facilita a reutilização do modelMapepr
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelmapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico){
		return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelmapper.map(ordemServicoInput, OrdemServico.class);
	}
}
