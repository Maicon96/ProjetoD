package br.com.dimo.ediws.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.dto.LayoutFilterDTO;
import br.com.dimo.ediws.dto.LoadResponseDTO;
import br.com.dimo.ediws.dto.ResponseDTO;
import br.com.dimo.ediws.dto.ResponseTabelaDTO;
import br.com.dimo.ediws.dto.ResponseTabelasDTO;
import br.com.dimo.ediws.dto.TabelaCampoDTO;
import br.com.dimo.ediws.dto.TabelaDTO;
import br.com.dimo.ediws.dto.TabelasDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroLayoutDTO;
import br.com.dimo.ediws.dto.cadastro.layout.LayoutDTO;
import br.com.dimo.ediws.entity.Layout;
import br.com.dimo.ediws.repository.LayoutRepository;
import br.com.dimo.ediws.service.LayoutService;

@RestController
@RequestMapping(value = "/service/layout/arquivo")
public class LayoutArquivoResource extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(LayoutArquivoResource.class);

	@Autowired
	private LayoutService layoutService;

	@Autowired
	private LayoutRepository layoutRepository;

	@PostMapping("/load")
	public ResponseEntity<?> loadLayoutArquivo() {
		LoadResponseDTO<Layout> dto = new LoadResponseDTO<Layout>();

		try {
			List<Layout> layoutArquivo = this.layoutRepository.findAll(Sort.by("padrao").descending()
					.and(Sort.by("descricao")));

			if (layoutArquivo == null) {
				dto.success = false;
				dto.msg = "Nenhum Layout encontrado!";
				LOG.info("Load - Nenhum Layout encontrado!");
			} else {
				dto.registros = layoutArquivo;
				dto.msg = "Sucesso ao buscar Layout!";
				LOG.info("Load - Sucesso ao buscar Layout!");
			}
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Layout: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Layout: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/load/filter")
	public ResponseEntity<?> loadLayoutArquivo(@RequestBody LayoutFilterDTO layout) {
		LoadResponseDTO<Layout> dto = new LoadResponseDTO<Layout>();

		try {
			List<Layout> layoutArquivo = this.layoutRepository.findLayoutsDescricao(layout.descricao);

			if (layoutArquivo == null) {
				dto.success = false;
				dto.msg = "Nenhum Layout encontrado!";
				LOG.info("Load - Nenhum Layout encontrado!");
			} else {
				dto.registros = layoutArquivo;
				dto.msg = "Sucesso ao buscar Layout!";
				LOG.info("Load - Sucesso ao buscar Layout!");
			}
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Layout: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Layout: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	@PostMapping("/save/antigo")
//	public ResponseEntity<?> saveAntigo(@RequestBody Layout layoutArquivo) {
//		ResponseDTO<Layout> response = new ResponseDTO<>();
//
//		try {
//			List<CampoErroDTO> errors = this.layoutService.salvar(layoutArquivo);
//			if (!errors.isEmpty()) {
//				response.success = false;
//				response.errors = errors;
//			} else {
//				response.registro = layoutArquivo;
//			}
//
//		} catch (Exception e) {
//			response.success = false;
//			LOG.error("Falha ao salvar layout de arquivo", e);
//			response.errors.add(new CampoErroDTO("id", "Falha ao salvar layout de arquivo"));
//		}
//
//		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
//				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody CadastroLayoutDTO cadastroDTO) {
		ResponseDTO<Layout> response = new ResponseDTO<>();

		try {
			List<CampoErroDTO> errors = this.layoutService.salvarLayout(cadastroDTO);
			if (!errors.isEmpty()) {
				response.success = false;
				response.errors = errors;
			} else {
				response.msg = "Sucesso ao cadastrar Layout";
			}

		} catch (Exception e) {
			response.success = false;
			LOG.error("Falha ao salvar layout de arquivo", e);
			response.errors.add(new CampoErroDTO("id", e.getMessage()));
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/buscar/tabela")
	public ResponseEntity<?> buscarTabela(@RequestBody TabelaDTO tabela) {
		ResponseDTO<ResponseTabelaDTO> response = new ResponseDTO<ResponseTabelaDTO>();

		try {
			List<TabelaCampoDTO> campos = this.layoutService.buscarTabelaBanco(tabela);

			if (campos.isEmpty()) {
				response.success = false;
				response.msg = "Tabela não encontrada no banco de dados!";
			} else {
				response.registro = new ResponseTabelaDTO();
				response.registro.campos = campos;
				response.msg = "Sucesso ao buscar informações da tabela";
			}

		} catch (Exception e) {
			response.success = false;
			LOG.error("Falha ao buscar informações da tabela", e);
			response.errors.add(new CampoErroDTO("id", e.getMessage()));
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/buscar/tabelas")
	public ResponseEntity<?> buscarTabelas(@RequestBody TabelasDTO tabelasDTO) {
		ResponseDTO<ResponseTabelasDTO> response = new ResponseDTO<ResponseTabelasDTO>();
		ResponseTabelasDTO responseTabelas = new ResponseTabelasDTO(); 
				
		try {
			List<TabelaDTO> tabelas = this.layoutService.buscarTabelasBanco(tabelasDTO.tabelas);

			if (tabelas.isEmpty()) {
				response.success = false;
				response.msg = "Tabelas não encontrada no banco de dados!";
			} else {
				responseTabelas.tabelas = tabelas;
				response.registro = responseTabelas;
				response.msg = "Sucesso ao buscar informações das tabelas";
			}

		} catch (Exception e) {
			response.success = false;
			LOG.error("Falha ao buscar informações das tabelas", e);
			response.errors.add(new CampoErroDTO("id", e.getMessage()));
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody List<LayoutDTO> layoutArquivo) {
		ResponseDTO<Layout> response = new ResponseDTO<Layout>();

		try {
			List<CampoErroDTO> errors = this.layoutService.deletar(layoutArquivo);
			if (errors.isEmpty()) {
				response.msg = "Sucesso ao deletar Layout!";
				LOG.info("Delete - Sucesso ao deletar Layout!");
			} else {
				response.success = false;
				response.errors = errors;
				LOG.info("Delete - Erro ao deletar Layout!");
			}
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao deletar Layout: " + e.getMessage();
			LOG.error("Delete - Falha ao deletar Layout: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
