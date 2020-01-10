package br.com.dimo.ediws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimo.ediws.entity.ClienteFormaRecebimentoEmail;

public interface ClienteFormaRecebimentoEmailRepository extends JpaRepository<ClienteFormaRecebimentoEmail, Long> {
	
	ClienteFormaRecebimentoEmail findById(long id);
	
	ClienteFormaRecebimentoEmail findByIdFormaRecebimentoEmail(long idFormaRecebimentoEmail);
	
		
	List<ClienteFormaRecebimentoEmail> findByIdCliente(Long idCliente);
	
}
