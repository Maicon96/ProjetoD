package br.com.dimo.ediwsboot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.common.io.ByteStreams;

import br.com.dimo.ediwsboot.connection.ConexaoJDBC;
import br.com.dimo.ediwsboot.connection.ConnectionPostgresJDBC;
import br.com.dimo.ediwsboot.entity.Layout;
import br.com.dimo.ediwsboot.entity.LayoutArquivo;
import br.com.dimo.ediwsboot.entity.LayoutArquivoCampoTabela;
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


//	public static Integer TEMPO_PASTA = 1000;
//	public static Integer TEMPO_EMAIL = 1000;
//	public static Integer TEMPO_FTP = 1000;
	
	private ConexaoJDBC conexao = null;
	

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	public CommandLineRunner importarDados() {
		
		List<InputStream> arquivos = new ArrayList<InputStream>();
		
		try {
			
			while (true) {
				
//				ThreadPasta threadPasta = new ThreadPasta("Thread pasta", arquivos);
//				Thread tPasta = new Thread(threadPasta);
				
				ThreadEmail threadEmail = new ThreadEmail("Thread email", arquivos);
				Thread tEmail = new Thread(threadEmail);
				
//				ThreadFTP threadFTP = new ThreadFTP("Thread ftp", arquivos);
//				Thread tFTP = new Thread(threadFTP);
				
//				tPasta.start();
//				tPasta.sleep(10000);
//				
				tEmail.start();
//				tEmail.sleep(10000);
//				
//				tFTP.start();
//				tFTP.sleep(10000);
								
			}
			
//			while (true) {
////				this.lerArquivosEmail(arquivos);			
////				tEmail.sleep(this.TEMPO_EMAIL);
//
//				
////				this.lerArquivosFTP(arquivos);
//				
////				tFTP.sleep(this.TEMPO_EMAIL);	
//
//				
////				this.lerArquivosFTP(arquivos);
//				
//				String a = "";
//				
////				this.lerArquivosPasta(arquivos);
//				
////				tPasta.sleep(this.TEMPO_EMAIL);
//				
//				
//				this.extrairDadosArquivos(arquivos);
//			}	
			

			
		} catch (Exception e) {
			e.printStackTrace();
//			response.success = false;
			LOG.error("Falha ao importar dados: ", e.getMessage());
		}

		return args -> {
			System.out.println("Funcionouuu");
		};

	}

	
	public void extrairDadosArquivos(List<InputStream> arquivos) throws IOException, SQLException {
     
		for (InputStream arquivo : arquivos) {
			Reader targetReader = new InputStreamReader(arquivo);    
			
			BufferedReader br = new BufferedReader(targetReader);

			String linha = "";
			String identificadorEmpresa = "";
			int cont = 1;
			
			this.conexao = new ConnectionPostgresJDBC();

			try {
				Layout layout = new Layout();
				List<LayoutArquivo> layoutArquivos = new ArrayList<LayoutArquivo>();
				
				while ((linha = br.readLine()) != null) {
					
					if (cont == 1) {
						identificadorEmpresa = linha.substring(0, 3);
						
//						layout = this.layoutRepository.findByIdentificadorEmpresa(identificadorEmpresa);
						
						if (layout == null) {
							// layout nao encontrado para essa empresa, enviar para api da expresso

							String entrou = "";
						
							break;
						}

					} else {				    
						layoutArquivos = this.layoutArquivoRepository.findByIdLayout(layout.getId());
						
						String idLinhaArquivo = linha.substring(0, 3);	
						
						for (LayoutArquivo layoutArquivo : layoutArquivos) {
							
							if (layoutArquivo.getIdentificadorLinha() == null) {							
								this.gravarDadosBanco(layoutArquivo, linha);
							} else {
								if (idLinhaArquivo.equals(layoutArquivo.getIdentificadorLinha())) {								
									this.gravarDadosBanco(layoutArquivo, linha);
								}
							}				
						}
					}

					cont++;
				}

				br.close();
				
			} catch (Exception e) {
				this.conexao.rollback();
				
				//enviar para api da expresso que deu erro
				
				throw new RuntimeException(e);
			} finally {
				this.conexao.commit();
			}
		}	
	    
	}
	
	public void descobrirLayoutArquivo() {
		List<Layout> layouts = this.layoutRepository.findAll();
			
	}
	
	
	public void gravarDadosBanco(LayoutArquivo layoutArquivo, String linha) throws SQLException {
		
		List<LayoutArquivoCampoTabela> campos = this.layoutArquivoCampoTabelaRepository.findCampos(layoutArquivo.getId(),
				layoutArquivo.getIdLayout());
		
		
		//ler linha 						
		for (LayoutArquivoCampoTabela campo : campos) {
			
			if (!campo.getCaractere().equals("")) {
				
				String dados[] = linha.split(campo.getCaractere());
				
				for (int i=0; dados.length>0;i++) {
					
					if (Integer.parseInt(campo.getIndexador()) == i) {
						
						//insert no banco
						String sqlInserir = "INSERT INTO " + campo.getNomeTabela()
								+ " (" + campo.getNomeCampoTabela() + ") VALUES (?) ";
						
						PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlInserir);
						stmt.setString(1, dados[i]);
						
						stmt.executeUpdate();
						stmt.close();
					}										
				}
				
			} else {
			
				//insert no banco
				String sqlInserir = "INSERT INTO " + campo.getNomeTabela()
						+ " (" + campo.getNomeCampoTabela() + ") VALUES (?) ";
				
				String valor = linha.substring(Integer.parseInt(campo.getPosicaoInicial()), 
						Integer.parseInt(campo.getPosicaoInicial()));
				
				PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlInserir);
				stmt.setString(1, valor);
				
				stmt.executeUpdate();
				
				stmt.close();
			}
		}
	}
	
	public void gravarArquivosEnviadosPasta(InputStream arquivo) throws IOException {				
		String baseArquivo = "C:\\Users\\maico\\Desktop\\ArquivosLayout";
		String nomeArquivo = "teste1";
		File diretorio = new File(baseArquivo);
		diretorio.mkdirs();
		
		FileOutputStream fos = new FileOutputStream(baseArquivo + nomeArquivo + ".txt"); 
		
		byte[] bytes = ByteStreams.toByteArray(arquivo);
		
		fos.write(bytes);
		fos.close();	
		
		
		//salvar em um banco de dados mongo
	}

	

