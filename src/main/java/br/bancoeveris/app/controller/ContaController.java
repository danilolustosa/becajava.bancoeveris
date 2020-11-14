package br.bancoeveris.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.response.ContaResponse;
import br.bancoeveris.app.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {
	
	final ContaService _service;
	
	public ContaController(ContaService service) {
		_service = service;
	}
	
	@GetMapping(path = "/{hash}")
	public ResponseEntity Saldo(@PathVariable String hash) {
		try {
			ContaResponse response = _service.Saldo(hash);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro genérico");
		}		
	}

}
