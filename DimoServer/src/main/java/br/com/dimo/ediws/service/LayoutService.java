package br.com.dimo.ediws.service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.connection.ConexaoJDBC;
import br.com.dimo.ediws.connection.ConnectionPostgresJDBC;
import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.dto.TabelaCampoDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroLayoutArquivoCamposDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroLayoutArquivoDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroLayoutDTO;
import br.com.dimo.ediws.entity.Layout;
import br.com.dimo.ediws.entity.LayoutArquivo;
import br.com.dimo.ediws.entity.LayoutArquivoCampoTabela;
import br.com.dimo.ediws.entity.LayoutArquivoCliente;
import br.com.dimo.ediws.repository.LayoutArquivoCampoTabelaRepository;
import br.com.dimo.ediws.repository.LayoutArquivoClienteRepository;
import br.com.dimo.ediws.repository.LayoutArquivoDelimitadorRepository;
import br.com.dimo.ediws.repository.LayoutArquivoRepository;
import br.com.dimo.ediws.repository.LayoutArquivoTabelaRepository;
import br.com.dimo.ediws.repository.LayoutRepository;

@Service
public class LayoutService {

	@Autowired
	LayoutRepository layoutRepository;

	@Autowired
	LayoutArquivoRepository layoutArquivoRepository;

	@Autowired
	LayoutArquivoCampoTabelaRepository layoutArquivoCampoRepository;

	@Autowired
	LayoutArquivoDelimitadorRepository layoutArquivoDelimitadorRepository;

	@Autowired
	LayoutArquivoTabelaRepository layoutArquivoTabelaRepository;

	@Autowired
	LayoutArquivoClienteRepository layoutArquivoClienteRepository;
	
	private ConexaoJDBC conexao = null;
	
	

	public List<CampoErroDTO> salvar(Layout layout) {

		List<CampoErroDTO> erros = this.validaSalvar(layout);

		if (erros.isEmpty()) {
			this.layoutRepository.save(layout);
		}

		return erros;

	}

