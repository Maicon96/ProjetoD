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
import br.com.dimo.ediws.entity.ClienteFormaRecebimentoFTP;
import br.com.dimo.ediws.entity.FormaRecebimentoFTP;
import br.com.dimo.ediws.repository.ClienteFormaRecebimentoFTPRepository;
import br.com.dimo.ediws.repository.FormaRecebimentoFTPRepository;
import br.com.dimo.ediws.service.FormaRecebimentoFTPService;


@RestController
@RequestMapping(value="/service/forma/recebimento/ftp")
public class FormaRecebimentoFTPResource extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(FormaRecebimentoFTPResource.class);
	
	@Autowired
	private FormaRecebimentoFTPService formaRecebimentoFTPService;
	
	@Autowired
	private FormaRecebimentoFTPRepository formaRecebimentoFTPRepository;
	
	@Autowired
	private ClienteFormaRecebimentoFTPRepository clienteFormaRecebimentoFTPRepository;
	
	
	@PostMapping("/load")
	public ResponseEntity<?> loadFormaRecebimentoFTP() {
		LoadResponseDTO<FormaRecebimentoFTP> dto = new LoadResponseDTO<FormaRecebimentoFTP>();

		try {
			List<FormaRecebimentoFTP> formaRecebimentoFTPs = this.formaRecebimentoFTPRepository.findAllNotCliente();

			if (formaRecebimentoFTPs == null) {
				dto.success = false;
				dto.msg = "Forma de Recebimento - FTP não encontrada!";
				LOG.info("Load - Nenhuma Forma de Recebimento - FTP encontrada!");
			} else {
				dto.registros = formaRecebimentoFTPs;
				dto.msg = "Sucesso ao buscar Forma de Recebimento - FTP!";
				LOG.info("Load - Sucesso ao buscar Forma de Recebimento - FTP!");
			}
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Forma de Recebimento - FTP: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Forma de Recebimento - FTP: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/load/filter/cliente")
	public ResponseEntity<?> loadFormaRecebimentoFTPFilter(@RequestBody Cliente cliente) {
		LoadResponseDTO<FormaRecebimentoFTP> dto = new LoadResponseDTO<FormaRecebimentoFTP>();
		List<FormaRecebimentoFTP> formasFTPs = new ArrayList<FormaRecebimentoFTP>(); 
				
		try {			
			List<ClienteFormaRecebimentoFTP> ftps = this.clienteFormaRecebimentoFTPRepository.findByIdCliente(cliente.getId());
			
			for(ClienteFormaRecebimentoFTP ftp : ftps) {
				formasFTPs.add(ftp.getFormaRecebimentoFTP());
			}			
			
			if (formasFTPs.size() == 0) {
				dto.msg = "Forma de Recebimento - FTP não encontrada!";
				LOG.info("Load - Nenhuma Forma de Recebimento - FTP encontrada!");
			} else {
				dto.registros = formasFTPs;
				dto.msg = "Sucesso ao buscar Forma de Recebimento - FTP!";
				LOG.info("Load - Sucesso ao buscar Forma de Recebimento - FTP!");
			}
			
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Forma de Recebimento - FTP: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Forma de Recebimento - FTP: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/exists")
	public ResponseEntity<?> existsUsuario(@RequestBody FormaRecebimentoFTP formaRecebimentoFTP) {
		ResponseDTO<FormaRecebimentoFTP> response = new ResponseDTO<FormaRecebimentoFTP>();
				
		try {			
			Optional<FormaRecebimentoFTP> formaRecebimentoFTPOp = this.formaRecebimentoFTPRepository.findById(formaRecebimentoFTP.getId());
			
			if (!formaRecebimentoFTPOp.isPresent()) {
				response.success = false;
				response.msg = "Forma de Recebimento - FTP não cadastrada!";
				LOG.info("Exists - Forma de Recebimento - FTP não cadastrado!");
			} else {
				response.registro = formaRecebimentoFTPOp.get();
				response.msg = "Sucesso ao buscar Forma de Recebimento - FTP!";
				LOG.info("Exists - Sucesso ao buscar Forma de Recebimento - FTP!");
			}
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao buscar Forma de Recebimento - FTP: " + e.getMessage();
			LOG.error("Exists - Falha ao buscar Forma de Recebimento - FTP: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
				
	@PostMapping("/save")	
	public ResponseEntity<?> updateUpload(@RequestBody FormaRecebimentoFTP formaRecebimentoFTP) {		
		ResponseDTO<FormaRecebimentoFTP> response = new ResponseDTO<>();
									
		try {			
			List<CampoErroDTO> errors = this.formaRecebimentoFTPService.salvar(formaRecebimentoFTP);	
			if (!errors.isEmpty()) {
				response.success = false;
				response.msg = "Erro ao salvar Forma de Recebimento - FTP";
				response.errors = errors;
			} else {
				response.registro = formaRecebimentoFTP;
			}
		} catch (Exception e) {
			response.success = false;
			LOG.error("Falha ao salvar Forma de Recebimento - FTP", e);
			response.errors.add(new CampoErroDTO("id", "Falha ao salvar Forma de Recebimento - FTP"));
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody List<FormaRecebimentoFTP> formaRecebimentoFTP) {		
		ResponseDTO<FormaRecebimentoFTP> response = new ResponseDTO<FormaRecebimentoFTP>();		
		
		try {
			this.formaRecebimentoFTPService.deletar(formaRecebimentoFTP);
			response.msg = "Sucesso ao deletar Forma de Recebimento - FTP!";					
			LOG.info("Delete - Sucesso ao deletar Forma de Recebimento - FTP!");
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao deletar Forma de Recebimento - FTP: " + e.getMessage();
			LOG.error("Delete - Falha ao deletar Forma de Recebimento - FTP: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	
}
