package br.com.dimo.ediwsboot.integracaoes.configuracoes;

import br.com.esm.jexpconfiguracoes.annotations.Configuration;
import br.com.esm.jexpconfiguracoes.base.ExpConfiguracoesBase;
import br.com.esm.jexpconfiguracoes.enums.BuildMode;
import lombok.Getter;


@Getter	
public class Configuracoes extends ExpConfiguracoesBase {

	
	@Override
	public String getChaveConfiguracoes() {
		return "8201dd79-5810-4681-87cd-a6bbef933dd1";
	}
	
	@Override
	public BuildMode getBuildMode() {		
		return BuildMode.DEBUG;
	}
	
	
	@Configuration(name = "MAXIMO_TENTATIVAS_ENVIO", defaultValue = "3")
	private Integer maximoTentativasIntegracao;
	
	@Configuration(name = "INTERVALO_EXECUCAO_INTEGRACAO", defaultValue = "10")
	private Integer intervaloTentativasIntegracao;
	
	@Configuration(name = "ACRESCIMO_TEMPO_TENTATIVA", defaultValue = "300")
	private Integer acrescimoTempoTentativa;
	
	@Configuration(name = "API_INTEGRACAO_BASE_URL")
	private Integer wsIntegracaoBaseURL;
		
}
