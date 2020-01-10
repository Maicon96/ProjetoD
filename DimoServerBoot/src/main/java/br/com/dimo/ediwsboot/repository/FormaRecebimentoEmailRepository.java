package br.com.dimo.ediwsboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.dimo.ediwsboot.entity.FormaRecebimentoEmail;

public interface FormaRecebimentoEmailRepository extends JpaRepository<FormaRecebimentoEmail, Long> {
	
	FormaRecebimentoEmail findById(long id);
	
	@Query(nativeQuery = true, value = "SELECT id, pop3, porta, senha, tls_ssl, usuario FROM T_IMPORT_EMAIL "
			+ "WHERE id=:id")
	public FormaRecebimentoEmail findFormaRecebimentoEmail(@Param("id") Long id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM T_IMPORT_EMAIL"
			+ " WHERE id NOT IN (SELECT T_IMPORT_EMAIL_id FROM T_CLIENTE_HAS_T_IMPORT_EMAIL)")
	public List<FormaRecebimentoEmail> findAllNotCliente();
	
	
}
