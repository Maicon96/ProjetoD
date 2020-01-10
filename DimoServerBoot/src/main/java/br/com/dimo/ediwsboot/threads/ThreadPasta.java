package br.com.dimo.ediwsboot.threads;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.dimo.ediwsboot.WsApplication;
import br.com.dimo.ediwsboot.entity.FormaRecebimentoPasta;
import br.com.dimo.ediwsboot.repository.FormaRecebimentoPastaRepository;

public class ThreadPasta implements Runnable {

	@Autowired
	private FormaRecebimentoPastaRepository formaRecebimentoPastaRepository;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(WsApplication.class);

	public static Integer TEMPO_PASTA = 10000;
	
	
	private String nomeThread;
    private List<InputStream> arquivos;
    
    public ThreadPasta(String nomeThread, List<InputStream> arquivos) {
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
        	
			this.lerPastas();
        	
//        	Thread.sleep(TEMPO_PASTA);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println(this.nomeThread + " Terminou a execução");
    }
    	

	public void lerPastas() throws Exception {

		try {
		
			Path directory = Paths.get("C:/Users/maico/Desktop/ArquivosLayout/");

			List<FormaRecebimentoPasta> pastas = this.formaRecebimentoEmailRepository.findAll();
			
			WatchService watchService = directory.getFileSystem().newWatchService();

			WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY);

			for (WatchEvent<?> event : watchKey.pollEvents()) {

				System.out.println("Event kind: " + event.kind());
				System.out.println("Arquivo affected: " + event.context());

				// leio arquivo
//					FileReader arquivo = new FileReader("C:/Users/maico/Desktop/ArquivosLayout/" + event.context());

				InputStream arquivo = new FileInputStream("C:/Users/maico/Desktop/ArquivosLayout/" + event.context());
	            
				this.arquivos.add(arquivo);
				
//					this.extrairDadosArquivos(arquivos);

			}		
		
		} catch (Exception ex) {
			throw new Exception("Falha ao ler arquivos de pasta: " + ex.getMessage());			
		}
	}
	
//	public void conectar(FormaRecebimentoPasta pasta) throws Exception {
//	
//	try {
//		BASE64Encoder enc = new sun.misc.BASE64Encoder();
//        //
////        URL url = new URL("http://100.100.100.177:8080/integracao/teste.txt");
//		
//		URL url = new URL(pasta.getCaminho());
//
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.addRequestProperty("Request-Method", "GET");
//        
//        connection.addRequestProperty("Authorization", "Basic " + enc.encode("user:password".getBytes()));
//
//        connection.setDoInput(true);
//        connection.setDoOutput(false);
//        connection.connect();
//
//        if (connection.getResponseCode() == 200) {//HttpURLConnection.HTTP_ACCEPTED
//        	
////        	connection.
//        	
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuffer newData = new StringBuffer();
//            String s = "";
//            while (null != ((s = br.readLine()))) {
//                newData.append(s);
//            }
//            br.close();
//            System.out.println(new String(newData));
//        }
//
//        System.out.println("Resultado: " + connection.getResponseCode() + "/" + connection.getResponseMessage());
//
//        connection.disconnect();
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//		LOG.error("Falha ao conectar ao Servidor: ", e.getMessage());
//	}
//
//}



}
