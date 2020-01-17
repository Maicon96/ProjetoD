package br.com.dimo.ediwsboot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import br.com.dimo.ediwsboot.connection.ConexaoJDBC;
import br.com.dimo.ediwsboot.connection.ConnectionPostgresJDBC;
import br.com.dimo.ediwsboot.dto.DeparaCampoDTO;
import br.com.dimo.ediwsboot.dto.DeparaDTO;
import br.com.dimo.ediwsboot.entity.FormaRecebimentoEmail;
import br.com.dimo.ediwsboot.entity.FormaRecebimentoFTP;
import br.com.dimo.ediwsboot.entity.FormaRecebimentoPasta;
import br.com.dimo.ediwsboot.entity.Layout;
import br.com.dimo.ediwsboot.entity.LayoutArquivo;
import br.com.dimo.ediwsboot.entity.LayoutArquivoCampoTabela;
import br.com.dimo.ediwsboot.integracaoes.configuracoes.Configuracoes;
import br.com.dimo.ediwsboot.repository.FormaRecebimentoEmailRepository;
import br.com.dimo.ediwsboot.repository.FormaRecebimentoFTPRepository;
import br.com.dimo.ediwsboot.repository.FormaRecebimentoPastaRepository;
import br.com.dimo.ediwsboot.repository.LayoutArquivoCampoTabelaRepository;
import br.com.dimo.ediwsboot.repository.LayoutArquivoRepository;
import br.com.dimo.ediwsboot.repository.LayoutRepository;
import br.com.dimo.ediwsboot.threads.ThreadEmail;
import br.com.dimo.ediwsboot.threads.ThreadFTP;
import br.com.dimo.ediwsboot.threads.ThreadPasta;

@SpringBootApplication
public class WsApplication {

	private static final Logger LOG = LoggerFactory.getLogger(WsApplication.class);

	@Autowired
	private LayoutRepository layoutRepository;

	@Autowired
	private LayoutArquivoRepository layoutArquivoRepository;

	@Autowired
	private LayoutArquivoCampoTabelaRepository layoutArquivoCampoTabelaRepository;

	@Autowired
	private FormaRecebimentoEmailRepository formaRecebimentoEmailRepository;

	@Autowired
	private FormaRecebimentoPastaRepository formaRecebimentoPastaRepository;

	@Autowired
	private FormaRecebimentoFTPRepository formaRecebimentoFtpRepository;

	// @Autowired
	// private Configuracoes configuracoes;

	public static Integer TEMPO_PASTA = 1000;
	public static Integer TEMPO_EMAIL = 1000;
	public static Integer TEMPO_FTP = 1000;

	private ConexaoJDBC conexao = null;
	// private static ArquivoIni;

	private Scanner wordFinder;

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	public CommandLineRunner importarDados() {

		// Configuracoes configuracoes = new Configuracoes();

		try {

			List<FileInputStream> arquivosFTP = new ArrayList<FileInputStream>();
			List<FileInputStream> arquivosPasta = new ArrayList<FileInputStream>();
			List<FileInputStream> arquivosEmail = new ArrayList<FileInputStream>();

			 List<FormaRecebimentoPasta> pastas =
			 this.formaRecebimentoPastaRepository.findAll();
			 List<FormaRecebimentoEmail> emails =
			 this.formaRecebimentoEmailRepository.findAll();
			 List<FormaRecebimentoFTP> ftps =
			 this.formaRecebimentoFtpRepository.findAll();

			 ThreadPasta threadPasta = new ThreadPasta("Thread pasta", arquivosPasta,
			 pastas);
			 ThreadEmail threadEmail = new ThreadEmail("Thread email", arquivosEmail,
			 emails);
			 ThreadFTP threadFTP = new ThreadFTP("Thread FTP", arquivosFTP, ftps);

			 ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

			 exec.scheduleAtFixedRate(new Thread(() -> {
			 Thread t = new Thread(threadEmail);
			 try {
			 t.start();
			 this.extrairDadosArquivos(arquivosEmail);
			 } catch (Exception e) { }
			 }), 0, TEMPO_EMAIL, TimeUnit.MILLISECONDS);

			 exec.scheduleAtFixedRate(new Thread(() -> {
			 Thread t = new Thread(threadFTP);
			 try {
			 t.start();
			 t.join();
			 System.out.println(arquivosFTP);
			 this.extrairDadosArquivos(arquivosFTP);
			 } catch (Exception e) {
			 e.printStackTrace();
			 LOG.error("Falha ao pegar dados FTP: ", e.getMessage());
			 }
			 }), 0, TEMPO_FTP, TimeUnit.MILLISECONDS);

			 exec.scheduleAtFixedRate(new Thread(() -> {
			 Thread t = new Thread(threadPasta);
			 try {
			 t.start();
			 this.extrairDadosArquivos(arquivosPasta);
			 } catch (Exception e) { }
			 }), 0, TEMPO_PASTA, TimeUnit.MILLISECONDS);

			this.extrairDadosArquivos(arquivosFTP);

		} catch (Exception e) {
			e.printStackTrace();
			// response.success = false;
			LOG.error("Falha ao importar dados: ", e.getMessage());
		}

		return args -> {
			System.out.println("Funcionouuu");
		};

	}

