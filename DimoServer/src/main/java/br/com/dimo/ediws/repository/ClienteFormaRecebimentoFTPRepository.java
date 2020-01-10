package br.com.dimo.ediws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimo.ediws.entity.ClienteFormaRecebimentoFTP;

public interface ClienteFormaRecebimentoFTPRepository extends JpaRepository<ClienteFormaRecebimentoFTP, Long> {
	
	ClienteFormaRecebimentoFTP findById(long id);
	
	ClienteFormaRecebimentoFTP findByIdFormaRecebimentoFTP(long idFormaRecebimentoFTP);
		
	List<ClienteFormaRecebimentoFTP> findByIdCliente(Long idCliente);
	
}
