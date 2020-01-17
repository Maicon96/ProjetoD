package br.com.dimo.ediwsboot;

import java.io.InputStream;

import br.com.esm.jexpmongo.MongoIntegratorBuilder;
import br.com.esm.jexpmongo.query.ConditionCriteria;
import br.com.esm.jexpmongo.query.MongoQuery;

public class MongoIntegrator {

	
	public MongoIntegratorBuilder getMongo() {		
		return MongoIntegratorBuilder.builder()
			.withCollection("teste")
			.withConfigurationId("1111");
	}
	
//	public void downloadArquivosMongo() {
	public MongoIntegratorBuilder downloadArquivosMongo() throws Exception {
		
		ConditionCriteria query;
		
		
		return MongoIntegratorBuilder.builder()
			.withCollection("teste")
			.withConfigurationId("1111")
			.search()
			.query(MongoQuery.query().where("metadata.key").in("key1").and("key2"))
			.execute(MongoIntegratorBuilder.class);
		
	}
		
	public void removerArquivosMongo() {
//	public MongoIntegratorBuilder removerArquivosMongo() {
		
		MongoIntegratorBuilder.builder()
				.withCollection("teste")
				.withConfigurationId("1111")
				.deleteFiles()
				.withFieldForSearch("metadata.key")
				.addFileId("key")
				.execute();
//		
//		
//		return MongoIntegratorBuilder.builder()
//			.withCollection("teste")
//			.withConfigurationId("1111")
//			.deleteFiles()
//			.withObjectId(Boolean.TRUE)
//			.addFileId("id")
//			.execute();
	}
	
	
//	public MongoIntegratorBuilder uploadArquivosMongo() {	
	public void uploadArquivosMongo(InputStream arquivo) {	
			
//		return MongoIntegratorBuilder.builder()
//			.withCollection("teste")
//			.withConfigurationId("1111")
//			.saveFiles()
//			.addFile(
//					file().withName("ArquivoEnviadoTeste.jpg")
//							.withContentType(".jpg")
//							.withKey("key")
//							.withBase64("base64")
//							.addAttributeMetadata("chave", "valor")
//			)
//			.execute();
	}
	
}