	@org.springframework.transaction.annotation.Transactional
	public List<CampoErroDTO> salvarLayout(CadastroLayoutDTO cadastroDTO) {
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();
		Layout layout;
		Boolean existeArquivos = false;
		Boolean existeCampos = false;

		try {
			/**** salvo layout ****/
			erros = this.validaSalvar(cadastroDTO.layout);
			if (erros.isEmpty()) {
				layout = this.layoutRepository.save(cadastroDTO.layout);

				/**** faço for dos identificadores de linha ****/
				for (CadastroLayoutArquivoDTO listaArquivos : cadastroDTO.getCadastroLayoutArquivoDTOs()) {
					existeArquivos = true;

					LayoutArquivo arquivo = new LayoutArquivo(layout.getId(), listaArquivos.getIdentificadorLinha());

					erros = this.validaSalvarLayoutArquivo(arquivo);
					if (erros.isEmpty()) {
						/**** salvo identificador de linha ****/
						arquivo = this.layoutArquivoRepository.save(arquivo);

						for (CadastroLayoutArquivoCamposDTO campos : listaArquivos.getCadastroLayoutArquivoDTOs()) {
							existeCampos = true;

							/**** salvo campos ****/
							erros = this.salvarCampo(campos, arquivo.getId(), layout.getId());
							if (!erros.isEmpty()) break;

//							/**** salvo delimitacoes ****/
//							erros = this.salvarDelimitacoes(campos, arquivo.getId(), layout.getId());
//							if (!erros.isEmpty()) break;
//
//							/**** salvo nometabela ****/
//							erros = this.salvarNomeTabela(campos, arquivo.getId(), layout.getId());
//							if (!erros.isEmpty()) break;
						}
					} else {
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!existeArquivos)
				erros.add(new CampoErroDTO("layout", "Layout com dados incompletos: arquivos não foram informados"));
			if (!existeCampos)
				erros.add(new CampoErroDTO("layout", "Layout com dados incompletos: campos não foram informados"));

			if (!erros.isEmpty()) {
				String msgErro = "";

				for (CampoErroDTO erro : erros) {
					if (msgErro != "") {
						msgErro = " - " + erro.msg;
					} else {
						msgErro = erro.msg;
					}
				}

				throw new RuntimeException("Erros ao salvar Layout: " + msgErro);
			}
		}

		return erros;
	}

	public List<CampoErroDTO> salvarCampo(CadastroLayoutArquivoCamposDTO campos, Long idArquivo, Long idLayout) {
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		LayoutArquivoCampoTabela layoutArquivoCampo = new LayoutArquivoCampoTabela(idArquivo,
				idLayout, campos.getNomeCampo(), campos.getNomeCampoTabela(), campos.getJsonDepara(), 
				campos.getCaractere(), campos.getIndexador(),
				campos.getPosicaoInicial(), campos.getPosicaoFinal(), campos.getNomeTabela());

		erros = this.validaSalvarLayoutCampo(layoutArquivoCampo);
		if (erros.isEmpty()) {
			this.layoutArquivoCampoRepository.save(layoutArquivoCampo);
		}

		return erros;
	}
	
//	public List<CampoErroDTO> salvarCampo(CadastroLayoutArquivoCamposDTO campos, Long idArquivo, Long idLayout) {
//		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();
//
//		LayoutArquivoCampo layoutArquivoCampo = new LayoutArquivoCampo(idArquivo, idLayout, campos.getNomeCampo(),
//				campos.getNomeCampoTabela(), campos.getJsonDepara());
//
//		erros = this.validaSalvarLayoutCampo(layoutArquivoCampo);
//		if (erros.isEmpty()) {
//			this.layoutArquivoCampoRepository.save(layoutArquivoCampo);
//		}
//
//		return erros;
//	}
//
//	public List<CampoErroDTO> salvarDelimitacoes(CadastroLayoutArquivoCamposDTO campos, Long idArquivo, Long idLayout) {
//		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();
//
//		LayoutArquivoDelimitacao layoutArquivoDelimitacao = new LayoutArquivoDelimitacao(idArquivo, idLayout,
//				campos.getCaractere(), campos.getIndexador(), campos.getPosicaoInicial(), campos.getPosicaoFinal());
//
//		erros = this.validaSalvarLayoutDelimitacao(layoutArquivoDelimitacao);
//		if (erros.isEmpty()) {
//			this.layoutArquivoDelimitadorRepository.save(layoutArquivoDelimitacao);
//		}
//
//		return erros;
//	}
//
//	public List<CampoErroDTO> salvarNomeTabela(CadastroLayoutArquivoCamposDTO campos, Long idArquivo, Long idLayout) {
//		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();
//
//		LayoutArquivoTabela layoutArquivoTabela = new LayoutArquivoTabela(idArquivo, idArquivo, campos.getNomeTabela());
//		erros = this.validaSalvarLayoutTabela(layoutArquivoTabela);
//		if (erros.isEmpty()) {
//			this.layoutArquivoTabelaRepository.save(layoutArquivoTabela);
//		}
//
//		return erros;
//	}

	public List<CampoErroDTO> validaSalvar(Layout layoutNotfis) {

		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		return erros;
	}

	public List<CampoErroDTO> validaSalvarLayoutArquivo(LayoutArquivo layoutArquivo) {

		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

//		if (layoutArquivo.getIdLayout() == null) {
//			erros.add(new CampoErroDTO("idLayout", "ID Layout é campo obrigatório"));
//		}

		if (layoutArquivo.getIdentificadorLinha() == null) {

			// erros.add(new CampoErroDTO("identificadorLinha", "Identificador da linha é
			// campo obrigatório"));
		}

		return erros;
	}
	
	public List<CampoErroDTO> validaSalvarLayoutCampo(LayoutArquivoCampoTabela layoutArquivoCampo) {

		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		if (layoutArquivoCampo.getIdLayout() == null) {
			erros.add(new CampoErroDTO("idLayout", "ID Layout é campo obrigatório"));
		}

		if (layoutArquivoCampo.getNomeCampo() == null || layoutArquivoCampo.getNomeCampo() == "") {
			erros.add(new CampoErroDTO("nomeCampo", "Nome do campo é campo obrigatório"));
		}

		if (layoutArquivoCampo.getNomeCampoTabela() == null || layoutArquivoCampo.getNomeCampoTabela() == "") {
			erros.add(new CampoErroDTO("nomeCampoTabela", "Nome do campo na tabela é campo obrigatório"));
		}
		
		if (layoutArquivoCampo.getIdLayout() == null) {
			erros.add(new CampoErroDTO("idLayout", "ID Layout é campo obrigatório"));
		}

		if (layoutArquivoCampo.getCaractere() == null) {

			// erros.add(new CampoErroDTO("identificadorLinha", "Identificador da linha é
			// campo obrigatório"));
		}

		if (layoutArquivoCampo.getPosicaoInicial() == null) {
			erros.add(new CampoErroDTO("posicaoInicial", "Posição Inicial é campo obrigatório"));
		}

		if (layoutArquivoCampo.getPosicaoFinal() == null) {
			erros.add(new CampoErroDTO("posicaoFinal", "Posição Final é campo obrigatório"));
		}
		
		if (layoutArquivoCampo.getIdLayout() == null) {
			erros.add(new CampoErroDTO("idLayout", "ID Layout é campo obrigatório"));
		}

		if (layoutArquivoCampo.getNomeTabela() == null || layoutArquivoCampo.getNomeTabela() == "") {
			erros.add(new CampoErroDTO("nomeTabela", "Nome da tabela é campo obrigatório"));
		}

		return erros;
	}

//	public List<CampoErroDTO> validaSalvarLayoutDelimitacao(LayoutArquivoDelimitacao layoutArquivoDelimitacao) {
//
//		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();
//
//		if (layoutArquivoDelimitacao.getIdLayout() == null) {
//			erros.add(new CampoErroDTO("idLayout", "ID Layout é campo obrigatório"));
//		}
//
//		if (layoutArquivoDelimitacao.getCaractere() == null) {
//
//			// erros.add(new CampoErroDTO("identificadorLinha", "Identificador da linha é
//			// campo obrigatório"));
//		}
//
//		if (layoutArquivoDelimitacao.getPosicaoInicial() == null) {
//			erros.add(new CampoErroDTO("posicaoInicial", "Posição Inicial é campo obrigatório"));
//		}
//
//		if (layoutArquivoDelimitacao.getPosicaoFinal() == null) {
//			erros.add(new CampoErroDTO("posicaoFinal", "Posição Final é campo obrigatório"));
//		}
//
//		return erros;
//	}
//
//	public List<CampoErroDTO> validaSalvarLayoutCampo(LayoutArquivoCampo layoutArquivoCampo) {
//
//		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();
//
//		if (layoutArquivoCampo.getIdLayout() == null) {
//			erros.add(new CampoErroDTO("idLayout", "ID Layout é campo obrigatório"));
//		}
//
//		if (layoutArquivoCampo.getNomeCampo() == null || layoutArquivoCampo.getNomeCampo() == "") {
//			erros.add(new CampoErroDTO("nomeCampo", "Nome do campo é campo obrigatório"));
//		}
//
//		if (layoutArquivoCampo.getNomeCampoTabela() == null || layoutArquivoCampo.getNomeCampoTabela() == "") {
//			erros.add(new CampoErroDTO("nomeCampoTabela", "Nome do campo na tabela é campo obrigatório"));
//		}
//
//		return erros;
//	}
//
//	public List<CampoErroDTO> validaSalvarLayoutTabela(LayoutArquivoTabela layoutArquivoTabela) {
//
//		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();
//
//		if (layoutArquivoTabela.getIdLayout() == null) {
//			erros.add(new CampoErroDTO("idLayout", "ID Layout é campo obrigatório"));
//		}
//
//		if (layoutArquivoTabela.getNomeTabela() == null || layoutArquivoTabela.getNomeTabela() == "") {
//			erros.add(new CampoErroDTO("nomeTabela", "Nome da tabela é campo obrigatório"));
//		}
//
//		return erros;
//	}

	public List<CampoErroDTO> deletar(List<Layout> layoutNotfis) {
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		for (Layout layout : layoutNotfis) {
			List<CampoErroDTO> errosRegistro = this.validaDeletar(layout);

			if (!errosRegistro.isEmpty()) {
				erros.addAll(errosRegistro);
			}
		}

		if (erros.isEmpty()) {
			this.layoutRepository.deleteAll(layoutNotfis);
		}

		return erros;
	}

	public List<CampoErroDTO> validaDeletar(Layout layout) {

		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		this.validaFKLayout(layout, erros);

		return erros;
	}

	public void validaFKLayout(Layout layout, List<CampoErroDTO> erros) {
		if (layout.getId() != null) {
			List<LayoutArquivoCliente> layoutsClientes = this.layoutArquivoClienteRepository
					.findByIdLayout(layout.getId());

			if (!layoutsClientes.isEmpty()) {
				int cont = 0;
				String msg = "Layout não pode ser excluído, pois tem " + "vinculo com o(s) cliente(s): ";

				for (LayoutArquivoCliente layoutCliente : layoutsClientes) {

					if (cont == 0) {
						msg += layoutCliente.getCliente().getCpfcnpjFormat();
					} else {
						msg += " - " + layoutCliente.getCliente().getCpfcnpjFormat();
					}
					cont++;
				}

				erros.add(new CampoErroDTO("idLayout", msg));
			}
		}
	}
	
	public List<TabelaCampoDTO> buscarTabelaBanco(String nomeTabela) throws SQLException {
		List<TabelaCampoDTO> campos = new ArrayList<TabelaCampoDTO>();

		this.conexao = new ConnectionPostgresJDBC();
		
		DatabaseMetaData dbm = this.conexao.getConnection().getMetaData();

		ResultSet tables = dbm.getTables(null, null, nomeTabela, null);
		if (tables.next()) {
			Statement stmt = this.conexao.getConnection().createStatement();
			
	        ResultSet rs = stmt.executeQuery("SELECT * FROM " + nomeTabela + " LIMIT 1");
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int columnCount = rsmd.getColumnCount();

	        for (int i = 1; i <= columnCount; i++ ) {
	        	TabelaCampoDTO campo = new TabelaCampoDTO();
	        	campo.nomeCampo = rsmd.getColumnName(i);
	        	campo.tipoCampo = rsmd.getColumnTypeName(i);
  
	        	campos.add(campo);
	        }
		}     

		return campos;
	}
	
	
}
