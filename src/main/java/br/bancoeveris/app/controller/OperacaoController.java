package br.bancoeveris.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.service.OperacaoService;

@RestController
@RequestMapping("/operacao")
public class OperacaoController {
	
	final OperacaoService _service;
	
	public OperacaoController(OperacaoService service) {
		_service = service;
	}
	
	
	@GetMapping(path = "/{idConta}")
	public double Saldo(@PathVariable Long idConta) {
		return _service.Saldo(idConta);
	}
	
	

}
