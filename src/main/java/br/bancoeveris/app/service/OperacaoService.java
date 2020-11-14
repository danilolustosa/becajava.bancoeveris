package br.bancoeveris.app.service;
import java.util.List;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.*;
import br.bancoeveris.app.request.*;
import br.bancoeveris.app.response.*;

@Service
public class OperacaoService {
	
	final OperacaoRepository _repository;
	final ContaRepository _contaRepository;
	
	public OperacaoService(OperacaoRepository repository, ContaRepository contaRepository) {
		_repository = repository;
		_contaRepository = contaRepository;
	}	
	
	public BaseResponse inserirDeposito(DepositoRequest request) {
		BaseResponse response = new BaseResponse();
		Operacao operacao = new Operacao();
		
		if (request.getHash() == "") {
			response.setStatusCode(400);
			response.setMessage("Conta para depósito não preenchida");
			return response;
		}
		
		if (request.getValor() <= 0) {
			response.setStatusCode(400);
			response.setMessage("Valor para depósito inválido.");
			return response;
		}
		
		Conta conta = _contaRepository.findByHash(request.getHash());
		
		if (conta == null) {
			response.setStatusCode(400);
			response.setMessage("Conta inexistente.");
			return response;
		}		
		
		operacao.setTipo("D");
		operacao.setValor(request.getValor());
		operacao.setContaDestino(conta);
		_repository.save(operacao);
		
		response.setStatusCode(201);
		response.setMessage("Depósito realizado com sucesso.");
		return response;		
	}
	
	public BaseResponse inserirSaque(SaqueRequest request) {
		BaseResponse response = new BaseResponse();
		Operacao operacao = new Operacao();
		
		if (request.getHash() == "") {
			response.setStatusCode(400);
			response.setMessage("Conta para saque não preenchida.");
			return response;
		}
		
		if (request.getValor() <= 0) {
			response.setStatusCode(400);
			response.setMessage("Valor para saque inválido.");
			return response;
		}
		
		Conta conta = _contaRepository.findByHash(request.getHash());
		
		if (conta == null) {
			response.setStatusCode(400);
			response.setMessage("Conta inexistente.");
			return response;
		}		
		
		operacao.setTipo("S");
		operacao.setValor(request.getValor());
		operacao.setContaOrigem(conta);
		_repository.save(operacao);
		
		response.setStatusCode(201);
		response.setMessage("Saque realizado com sucesso.");
		return response;		
	}
	
	public BaseResponse inserirTransferencia(TransferenciaRequest request) {
		BaseResponse response = new BaseResponse();
		Operacao operacao = new Operacao();
		
		if (request.getHashOrigem() == "") {
			response.setStatusCode(400);
			response.setMessage("Conta origem não preenchida.");
			return response;
		}
		
		if (request.getHashDestino() == "") {
			response.setStatusCode(400);
			response.setMessage("Conta destino não preenchida.");
			return response;
		}
		
		if (request.getValor() <= 0) {
			response.setStatusCode(400);
			response.setMessage("Valor para saque inválido.");
			return response;
		}
		
		Conta contaOrigem = _contaRepository.findByHash(request.getHashOrigem());
		
		if (contaOrigem == null) {
			response.setStatusCode(400);
			response.setMessage("Conta origem inexistente.");
			return response;
		}		
		
		Conta contaDestino = _contaRepository.findByHash(request.getHashDestino());
		
		if (contaDestino == null) {
			response.setStatusCode(400);
			response.setMessage("Conta destino inexistente.");
			return response;
		}		
		
		operacao.setTipo("T");
		operacao.setValor(request.getValor());
		operacao.setContaOrigem(contaOrigem);
		operacao.setContaDestino(contaDestino);
		_repository.save(operacao);
		
		response.setStatusCode(201);
		response.setMessage("Transferência realizada com sucesso.");
		return response;		
	}
	
	public double Saldo(Long contaId) {
		
		double saldo = 0;
		
		List<Operacao> lista = _repository.findOperacoesPorConta(contaId);
		
		for(Operacao o : lista) {			
			switch(o.getTipo()) {				
				case "D":
					saldo += o.getValor();
					break;
				case "S":
					saldo -= o.getValor();
					break;					
				case "T":
					
					if (o.getContaDestino().getId() == contaId) 					
						saldo += o.getValor();

					if (o.getContaOrigem().getId() == contaId) 					
						saldo -= o.getValor();
					
					break;					
				default:					
					break;				
			}			
		}		
		
		return saldo;
	}

}
