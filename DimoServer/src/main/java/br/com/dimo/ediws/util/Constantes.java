package br.com.dimo.ediws.util;

public class Constantes {

	public static final Integer NAO_PERMITIDO = 1;
	public static final Integer PERMITIDO = 2;
	
	
	public static final String TOKEN_NAME = "token";
	public static final String LOGIN_USUARIO = "loginUsuario";
	public static final String NOME_USUARIO = "nomeUsuario";	
	public static final String OPCAO_PERMISSAO = "opcaoPermissao";
	public static final String RETORNO_PERMISSAO = "retornoPermissao";
	public static final String MSG_PERMISSAO = "msgPermissao";
	public static final String VALIDA_PERMISSAO = "validaPermissao";
	public static final String ID_AUDITORIA = "idAuditoria";	
	
	
	
	private static String getDescricaoTipoPermissao(String permissao) {
		String descricaoPermissao = "";
		switch (permissao) {
			case "inclusao":
				descricaoPermissao = "incluir";
				break;
			case "alteracao":
				descricaoPermissao = "alterar";
				break;
			case "exclusao":
				descricaoPermissao = "excluir";
				break;
			default:
				break;
		}
		return descricaoPermissao;
	}
	
	public static String getMsgPermitidoUsuarioIgualError(String nome) {
		return "Usuário não possui permissão para " + getDescricaoTipoPermissao(nome) + " registros de outro usuário";
	}

	public static String getMsgPermitidoFilialIgualError(String nome) {
		return "Usuário não possui permissão para " + getDescricaoTipoPermissao(nome) + " registros de outra filial";
	}
	
	public static String getMsgPermitidoUnidadeIgualError(String nome) {
		return "Usuário não possui permissão para " + getDescricaoTipoPermissao(nome) + " registros de outra unidade";
	}
	
}
