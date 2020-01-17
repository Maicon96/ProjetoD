package br.com.dimo.ediwsboot.threads;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dimo.ediwsboot.entity.FormaRecebimentoFTP;

public class ThreadFTP implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ThreadFTP.class);
	
	public static Integer TEMPO_EMAIL = 10000;
	
	
	private String nomeThread;
    private List<InputStream> arquivos;
    private List<FormaRecebimentoFTP> ftps;
    
	
    public ThreadFTP(String nomeThread,  List<InputStream> arquivos, List<FormaRecebimentoFTP> ftps) {
        this.nomeThread = nomeThread;
        this.arquivos = arquivos;
        this.ftps = ftps;
    }
    
    @Override
    public void run() {
        System.out.println("executando a thread: " + this.nomeThread);
        
        try {        	
			this.lerFTPs();
        	
        	Thread.sleep(TEMPO_EMAIL);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println(this.nomeThread + " Terminou a execução");
    }
    
	public void lerFTPs() throws NumberFormatException, Exception {
				
		for (FormaRecebimentoFTP ftp : ftps) {			
			FTPClient ftpClient = this.conectar(ftp);
			
			this.lerArquivos(ftpClient);
		}
	}
	
	public FTPClient conectar(FormaRecebimentoFTP ftp) throws Exception {
		FTPClient ftpClient = new FTPClient();
		
		try {
			ftpClient.connect(ftp.getServidor());
			ftpClient.login(ftp.getUsuario(), ftp.getSenha());
			ftpClient.changeWorkingDirectory(ftp.getDiretorio());
		} catch (Exception e) {
			e.printStackTrace();
//			response.success = false;
			LOG.error("Falha ao conectar ao FTP: ", e.getMessage());
		}
		
		return ftpClient;

	}


	public void lerArquivos(FTPClient ftpClient) throws Exception {

		try {
		
			FTPFile[] arquivosFTP = ftpClient.listFiles();

			System.out.println("Listando arquivos: \n");

			for (FTPFile file : arquivosFTP) {
				InputStream f = ftpClient.retrieveFileStream(file.getName());
				this.arquivos.add(f);
				f.close();				
				ftpClient.completePendingCommand();			
			}

			ftpClient.logout();
			ftpClient.disconnect();		
		
		} catch (Exception ex) {
			throw new Exception("Falha ao ler novas mensagens de FTP: " + ex.getMessage());			
		}
	}
	
}
