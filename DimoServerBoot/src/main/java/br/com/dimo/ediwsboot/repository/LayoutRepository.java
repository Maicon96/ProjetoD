package br.com.dimo.ediwsboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.dimo.ediwsboot.entity.Layout;

public interface LayoutRepository extends JpaRepository<Layout, Long> {
	
	Layout findById(long id);
	
//	Layout findByIdentificadorEmpresa(String identificadorEmpresa);
	
	@Query("SELECT u FROM Layout u WHERE u.descricao LIKE %:descricao% ORDER BY u.descricao ASC")
	public List<Layout> findLayoutsDescricao(@Param("descricao") String descricao);	
		
}
