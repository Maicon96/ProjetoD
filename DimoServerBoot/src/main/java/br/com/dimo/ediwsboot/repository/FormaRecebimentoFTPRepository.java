package br.com.dimo.ediwsboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.dimo.ediwsboot.entity.FormaRecebimentoFTP;

public interface FormaRecebimentoFTPRepository extends JpaRepository<FormaRecebimentoFTP, Long> {
	
	FormaRecebimentoFTP findById(long id);
		
	@Query(nativeQuery = true, value = "SELECT * FROM T_IMPORT_FTP"
			+ " WHERE id NOT IN (SELECT T_IMPORT_FTP_id FROM T_CLIENTE_HAS_T_IMPORT_FTP)")
	public List<FormaRecebimentoFTP> findAllNotCliente();
	
	
}
