package br.com.dimo.ediws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimo.ediws.entity.ClienteFormaRecebimentoPasta;

public interface ClienteFormaRecebimentoPastaRepository extends JpaRepository<ClienteFormaRecebimentoPasta, Long> {
	
	ClienteFormaRecebimentoPasta findById(long id);
	
	ClienteFormaRecebimentoPasta findByIdFormaRecebimentoPasta(long idFormaRecebimentoPasta);
			
	List<ClienteFormaRecebimentoPasta> findByIdCliente(Long idCliente);	
}
