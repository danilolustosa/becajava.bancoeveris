package br.bancoeveris.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;

@Service
public class ContaService {
	
	final ContaRepository _repository;
	final OperacaoService _operacaoService;
	
	public ContaService(ContaRepository repository, OperacaoService operacaoService)	{
		_repository = repository;
		_operacaoService = operacaoService;
	}
	
	public double Saldo(String hash) {
		
		List<Conta> lista = _repository.findByHash(hash);
		
		if (lista.size() == 0)
			return 0;
		else
			return _operacaoService.Saldo(lista.get(0).getId());		
	}

}
