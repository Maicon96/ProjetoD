package br.com.dimo.ediwsboot.threads;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.dimo.ediwsboot.entity.FormaRecebimentoFTP;
import br.com.dimo.ediwsboot.repository.FormaRecebimentoFTPRepository;

public class ThreadFTP implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ThreadFTP.class);
	
	public static Integer TEMPO_EMAIL = 10000;
	
	@Autowired
	private FormaRecebimentoFTPRepository formaRecebimentoFtpRepository;
	

	private String nomeThread;
    private List<InputStream> arquivos;
    
	
    public ThreadFTP(String nomeThread,  List<InputStream> arquivos) {
        this.nomeThread = nomeThread;
        this.arquivos = arquivos;
        Thread t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        System.out.println("executando a thread: " + this.nomeThread);
        
        try {
        	for (int i=0; i<6; i++) {
  	          System.out.println(this.nomeThread + " contador " + i);
  	          Thread.sleep(1000);
  	    	}
        	
//			this.lerFTPs();
        	
        	Thread.sleep(TEMPO_EMAIL);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println(this.nomeThread + " Terminou a execução");
    }
    
	public void lerFTPs() throws NumberFormatException, Exception {
		
		List<FormaRecebimentoFTP> ftps = this.formaRecebimentoFtpRepository.findAll();
		
		for (FormaRecebimentoFTP ftp : ftps) {			
			FTPClient ftpClient = this.conectar(ftp);
			
			this.lerArquivos(ftpClient);
		}
	}
	
	public FTPClient conectar(FormaRecebimentoFTP ftp) throws Exception {
		FTPClient ftpClient = new FTPClient();
		
		try {
//			crs3.unochapeco.edu.br
//			dimo
//			hackatruck

//			ftpClient.connect(ftp.getServidor());
//			ftpClient.login(ftp.getUsuario(), ftp.getSenha());

			ftpClient.connect("crs3.unochapeco.edu.br");
			ftpClient.login("dimo", "hackatruck");

			// ftpClient.makeDirectory("/arquivosEdi");
			ftpClient.changeWorkingDirectory("/arquivosEdi");

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
				System.out.println(file.getName());
				this.arquivos.add(ftpClient.retrieveFileStream(file.getName()));				
			}

			ftpClient.logout();
			ftpClient.disconnect();		
		
		} catch (Exception ex) {
			throw new Exception("Falha ao ler novas mensagens de emails: " + ex.getMessage());			
		}
	}
	

//	public void mostrarTodasMensagens() throws Exception {
//
//		// Atributos e bandeiras de todas as mensagens ..
//		Message[] msgs = folder.getMessages();
//
//		// Use uma FetchProfile adequado
//		FetchProfile fp = new FetchProfile();
//		fp.add(FetchProfile.Item.ENVELOPE);
////        folder.fetch(msgs, fp);
//
//		for (int i = 0; i < msgs.length; i++) {
//			System.out.println("--------------------------");
//			System.out.println("MENSAGEM #" + (i + 1) + ":");
//
//			if (msgs[i] instanceof Message)
//
//				pr(" ");
//			Address[] a;
//			String remetente = "";
//			// FROM
//			if ((a = msgs[i].getFrom()) != null) {
//				for (int j = 0; j < a.length; j++) {
//					pr("DE: " + a[j].toString());
//					remetente = a[j].toString();
//				}
//			}
//
//			// TO
//			String destinatario = "";
//			if ((a = msgs[i].getRecipients(Message.RecipientType.TO)) != null) {
//				for (int j = 0; j < a.length; j++) {
//					pr("PARA: " + a[j].toString());
//					destinatario = a[j].toString();
//				}
//			}
//
//			// SUBJECT
//			pr("ASSUNTO: " + msgs[i].getSubject());
//
//			// DATE
//			Date d = msgs[i].getSentDate();
//			pr("Enviado Dia: " + (d != null ? d.toString() : "UNKNOWN"));
//
//			String ct = msgs[i].getContentType();
//			try {
//				if (ct.contains("multipart/mixed")) {
//					pr("CONTENT-TYPE: " + (new ContentType(ct)).toString() + " tem anexo");
//
//					if (destinatario.contains("monitor@isdra.com.br")) {
//
//						try {
//							InputStream is = msgs[i].getInputStream();
//							File temp = new File("\\teste\\anexo\\");
//							if (!temp.exists()) {
//								if (!temp.mkdir()) {
//									System.out.println("false");
//								}
//							}
//							FileOutputStream fos = new FileOutputStream(
//									"\\teste\\anexo\\" + msgs[i].getFileName() + ".xls");
//							int x = -1;
//							while ((x = is.read()) != -1) {
//								fos.write((char) x);
//							}
//							fos.flush();
//							fos.close();
//							System.out.println("true");
//						} catch (IOException e) {
//							e.printStackTrace();
//							System.out.println("false");
//						} catch (MessagingException e) {
//							e.printStackTrace();
//							System.out.println("false");
//						} catch (Throwable e) {
//							e.printStackTrace();
//							System.out.println("false");
//						}
//
//					}
//
//				} else {
//					pr("CONTENT-TYPE: " + (new ContentType(ct)).toString() + " ñ tem anexo");
//				}
//			} catch (ParseException pex) {
//				pr("BAD CONTENT-TYPE: " + ct);
//			} finally {
//				store.close();
//			}
//
//			/*
//			 * Using isMimeType to determine the content type avoids fetching the actual
//			 * content data until we need it.
//			 */
//			if (msgs[i].isMimeType("text/plain")) {
//				pr("Este é um texto simples");
//				pr("---------------------------");
//				System.out.println((String) msgs[i].getContent());
//			} else {
//
//				// just a separator
//				pr("---------------------------");
//
//			}
//
//		}
//
//	}

}
