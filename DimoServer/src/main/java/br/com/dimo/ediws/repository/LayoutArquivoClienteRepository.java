package br.com.dimo.ediws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimo.ediws.entity.LayoutArquivoCliente;

public interface LayoutArquivoClienteRepository extends JpaRepository<LayoutArquivoCliente, Long> {
	
	LayoutArquivoCliente findById(long id);
	
	List<LayoutArquivoCliente> findByIdLayout(long idLayout);
		
	boolean existsIdLayoutByIdLayout(long idLayout);
	
	List<LayoutArquivoCliente> findAllByOrderByIdClienteAsc();
	
//	@Query(value="SELECT u.idLayout FROM LayoutArquivoCliente u WHERE u.idLayout = :idLayout")
//	public Long existsByIdLayout(@Param("idLayout") Long idLayout);
		
}
