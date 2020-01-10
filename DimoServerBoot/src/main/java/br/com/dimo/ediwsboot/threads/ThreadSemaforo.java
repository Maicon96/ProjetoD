package br.com.dimo.ediwsboot.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dimo.ediwsboot.connection.ConexaoJDBC;
import br.com.dimo.ediwsboot.connection.ConnectionPostgresJDBC;
import br.com.dimo.ediwsboot.entity.Layout;
import br.com.dimo.ediwsboot.entity.LayoutArquivo;
import br.com.dimo.ediwsboot.entity.LayoutArquivoCampoTabela;
import br.com.dimo.ediwsboot.repository.LayoutArquivoCampoTabelaRepository;
import br.com.dimo.ediwsboot.repository.LayoutArquivoRepository;

public class ThreadSemaforo implements Runnable {
	
	@Autowired
	private LayoutArquivoRepository layoutArquivoRepository;
	
	@Autowired
	private LayoutArquivoCampoTabelaRepository layoutArquivoCampoTabelaRepository;
	
	
	
	private ConexaoJDBC conexao = null;
	
	private int idThread;
	private Semaphore semaforo;

	
	public ThreadSemaforo(int id, Semaphore semaphore) {
        this.idThread = id;
        this.semaforo = semaphore;
    }
	
	private void processar() {
	    try {
	        System.out.println("Thread #" + idThread + " processando");
	        Thread.sleep((long) (Math.random() * 10000));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void entrarRegiaoNaoCritica() {
	    System.out.println("Thread #" + idThread + " em região não crítica");
	    processar();
	}
	
	private void entrarRegiaoCritica() {
	    System.out.println("Thread #" + idThread
	            + " entrando em região crítica");
	    processar();
	    System.out.println("Thread #" + idThread + " saindo da região crítica");
	}
	
	public void run() {
	    entrarRegiaoNaoCritica();
	    try {
	        semaforo.acquire();
	        
//	        this.extrairDadosArquivos(arquivos);
	        
	        entrarRegiaoCritica();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    } finally {
	        semaforo.release();
	    }
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

}
