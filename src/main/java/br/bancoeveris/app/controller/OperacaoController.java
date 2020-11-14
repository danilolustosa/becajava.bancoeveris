package br.bancoeveris.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.request.*;
import br.bancoeveris.app.response.*;
import br.bancoeveris.app.service.OperacaoService;

@RestController
@RequestMapping("/operacao")
public class OperacaoController {
	
	final OperacaoService _service;
	
	public OperacaoController(OperacaoService service) {
		_service = service;
	}
	
	@PostMapping(path = "/deposito")
	public ResponseEntity deposito(@RequestBody DepositoRequest request) {
		try {
			BaseResponse response = _service.inserirDeposito(request);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro genérico");
		}		
	}
	
	@PostMapping(path = "/saque")
	public ResponseEntity saque(@RequestBody SaqueRequest request) {
		try {
			BaseResponse response = _service.inserirSaque(request);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro genérico");
		}		
	}

	@PostMapping(path = "/transferencia")
	public ResponseEntity transferencia(@RequestBody TransferenciaRequest request) {
		try {
			BaseResponse response = _service.inserirTransferencia(request);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro genérico");
		}		
	}

}