	public void extrairDadosArquivos(List<FileInputStream> arquivos) throws IOException, SQLException {

		List<Layout> layouts = this.layoutRepository.findAll(Sort.by("id").ascending());

		// Comentar dps
		FileInputStream arquivoTes = new FileInputStream("/home/william/Documents/caractere.csv");
		arquivos.add(arquivoTes);

		Layout lArquivo;

		for (FileInputStream arquivo : arquivos) {

			Map<String, List<String>> nomeTabelaColumnsMap = new HashMap<>();
			Map<String, List<List<String>>> nomeTabelaValuesMap = new HashMap<>();

			lArquivo = null;

			BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
			Scanner sc = new Scanner(arquivo, "UTF-8");

			for (Layout layout : layouts) {

				String a = layout.getNomeEmpresa();

				if (sc.findWithinHorizon(layout.getNomeEmpresa(), 0) != null) {
					lArquivo = layout;
					break;
				}
			}
			if (lArquivo == null) {
				System.out.println("layout nao encontrado para essa empresa, enviar para api da expresso!");
				continue;
			}

			this.conexao = new ConnectionPostgresJDBC();

			arquivo.getChannel().position(0);

			try {

				int count = 1;
				String linha = "";
				int valueCount = 0;

				while ((linha = br.readLine()) != null) {

					if (count == 1) {
						count++;
						continue;
					}

					List<LayoutArquivo> layoutArquivos = this.layoutArquivoRepository.findByIdLayout(lArquivo.getId());
					if (lArquivo.getDelimitador() == null) {
						String idLinhaArquivo = linha.substring(0, 3);
						for (LayoutArquivo layoutArquivo : layoutArquivos) {

							if (layoutArquivo.getIdentificadorLinha() == null) {
								this.montaSQL(lArquivo, layoutArquivo, linha, nomeTabelaColumnsMap,
										nomeTabelaValuesMap);
								valueCount++;
							} else {
								if (idLinhaArquivo.equals(layoutArquivo.getIdentificadorLinha())) {
									this.montaSQL(lArquivo, layoutArquivo, linha, nomeTabelaColumnsMap,
											nomeTabelaValuesMap);
									valueCount++;
								}
							}
						}
					} else {
						this.montaSQL(lArquivo, layoutArquivos.get(0), linha, nomeTabelaColumnsMap,
								nomeTabelaValuesMap);
						valueCount++;
					}

					if (valueCount > 0 && valueCount % 3 == 0) {
						this.gravarDadosBanco(nomeTabelaColumnsMap, nomeTabelaValuesMap);
						nomeTabelaValuesMap = new HashMap<>();
						valueCount = 0;
					}
					count++;
				}

				if (valueCount > 0) {
					this.gravarDadosBanco(nomeTabelaColumnsMap, nomeTabelaValuesMap);
					nomeTabelaValuesMap = new HashMap<>();
					valueCount = 0;
				}

				arquivo.close();
				sc.close();
			} catch (Exception e) {
				System.out.println("Rollback");
				this.conexao.rollback();

				// enviar para api da expresso que deu erro
				System.out.println(e.getMessage());
				LOG.error(e.getMessage());

				throw new RuntimeException(e);
			} finally {
				this.conexao.commit();
			}
		}

	}