//	public void lerArquivosPasta(List<InputStream> arquivos) throws IOException, SQLException, InterruptedException {
//
//		MyThread threadPasta = new MyThread("Thread Pai", 1000);
//		
//		
//		List<FormaRecebimentoPasta> pastas = this.formaRecebimentoPastaRepository.findAll();
//		
//		for (FormaRecebimentoPasta pasta : pastas) {
//			
//			
//		}
//		
//		
//		Path directory = Paths.get("C:/Users/maico/Desktop/ArquivosLayout/");
//
//		WatchService watchService = directory.getFileSystem().newWatchService();
//
//		WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
//				StandardWatchEventKinds.ENTRY_MODIFY);
//
//		while (true) {
//
//			for (WatchEvent<?> event : watchKey.pollEvents()) {
//
//				System.out.println("Event kind: " + event.kind());
//				System.out.println("Arquivo affected: " + event.context());
//
//				// leio arquivo
////				FileReader arquivo = new FileReader("C:/Users/maico/Desktop/ArquivosLayout/" + event.context());
//
//				InputStream arquivo = new FileInputStream("C:/Users/maico/Desktop/ArquivosLayout/" + event.context());
//	            
//				arquivos.add(arquivo);
//				
//				this.extrairDadosArquivos(arquivos);
//
//			}
//
//			// Isso significa que a instância da chave de monitoramento é removida da fila
//			// do serviço
//			// de monitoramento toda vez que é retornada por uma operação de pesquisa. A
//			// chamada de
//			// API de redefinição coloca de volta na fila para aguardar mais eventos.
//			watchKey.reset();
//		}		
//		
//
//	}

//	public void lerArquivosEmail(List<InputStream> arquivos) {
//
//		try {
//		
//			List<FormaRecebimentoEmail> emails = this.formaRecebimentoEmailRepository.findAll();
//				
//			
////			for(FormaRecebimentoEmail email : emails) {
//				ThreadEmail metodosEmail = new ThreadEmail();
//				
//				metodosEmail.lerEmails(new FormaRecebimentoEmail(Long.parseLong("1"), "smtp.gmail.com", "maiconpgremio@gmail.com", 
//						"maiconpgremio96", "995", "1"), arquivos);
////			}		
//			
//			
//		} catch (Exception e) {
//	          throw new RuntimeException(e);
//	    }
//	}

//	public void lerArquivosFTP(List<InputStream> arquivos) throws SocketException, IOException {
//		
//		try {
//			Thread threadFTP = new Thread();
//			
//			threadFTP.start();
//			
//			threadFTP.sleep(this.TEMPO_FTP);	
//			
//			List<FormaRecebimentoFTP> ftps = this.formaRecebimentoFTPRepository.findAll();
//			
////			for (FormaRecebimentoFTP ftp : ftps) {
//				ThreadFTP metodosFTP = new ThreadFTP();
//				metodosFTP.lerFTPs(new FormaRecebimentoFTP(), arquivos);
////			}
//			
//		} catch (Exception e) {
//			System.out.println("Erro ao ler arquivos FTP: " + e.getMessage());
//		}	
//		
//	}
}
