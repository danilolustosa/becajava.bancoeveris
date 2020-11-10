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
			response.statusCode = 400;
			response.message = "Conta para depósito não preenchida.";
			return response;
		}
		
		if (request.getValor() <= 0) {
			response.statusCode = 400;
			response.message = "Valor para depósito inválido.";
			return response;
		}
		
		Conta conta = _contaRepository.findByHash(request.getHash());
		
		if (conta == null) {
			response.statusCode = 400;
			response.message = "Conta inexistente.";
			return response;
		}		
		
		operacao.setTipo("D");
		operacao.setValor(request.getValor());
		operacao.setContaDestino(conta);
		this.inserir(operacao);
		
		response.statusCode = 201;
		response.message = "Depósito realizado com sucesso.";		
		return response;		
	}
	
	public BaseResponse inserirSaque(SaqueRequest request) {
		BaseResponse response = new BaseResponse();
		Operacao operacao = new Operacao();
		
		if (request.getHash() == "") {
			response.statusCode = 400;
			response.message = "Conta para saque não preenchida.";
			return response;
		}
		
		if (request.getValor() <= 0) {
			response.statusCode = 400;
			response.message = "Valor para saque inválido.";
			return response;
		}
		
		Conta conta = _contaRepository.findByHash(request.getHash());
		
		if (conta == null) {
			response.statusCode = 400;
			response.message = "Conta inexistente.";
			return response;
		}		
		
		operacao.setTipo("S");
		operacao.setValor(request.getValor());
		operacao.setContaOrigem(conta);
		this.inserir(operacao);
		
		response.statusCode = 201;
		response.message = "Saque realizado com sucesso.";		
		return response;		
	}
	
	public BaseResponse inserirTransferencia(TransferenciaRequest request) {
		BaseResponse response = new BaseResponse();
		Operacao operacao = new Operacao();
		
		if (request.getHashOrigem() == "") {
			response.statusCode = 400;
			response.message = "Conta origem não preenchida.";
			return response;
		}
		
		if (request.getHashDestino() == "") {
			response.statusCode = 400;
			response.message = "Conta destino não preenchida.";
			return response;
		}
		
		if (request.getValor() <= 0) {
			response.statusCode = 400;
			response.message = "Valor para saque inválido.";
			return response;
		}
		
		Conta contaOrigem = _contaRepository.findByHash(request.getHashOrigem());
		
		if (contaOrigem == null) {
			response.statusCode = 400;
			response.message = "Conta origem inexistente.";
			return response;
		}		
		
		Conta contaDestino = _contaRepository.findByHash(request.getHashDestino());
		
		if (contaDestino == null) {
			response.statusCode = 400;
			response.message = "Conta destino inexistente.";
			return response;
		}		
		
		operacao.setTipo("T");
		operacao.setValor(request.getValor());
		operacao.setContaOrigem(contaOrigem);
		operacao.setContaDestino(contaDestino);
		this.inserir(operacao);
		
		response.statusCode = 201;
		response.message = "Transferência realizada com sucesso.";
		return response;		
	}
	
	public void inserir(Operacao operacao) {
		_repository.save(operacao);
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
