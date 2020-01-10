package br.com.dimo.ediwsboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.dimo.ediwsboot.entity.LayoutArquivoCampoTabela;

public interface LayoutArquivoCampoTabelaRepository extends JpaRepository<LayoutArquivoCampoTabela, Long> {
	
	List<LayoutArquivoCampoTabela> findByIdLayoutArquivo(long idLayoutArquivo);
	
	@Query("SELECT u FROM LayoutArquivoCampoTabela u WHERE u.idLayoutArquivo=:idLayoutArquivo AND u.idLayout=:idLayout")
	public List<LayoutArquivoCampoTabela> findCampos(@Param("idLayoutArquivo") long idLayoutArquivo, 
			@Param("idLayout") long idLayout);	
	
		
}
