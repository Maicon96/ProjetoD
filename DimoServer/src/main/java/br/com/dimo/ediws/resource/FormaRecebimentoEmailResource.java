package br.com.dimo.ediws.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.dto.LoadResponseDTO;
import br.com.dimo.ediws.dto.ResponseDTO;
import br.com.dimo.ediws.entity.Cliente;
import br.com.dimo.ediws.entity.ClienteFormaRecebimentoEmail;
import br.com.dimo.ediws.entity.FormaRecebimentoEmail;
import br.com.dimo.ediws.repository.ClienteFormaRecebimentoEmailRepository;
import br.com.dimo.ediws.repository.FormaRecebimentoEmailRepository;
import br.com.dimo.ediws.service.FormaRecebimentoEmailService;


@RestController
@RequestMapping(value="/service/forma/recebimento/email")
public class FormaRecebimentoEmailResource extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(FormaRecebimentoEmailResource.class);
	
	@Autowired
	private FormaRecebimentoEmailService formaRecebimentoEmailService;
	
	@Autowired
	private FormaRecebimentoEmailRepository formaRecebimentoEmailRepository;
	
	@Autowired
	private ClienteFormaRecebimentoEmailRepository clienteFormaRecebimentoEmailRepository;
		
	
	@PostMapping("/load")
	public ResponseEntity<?> loadFormaRecebimentoEmail() {
		LoadResponseDTO<FormaRecebimentoEmail> dto = new LoadResponseDTO<FormaRecebimentoEmail>();

		try {
			List<FormaRecebimentoEmail> formaRecebimentoEmails = this.formaRecebimentoEmailRepository.findAllNotCliente();

			if (formaRecebimentoEmails == null) {
				dto.success = false;
				dto.msg = "Forma de Recebimento - E-mail não encontrada!";
				LOG.info("Load - Nenhuma Forma de Recebimento - E-mail encontrada!");
			} else {
				dto.registros = formaRecebimentoEmails;
				dto.msg = "Sucesso ao buscar Forma de Recebimento - E-mail!";
				LOG.info("Load - Sucesso ao buscar Forma de Recebimento - E-mail!");
			}
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Forma de Recebimento - E-mail: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Forma de Recebimento - E-mail: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@PostMapping("/load/filter/cliente")
	public ResponseEntity<?> loadFormaRecebimentoEmailFilter(@RequestBody Cliente cliente) {
		LoadResponseDTO<FormaRecebimentoEmail> dto = new LoadResponseDTO<FormaRecebimentoEmail>();
		List<FormaRecebimentoEmail> formasEmails = new ArrayList<FormaRecebimentoEmail>(); 
				
		try {			
			List<ClienteFormaRecebimentoEmail> emails = this.clienteFormaRecebimentoEmailRepository.findByIdCliente(cliente.getId());
			
			for(ClienteFormaRecebimentoEmail email : emails) {
				formasEmails.add(email.getFormaRecebimentoEmail());
			}			
			
			if (formasEmails.size() == 0) {
				dto.msg = "Forma de Recebimento - E-mail não encontrada!";
				LOG.info("Load - Nenhuma Forma de Recebimento - E-mail encontrada!");
			} else {
				dto.registros = formasEmails;
				dto.msg = "Sucesso ao buscar Forma de Recebimento - E-mail!";
				LOG.info("Load - Sucesso ao buscar Forma de Recebimento - E-mail!");
			}
			
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Forma de Recebimento - E-mail: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Forma de Recebimento - E-mail: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		
	@PostMapping("/exists")
	public ResponseEntity<?> existsUsuario(@RequestBody FormaRecebimentoEmail formaRecebimentoEmail) {
		ResponseDTO<FormaRecebimentoEmail> response = new ResponseDTO<FormaRecebimentoEmail>();
				
		try {			
			Optional<FormaRecebimentoEmail> formaRecebimentoEmailOp = this.formaRecebimentoEmailRepository.findById(formaRecebimentoEmail.getId());
			
			if (!formaRecebimentoEmailOp.isPresent()) {
				response.success = false;
				response.msg = "Forma de Recebimento - E-mail não cadastrada!";
				LOG.info("Exists - Forma de Recebimento - E-mail não cadastrado!");
			} else {
				response.registro = formaRecebimentoEmailOp.get();
				response.msg = "Sucesso ao buscar Forma de Recebimento - E-mail!";
				LOG.info("Exists - Sucesso ao buscar Forma de Recebimento - E-mail!");
			}
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao buscar Forma de Recebimento - E-mail: " + e.getMessage();
			LOG.error("Exists - Falha ao buscar Forma de Recebimento - E-mail: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}		
				
	@PostMapping("/save")	
	public ResponseEntity<?> updateUpload(@RequestBody FormaRecebimentoEmail formaRecebimentoEmail) {		
		ResponseDTO<FormaRecebimentoEmail> response = new ResponseDTO<>();
									
		try {			
			List<CampoErroDTO> errors = this.formaRecebimentoEmailService.salvar(formaRecebimentoEmail);	
			if (!errors.isEmpty()) {
				response.success = false;
				response.msg = "Erro ao salvar Forma de Recebimento - E-mail";
				response.errors = errors;
			} else {
				response.registro = formaRecebimentoEmail;
			}
		} catch (Exception e) {
			response.success = false;
			LOG.error("Falha ao salvar Forma de Recebimento - Pasta", e);
			response.errors.add(new CampoErroDTO("id", "Falha ao salvar Forma de Recebimento - E-mail"));
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody List<FormaRecebimentoEmail> formaRecebimentoEmail) {		
		ResponseDTO<FormaRecebimentoEmail> response = new ResponseDTO<FormaRecebimentoEmail>();		
		
		try {
			this.formaRecebimentoEmailService.deletar(formaRecebimentoEmail);
			response.msg = "Sucesso ao deletar Forma de Recebimento - E-mail!";					
			LOG.info("Delete - Sucesso ao deletar Forma de Recebimento - E-mail!");
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao deletar Forma de Recebimento - E-mail: " + e.getMessage();
			LOG.error("Delete - Falha ao deletar Forma de Recebimento - E-mail: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	
}
