package br.com.dimo.ediwsboot.threads;

import java.io.File;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.ContentType;
import javax.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.mail.pop3.POP3SSLStore;
import com.sun.mail.util.MailSSLSocketFactory;

import br.com.dimo.ediwsboot.WsApplication;
import br.com.dimo.ediwsboot.entity.FormaRecebimentoEmail;
import br.com.dimo.ediwsboot.repository.FormaRecebimentoEmailRepository;

public class ThreadEmail implements Runnable {
	
	@Autowired
	private FormaRecebimentoEmailRepository formaRecebimentoEmailRepository;
	

	private static final Logger LOG = LoggerFactory.getLogger(WsApplication.class);

	public static Integer TEMPO_EMAIL = 10000;
	
	private Session session = null;
	private Store store = null;
	private Folder folder;
	
	private String nomeThread;
    private List<InputStream> arquivos;
    
    public ThreadEmail(String nomeThread, List<InputStream> arquivos) {
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
        	
        	this.lerEmails();
        	
        	
//        	List<FormaRecebimentoEmail> emails = this.formaRecebimentoEmailRepository.findAll();
//    		
//    		for (FormaRecebimentoEmail email : emails) {
//    			System.out.println(this.nomeThread + " contador " + 1);
//    			
//    			this.conectar(email);
//    			
//    			this.lerMensagensNovas(this.arquivos);	    			
//    			    			
//    			Thread.sleep(1000);
//    			
//    		}
    		
//			this.lerEmails();
	    	
//	    	Thread.sleep(TEMPO_EMAIL);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println(this.nomeThread + " Terminou a execução");
    }
	
	public void lerEmails() throws NumberFormatException, Exception {
		List<FormaRecebimentoEmail> emails = this.formaRecebimentoEmailRepository.findAll();
		
		for (FormaRecebimentoEmail email : emails) {
			this.conectar(email);
			
			this.lerMensagensNovas(this.arquivos);	
		}
		
		
	}
	
	public void conectar(FormaRecebimentoEmail email) throws Exception {

		try {
			String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

			Properties pop3Props = new Properties();

			pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
			pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
			pop3Props.setProperty("mail.pop3.port", "110");
			pop3Props.setProperty("mail.pop3.socketFactory.port", email.getPorta());

			MailSSLSocketFactory sf = null;
			try {
				sf = new MailSSLSocketFactory();
			} catch (GeneralSecurityException e1) {
				e1.printStackTrace();
			}
			sf.setTrustAllHosts(true);
			pop3Props.put("mail.pop3.ssl.enable", "true");
			pop3Props.put("mail.pop3.ssl.socketFactory", sf);

//			URLName url = new URLName("pop3", "100.100.100.8", 995, "",username, password);
			URLName url = new URLName("pop3", email.getPop3(), Integer.parseInt(email.getPorta()), "", 
					email.getUsuario(), email.getSenha());

			session = Session.getInstance(pop3Props, null);
			store = new POP3SSLStore(session, url);
			store.connect();

			this.abrirCaixaEntrada("inbox");

		} catch (Exception e) {
			e.printStackTrace();
//			response.success = false;
			LOG.error("Falha ao conectar ao e-mail: ", e.getMessage());
		}

	}

	public void abrirCaixaEntrada(String folderName) throws Exception {

		// Abra a pasta de e-mails
		folder = store.getDefaultFolder();

		folder = folder.getFolder(folderName);

		if (folder == null) {
			throw new Exception("Falha ao abrir caixa de entrada");
		}

		// Tentar abrir para leitura / gravação
		try {

			folder.open(Folder.READ_WRITE);

		} catch (MessagingException ex) {	
			folder.open(Folder.READ_ONLY);
		} catch (Exception ex) {		
			throw new Exception("Falha ao abrir caixa de entrada");			
		}
	}

	public void lerMensagensNovas(List<InputStream> arquivos) throws Exception {

		try {
		
			// search for all "unseen" messages
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message messages[] = folder.search(unseenFlagTerm);
	
			if (messages.length == 0)
				System.out.println("No messages found.");
	
			for (int i = 52; i < messages.length; i++) {
				System.out.println("Message " + (i + 1));
				System.out.println("From : " + messages[i].getFrom()[0]);
				System.out.println("Subject : " + messages[i].getSubject());
				System.out.println("Sent Date : " + messages[i].getSentDate());
				System.out.println();
	
				String ct = messages[i].getContentType();
	
				if (ct.contains("multipart/mixed")) {
					
					arquivos.add(messages[i].getInputStream());
					
					pr("CONTENT-TYPE: " + (new ContentType(ct)).toString() + " tem anexo");
					InputStream is = messages[i].getInputStream();
					File temp = new File("\\teste\\anexo\\");
					if (!temp.exists()) {
						if (!temp.mkdir()) {
							System.out.println("false");
						}
					}
				}
			}
	
			folder.close(true);
			store.close();
		
		} catch (Exception ex) {
			throw new Exception("Falha ao ler novas mensagens de emails: " + ex.getMessage());			
		}
	}

	public int getQtdMensagens() throws Exception {
		return folder.getMessageCount();
	}

	public int getQtdNovasMensagens() throws Exception {
		return folder.getNewMessageCount();
	}

	static String indentStr = "                                               ";
	static int level = 0;

	/**
	 * Imprimir uma, possivelmente recuado string.
	 */
	public static void pr(String s) {

		System.out.print(indentStr.substring(0, level * 2));
		System.out.println(s);
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
