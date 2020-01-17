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
import br.com.dimo.ediws.dto.TabelaDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroLayoutArquivoCamposDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroLayoutArquivoDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroLayoutDTO;
import br.com.dimo.ediws.dto.cadastro.layout.LayoutDTO;
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

	@org.springframework.transaction.annotation.Transactional
	public List<CampoErroDTO> salvarLayout(CadastroLayoutDTO cadastroDTO) {
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();
		Layout layout;
		Boolean existeArquivos = false;
		Boolean existeCampos = false;

		try {
			/**** salvo layout ****/

			layout = new Layout(cadastroDTO.layout.getId(), cadastroDTO.layout.getDescricao(),
					cadastroDTO.layout.getPadrao(), cadastroDTO.layout.getIdentificadorLinha(),
					cadastroDTO.layout.getTipoDelimitador(),
					cadastroDTO.layout.getPosicaoInicial(), cadastroDTO.layout.getPosicaoFinal(),
					cadastroDTO.layout.getDelimitador(), cadastroDTO.layout.getIndexador(), 
					cadastroDTO.layout.getNomeEmpresa(), cadastroDTO.layout.getCabecalho());

			erros = this.validaSalvar(layout);
			if (erros.isEmpty()) {
				layout = this.layoutRepository.save(layout);

				/**** faço for das linha ****/
				for (CadastroLayoutArquivoDTO listaArquivos : cadastroDTO.getCadastroLayoutArquivoDTOs()) {
					existeArquivos = true;

					LayoutArquivo arquivo = new LayoutArquivo(listaArquivos.getId(), layout.getId(),
							listaArquivos.getIdentificadorLinha());

					erros = this.validaSalvarLayoutArquivo(arquivo);
					if (erros.isEmpty()) {
						/**** salvo linha ****/
						arquivo = this.layoutArquivoRepository.save(arquivo);

						for (CadastroLayoutArquivoCamposDTO campos : listaArquivos.getCadastroLayoutArquivoDTOs()) {
							existeCampos = true;

							/**** salvo campos ****/
							erros = this.salvarCampo(campos, layout, arquivo.getId(), layout.getId());
							if (!erros.isEmpty())
								break;
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

	public List<CampoErroDTO> salvarCampo(CadastroLayoutArquivoCamposDTO campos, Layout layout, Long idArquivo,
			Long idLayout) {
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		String jsonDepara = campos.getJsonDepara().toString();

		LayoutArquivoCampoTabela layoutArquivoCampo = new LayoutArquivoCampoTabela(campos.getId(), idArquivo, idLayout,
				campos.getNomeCampo(), campos.getNomeCampoTabela(), jsonDepara, layout.getDelimitador(),
				campos.getIndexador(), campos.getPosicaoInicial(), campos.getPosicaoFinal(), campos.getNomeTabela());

		if (campos.getId() != null) {
			if (campos.getPosicaoInicial() != null && campos.getPosicaoInicial() != null
					&& campos.getIndexador() != null) {
				this.layoutArquivoCampoRepository.delete(layoutArquivoCampo);
			} else {
				erros = this.validaSalvarLayoutCampo(layoutArquivoCampo);
				if (erros.isEmpty()) {
					this.layoutArquivoCampoRepository.save(layoutArquivoCampo);
				}
			}
		} else {
			erros = this.validaSalvarLayoutCampo(layoutArquivoCampo);
			if (erros.isEmpty()) {
				this.layoutArquivoCampoRepository.save(layoutArquivoCampo);
			}
		}

		return erros;
	}

	public List<CampoErroDTO> validaSalvar(Layout layoutNotfis) {

		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		return erros;
	}

	public List<CampoErroDTO> validaSalvarLayoutArquivo(LayoutArquivo layoutArquivo) {

		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

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

		if (layoutArquivoCampo.getCaractere() == null || layoutArquivoCampo.getCaractere().equals("")) {
			if (layoutArquivoCampo.getPosicaoInicial() == null) {
				erros.add(new CampoErroDTO("posicaoInicial", "Posição Inicial é campo obrigatório"));
			}

			if (layoutArquivoCampo.getPosicaoFinal() == null) {
				erros.add(new CampoErroDTO("posicaoFinal", "Posição Final é campo obrigatório"));
			}
		}

		if (layoutArquivoCampo.getIdLayout() == null) {
			erros.add(new CampoErroDTO("idLayout", "ID Layout é campo obrigatório"));
		}

		if (layoutArquivoCampo.getNomeTabela() == null || layoutArquivoCampo.getNomeTabela() == "") {
			erros.add(new CampoErroDTO("nomeTabela", "Nome da tabela é campo obrigatório"));
		}

		return erros;
	}

	public List<CampoErroDTO> deletar(List<LayoutDTO> layouts) {
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		for (LayoutDTO layoutDTO : layouts) {

			Layout layout = this.layoutRepository.findLayout(layoutDTO.getId());

			List<CampoErroDTO> errosRegistro = this.validaDeletar(layout);

			if (!errosRegistro.isEmpty()) {
				erros.addAll(errosRegistro);
			} else {

				List<LayoutArquivo> layoutsArquivos = this.layoutArquivoRepository.findAllByIdLayout(layout.getId());

				for (LayoutArquivo layoutArquivo : layoutsArquivos) {
					this.layoutArquivoCampoRepository.deleteAll(layoutArquivo.getLayoutArquivoCampoTabelas());

					this.layoutArquivoRepository.delete(layoutArquivo);
				}

				this.layoutRepository.delete(layout);
			}
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

	public List<TabelaDTO> buscarTabelasBanco(List<TabelaDTO> tabelas) throws SQLException {

		this.conexao = new ConnectionPostgresJDBC();

		DatabaseMetaData dbm = this.conexao.getConnection().getMetaData();

		for (TabelaDTO tabela : tabelas) {

			ResultSet tables = dbm.getTables(null, null, tabela.getNomeTabela(), null);
			if (tables.next()) {
				
				List<TabelaCampoDTO> campos = new ArrayList<TabelaCampoDTO>();
				
				campos = this.lerTabela(tabela, dbm);

				tabela.setCampos(campos);
			}
		}

		return tabelas;
	}

	public List<TabelaCampoDTO> buscarTabelaBanco(TabelaDTO tabela) throws SQLException {
		List<TabelaCampoDTO> campos = new ArrayList<TabelaCampoDTO>();

		this.conexao = new ConnectionPostgresJDBC();
		
		DatabaseMetaData dbm = this.conexao.getConnection().getMetaData();
		
		campos = this.lerTabela(tabela, dbm);
		
		return campos;
	}
	
	public List<TabelaCampoDTO> lerTabela(TabelaDTO tabela, DatabaseMetaData dbm) throws SQLException {
		List<TabelaCampoDTO> campos = new ArrayList<TabelaCampoDTO>();
		
		if (tabela.getNomeTabela() != null) {
			ResultSet tables = dbm.getTables(null, null, tabela.getNomeTabela(), null);
			if (tables.next()) {
				Statement stmt = this.conexao.getConnection().createStatement();

				ResultSet rs = stmt.executeQuery("SELECT * FROM " + tabela.getNomeTabela() + " LIMIT 1");
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				

				for (int i = 1; i <= columnCount; i++) {	
					String nomeCampo = rsmd.getColumnName(i);
					
					boolean fk = false;
					
					if (nomeCampo.length() > 2) {
						if (nomeCampo.substring(0, 3).equals("id_")) {
							fk = true;	
						}
					}
						
					if (!nomeCampo.equals("id") && !fk) {
						TabelaCampoDTO campo = new TabelaCampoDTO();
						campo.nomeCampo = rsmd.getColumnName(i);
						campo.tipoCampo = rsmd.getColumnTypeName(i);

						campos.add(campo);
					}			
				}
			}	
		}	
		
		return campos;
	}
	

}
