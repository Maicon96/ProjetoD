package br.com.dimo.ediws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.dimo.ediws.entity.FormaRecebimentoPasta;

public interface FormaRecebimentoPastaRepository extends JpaRepository<FormaRecebimentoPasta, Long> {
	
	FormaRecebimentoPasta findById(long id);
	
	@Query(value="SELECT id, caminho, usuario FROM T_IMPORT_PASTA", nativeQuery = true)
	public List<FormaRecebimentoPasta> findAllPastas();
		
	@Query(nativeQuery = true, value = "SELECT * FROM T_IMPORT_PASTA"
			+ " WHERE id NOT IN (SELECT T_IMPORT_PASTA_id FROM T_CLIENTE_HAS_T_IMPORT_PASTA)")
	public List<FormaRecebimentoPasta> findAllNotCliente();
	
	
}