	public void montaSQL(Layout layout, LayoutArquivo layoutArquivo, String linha,
			Map<String, List<String>> nomeTabelaColumnsMap, Map<String, List<List<String>>> nomeTabelaValuesMap) {

		List<LayoutArquivoCampoTabela> campos = this.layoutArquivoCampoTabelaRepository
				.findCampos(layoutArquivo.getId(), layoutArquivo.getIdLayout());

		try {
			for (LayoutArquivoCampoTabela campo : campos) {

				String nomeTabela = campo.getNomeTabela();
				String nomeCampo = campo.getNomeCampo();
				String caractere = campo.getCaractere();
				String value = "";

				if (caractere != null && caractere != "") {
					int index = Integer.parseInt(campo.getIndexador());

					List<String> dados = Arrays.asList(linha.split(layout.getDelimitador()));

					if (dados.size() > index) {
						value = dados.get(index);
					}

				} else {
					int posInicial = Integer.parseInt(campo.getPosicaoInicial());
					int posFinal = Integer.parseInt(campo.getPosicaoFinal());

					value = linha.substring(posInicial, posFinal).trim();
				}

				if (!nomeTabelaColumnsMap.containsKey(nomeTabela)) {
					nomeTabelaColumnsMap.put(nomeTabela, new ArrayList<>());
					nomeTabelaValuesMap.put(nomeTabela, new ArrayList<>());
					nomeTabelaValuesMap.get(nomeTabela).add(new ArrayList<>());
				}

				if (!nomeTabelaColumnsMap.get(nomeTabela).contains(nomeCampo)) {
					nomeTabelaColumnsMap.get(nomeTabela).add(nomeCampo);
				}

				if (campo.getJsonDepara() != null) {
					DeparaDTO depara = new Gson().fromJson(campo.getJsonDepara(), DeparaDTO.class);
					for (DeparaCampoDTO deparaCampo : depara.getCampos()) {
						if (value.equals(deparaCampo.getDe())) {
							value = deparaCampo.getPara();
						}
					}
				}

				value = value.replaceAll("[\\n\\r\\t]+", "");

				nomeTabelaValuesMap.get(nomeTabela).get(nomeTabelaValuesMap.get(nomeTabela).size() - 1).add(value);

			}

			for (String nomeTabela : nomeTabelaValuesMap.keySet()) {
				nomeTabelaValuesMap.get(nomeTabela).add(new ArrayList<>());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void gravarDadosBanco(Map<String, List<String>> nomeTabelaColumnsMap,
			Map<String, List<List<String>>> nomeTabelaValuesMap) {

		for (String nomeTabela : nomeTabelaColumnsMap.keySet()) {
			String columns = String.join(",", nomeTabelaColumnsMap.get(nomeTabela));
			String sql = "INSERT INTO " + nomeTabela + " (" + columns + ") VALUES ";
			try {
				String dados = "";

				for (List<List<String>> values : nomeTabelaValuesMap.values()) {
					for (List<String> cols : values) {
						if (cols.size() == 0)
							continue;
						String tmp = "";
						for (String value : cols)
							tmp += "'" + value + "',";
						tmp = tmp.replaceAll(",$", "");
						tmp = "(" + tmp + "),";
						dados += tmp;
					}
				}

				sql += dados;
				sql = sql.replaceAll(",$", "");

				PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sql);
				stmt.executeUpdate();
				stmt.close();
				// this.conexao.commit();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	// public void descobrirLayoutArquivo() {
	// List<Layout> layouts = this.layoutRepository.findAll();

	// }

	// public void gravarDadosBanco(LayoutArquivo layoutArquivo, String linha)
	// throws SQLException {

	// List<LayoutArquivoCampoTabela> campos =
	// this.layoutArquivoCampoTabelaRepository.findCampos(layoutArquivo.getId(),
	// layoutArquivo.getIdLayout());

	// Map<String, List<String>> nomeTabelaColumnsMap = new HashMap<>();
	// Map<String, List<List<String>>> nomeTabelaValuesMap = new HashMap<>();

	// //ler linha
	// try {
	// for (LayoutArquivoCampoTabela campo : campos) {

	// if (!nomeTabelaColumnsMap.containsKey(campo.getNomeTabela())) {
	// nomeTabelaColumnsMap.put(campo.getNomeTabela(), new ArrayList<>());
	// }
	// if
	// (!nomeTabelaColumnsMap.get(campo.getNomeTabela()).contains(campo.getNomeCampo()))
	// {
	// nomeTabelaColumnsMap.get(campo.getNomeTabela()).add(campo.getNomeCampo());
	// }

	// String caractere = campo.getCaractere();
	// if (caractere != null && caractere != "") {

	// String dados[] = linha.split(campo.getCaractere());

	// for (int i=0; dados.length>0;i++) {

	// if (Integer.parseInt(campo.getIndexador()) == i) {

	// DeparaDTO depara = new Gson().fromJson(campo.getJsonDepara(),
	// DeparaDTO.class);

	// for (DeparaCampoDTO deparaCampo : depara.getCampos()) {
	// if (dados[i].equals(deparaCampo.getDe())) {
	// dados[i] = deparaCampo.getPara();
	// }
	// }

	// //insert no banco
	// // String sqlInserir = "INSERT INTO " + campo.getNomeTabela()
	// // + " (" + campo.getNomeCampoTabela() + ") VALUES (?) ";

	// // PreparedStatement stmt =
	// this.conexao.getConnection().prepareStatement(sqlInserir);
	// // stmt.setString(1, dados[i]);

	// // stmt.executeUpdate();
	// // stmt.close();
	// }
	// }

	// } else {

	// //insert no banco
	// // String sqlInserir = "INSERT INTO " + campo.getNomeTabela()
	// // + " (" + campo.getNomeCampoTabela() + ") VALUES (?) ";

	// String valor = linha.substring(Integer.parseInt(campo.getPosicaoInicial()),
	// Integer.parseInt(campo.getPosicaoInicial()));

	// // String json = "{ 'campos':"
	// // + "["
	// // + "{ 'de': 't', 'para': 'true' },"
	// // + "{ 'de': 'f', 'para': 'false' }"
	// // + "]}";

	// DeparaDTO depara = new Gson().fromJson(campo.getJsonDepara(),
	// DeparaDTO.class);

	// for (DeparaCampoDTO deparaCampo : depara.getCampos()) {
	// if (valor.equals(deparaCampo.getDe())) {
	// valor = deparaCampo.getPara();
	// }
	// }

	// // PreparedStatement stmt =
	// this.conexao.getConnection().prepareStatement(sqlInserir);
	// // stmt.setString(1, valor);

	// // stmt.executeUpdate();

	// // stmt.close();
	// }
	// }
	// } catch (Exception e ) {
	// System.out.println(e.getMessage());
	// }
	// }

	public void gravarArquivosEnviadosPasta(InputStream arquivo) throws IOException {
		String baseArquivo = "C:\\Users\\maico\\Desktop\\ArquivosLayout";
		String nomeArquivo = "teste1";
		File diretorio = new File(baseArquivo);
		diretorio.mkdirs();

		FileOutputStream fos = new FileOutputStream(baseArquivo + nomeArquivo + ".txt");

		byte[] bytes = ByteStreams.toByteArray(arquivo);

		fos.write(bytes);
		fos.close();

		// salvar em um banco de dados mongo
	}

	// public void lerArquivosPasta(List<InputStream> arquivos) throws IOException,
	// SQLException, InterruptedException {
	//
	// MyThread threadPasta = new MyThread("Thread Pai", 1000);
	//
	//
	// List<FormaRecebimentoPasta> pastas =
	// this.formaRecebimentoPastaRepository.findAll();
	//
	// for (FormaRecebimentoPasta pasta : pastas) {
	//
	//
	// }
	//
	//
	// Path directory = Paths.get("C:/Users/maico/Desktop/ArquivosLayout/");
	//
	// WatchService watchService = directory.getFileSystem().newWatchService();
	//
	// WatchKey watchKey = directory.register(watchService,
	// StandardWatchEventKinds.ENTRY_CREATE,
	// StandardWatchEventKinds.ENTRY_MODIFY);
	//
	// while (true) {
	//
	// for (WatchEvent<?> event : watchKey.pollEvents()) {
	//
	// System.out.println("Event kind: " + event.kind());
	// System.out.println("Arquivo affected: " + event.context());
	//
	// // leio arquivo
	//// FileReader arquivo = new
	// FileReader("C:/Users/maico/Desktop/ArquivosLayout/" + event.context());
	//
	// InputStream arquivo = new
	// FileInputStream("C:/Users/maico/Desktop/ArquivosLayout/" + event.context());
	//
	// arquivos.add(arquivo);
	//
	// this.extrairDadosArquivos(arquivos);
	//
	// }
	//
	// // Isso significa que a instância da chave de monitoramento é removida da
	// fila
	// // do serviço
	// // de monitoramento toda vez que é retornada por uma operação de pesquisa. A
	// // chamada de
	// // API de redefinição coloca de volta na fila para aguardar mais eventos.
	// watchKey.reset();
	// }
	//
	//
	// }

	// public void lerArquivosEmail(List<InputStream> arquivos) {
	//
	// try {
	//
	// List<FormaRecebimentoEmail> emails =
	// this.formaRecebimentoEmailRepository.findAll();
	//
	//
	//// for(FormaRecebimentoEmail email : emails) {
	// ThreadEmail metodosEmail = new ThreadEmail();
	//
	// metodosEmail.lerEmails(new FormaRecebimentoEmail(Long.parseLong("1"),
	// "smtp.gmail.com", "maiconpgremio@gmail.com",
	// "maiconpgremio96", "995", "1"), arquivos);
	//// }
	//
	//
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// }

	// public void lerArquivosFTP(List<InputStream> arquivos) throws
	// SocketException, IOException {
	//
	// try {
	// Thread threadFTP = new Thread();
	//
	// threadFTP.start();
	//
	// threadFTP.sleep(this.TEMPO_FTP);
	//
	// List<FormaRecebimentoFTP> ftps =
	// this.formaRecebimentoFTPRepository.findAll();
	//
	//// for (FormaRecebimentoFTP ftp : ftps) {
	// ThreadFTP metodosFTP = new ThreadFTP();
	// metodosFTP.lerFTPs(new FormaRecebimentoFTP(), arquivos);
	//// }
	//
	// } catch (Exception e) {
	// System.out.println("Erro ao ler arquivos FTP: " + e.getMessage());
	// }
	//
	// }
}
