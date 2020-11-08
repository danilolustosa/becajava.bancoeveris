package br.bancoeveris.app.service;
import java.util.List;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.OperacaoRepository;

@Service
public class OperacaoService {
	
	final OperacaoRepository _repository;
	
	public OperacaoService(OperacaoRepository repository) {
		_repository = repository;
	}
	
	public double Saldo(Long contaId) {
		
		double saldo = 0;
		
		Conta contaOrigem = new Conta();
		contaOrigem.setId(contaId);
		
		Conta contaDestino = new Conta();
		contaDestino.setId(contaId);
		
		List<Operacao> listaOrigem = _repository.findByContaOrigem(contaOrigem);
		List<Operacao> listaDestino = _repository.findByContaDestino(contaDestino);
		
		for(Operacao o : listaOrigem) {			
			switch(o.getTipo()) {				
				case "D":
					saldo += o.getValor();
					break;
				case "S":
					saldo -= o.getValor();
					break;					
				case "T":
					saldo -= o.getValor();
					break;					
				default:					
					break;				
			}
			
		}
		
		for(Operacao o : listaDestino) {			
			switch(o.getTipo()) {				
				case "D":
					saldo += o.getValor();
					break;
				case "S":
					saldo -= o.getValor();
					break;					
				case "T":
					saldo += o.getValor();
					break;					
				default:					
					break;				
			}	
		}		
		
		return saldo;
	}

}
