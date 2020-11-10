package br.bancoeveris.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.bancoeveris.app.model.*;

@Repository
public interface ContaRepository  extends JpaRepository<Conta,Long> {
	
	Conta findByHash(String hash);

}
