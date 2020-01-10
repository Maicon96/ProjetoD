package br.com.dimo.ediwsboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimo.ediwsboot.entity.LayoutArquivo;

public interface LayoutArquivoRepository extends JpaRepository<LayoutArquivo, Long> {
	
	
	List<LayoutArquivo> findByIdLayout(long idLayout);
	
	
	
}
