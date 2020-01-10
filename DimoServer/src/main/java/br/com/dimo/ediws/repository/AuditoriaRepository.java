package br.com.dimo.ediws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimo.ediws.entity.Auditoria;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
	
	Auditoria findById(long id);
	
	
}
