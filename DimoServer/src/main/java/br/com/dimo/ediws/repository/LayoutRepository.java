package br.com.dimo.ediws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.dimo.ediws.entity.Layout;

public interface LayoutRepository extends JpaRepository<Layout, Long> {
	
	Layout findById(long id);
	
	@Query("SELECT u FROM Layout u WHERE u.id=:id")
	public Layout findLayout(@Param("id") Long id);
	
	
	@Query("SELECT u FROM Layout u WHERE u.descricao LIKE %:descricao% ORDER BY u.descricao ASC")
	public List<Layout> findLayoutsDescricao(@Param("descricao") String descricao);	
	
}
