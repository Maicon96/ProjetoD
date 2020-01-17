package br.com.dimo.ediws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimo.ediws.entity.LayoutArquivo;

public interface LayoutArquivoRepository extends JpaRepository<LayoutArquivo, Long> {
	
	
	List<LayoutArquivo> findAllByIdLayout(long idLayout);
	
		
}
