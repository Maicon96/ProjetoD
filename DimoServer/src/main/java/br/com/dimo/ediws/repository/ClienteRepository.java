package br.com.dimo.ediws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimo.ediws.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findById(long id);
	
	
}
