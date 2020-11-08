package br.bancoeveris.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {
	
	final ContaService _service;
	
	public ContaController(ContaService service) {
		_service = service;
	}
	
	@GetMapping(path = "/{hash}")
	public double Saldo(@PathVariable String hash) {
		return _service.Saldo(hash);
	}

}
