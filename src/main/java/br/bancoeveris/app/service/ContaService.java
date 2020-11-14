package br.bancoeveris.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.response.ContaResponse;

@Service
public class ContaService {
	
	final ContaRepository _repository;
	final OperacaoService _operacaoService;
	
	public ContaService(ContaRepository repository, OperacaoService operacaoService)	{
		_repository = repository;
		_operacaoService = operacaoService;
	}
	
	public ContaResponse Saldo(String hash) {
		ContaResponse response = new ContaResponse();
		Conta conta = _repository.findByHash(hash);
		
		if (conta == null) {
			response.setStatusCode(404);
			response.setMessage("Conta não encontrada.");
			return response;					
		}		

		response.setStatusCode(200);
		response.setMessage("Conta encontrada");
		response.setHash(conta.getHash());
		response.setId(conta.getId());
		response.setSaldo(_operacaoService.Saldo(conta.getId()));
		
		return response;		
	}

}
