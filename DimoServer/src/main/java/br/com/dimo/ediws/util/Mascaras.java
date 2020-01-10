package br.com.dimo.ediws.util;

public class Mascaras {

    /*Máscara de CPF*/
    public static String mascaraCPF(String cpf) {
        if (cpf != null && !cpf.equals("")) {
            cpf = cpf.replaceAll("[^\\d]", "");        
            String p1 = cpf.substring(0,3);
            String p2 = cpf.substring(3,6);
            String p3 = cpf.substring(6,9);
            String p4 = cpf.substring(9,11);
            cpf = p1 + "." + p2 + "." + p3 + "-" + p4;
        }
        return cpf;    
    }
   
    /*Máscara de CNPJ*/
    public static String mascaraCNPJ(String cnpj) {
        if (cnpj != null && !cnpj.equals("")) {
            cnpj = cnpj.replaceAll("[^\\d]", "");        
            String p1 = cnpj.substring(0,2);
            String p2 = cnpj.substring(2,5);
            String p3 = cnpj.substring(5,8);
            String p4 = cnpj.substring(8,12);
            String p5 = cnpj.substring(12,14);
            cnpj = p1 + "." + p2 + "." + p3 + "/" + p4 + "-" + p5;
        }
        return cnpj;    
    }

//    /*Máscara de CEP*/
//    public static String mascaraCEP(String cep) {
//        if (cep != null && cep.length() > 3) {
//            String p1 = cep.substring(0, cep.length()-3);
//            String p2 = cep.substring(cep.length()-3);
//            cep = p1 + "-" + p2;
//        }
//        return cep;                
//    }
//
//    /*Máscara de CEP com separador milhar*/
//    public static String mascaraCEPMilhar(String cep) {
//        if (cep != null && cep.length() > 3) {
//            String p1 = cep.substring(0,2);
//            String p2 = cep.substring(2,5);
//            String p3 = cep.substring(cep.length()-3);
//            cep = p1 + "." + p2 + "-" + p3;
//        }
//        return cep;                
//    }
//    
//    /*Máscara de Fone/Fax*/
//    public static String mascaraFone(String fone) {
//        if (fone != null && fone.length() > 4) {
//            String p1 = fone.substring(0, fone.length()-4);
//            String p2 = fone.substring(fone.length()-4);
//            fone = p1 + "-" + p2;
//        }
//        return fone;                
//    }
//    
//    /*Máscara de CNAE Fiscal*/
//    public static String mascaraCNAE(String value) {
//    	if (value != null) {
//	        if (value.length() > 2) {
//	            String p1 = value.substring(0, value.length()-2);
//	            String p2 = value.substring(value.length()-2);
//	            value = p1 + "/" + p2;
//	        }
//	        if (value.length() > 4) {
//	            String p1 = value.substring(0, value.length()-4);
//	            String p2 = value.substring(value.length()-4);
//	            value = p1 + "-" + p2;
//	        }
//    	}
//        return value;                
//    }
//    
//    /*Máscara de Dígito*/
//    public static String mascaraDigito(String value) {
//        if (value != null && value.length() > 1) {
//            String p1 = value.substring(0, value.length()-1);
//            String p2 = value.substring(value.length()-1);
//            value = p1 + "-" + p2;
//        }
//        return value;                
//    }
//    
//    /*Máscara de conta que possui níveis*/
//    public static HashMap<String, Object> mascaraContaNiveis(String conta, ContaDTO contaDTO) throws Exception {        
//		HashMap<String, Object> result = new HashMap<>();
//		String cta = conta.replaceAll("[^\\d]", "");
//		String ctaMascara = "";
//		int ctaGrau = 0;
//		int nivGrau = 0;
//		int inicio = 0;
//		int fim = 0;
//          
//        nivGrau = contaDTO.niveis.size();        
//        for (int i=0; i < nivGrau; i++) {
//            inicio = fim;
//            fim = inicio + contaDTO.niveis.get(i).digitos;
//            String dig = cta;
//            if (cta.length() > inicio) {
//                if (cta.length() > fim) {
//                    dig = cta.substring(inicio, fim);
//                } else {
//                    dig = cta.substring(inicio);
//                    i = nivGrau;
//                }
//            } else {
//                i = nivGrau;
//            }
//            if (!ctaMascara.equals("")) {
//                ctaMascara += ".";
//            }
//            ctaMascara += dig;
//            ctaGrau++;
//        }                
//        if (ctaMascara.equals("")) {
//            ctaMascara = cta;
//        }
//        
//        result.put("contaMascara", ctaMascara);
//        result.put("contaGrau", ctaGrau);
//        result.put("nivelGrau", nivGrau);
//        return result;
//    }
//        
//    /*Máscara de Vínculo*/
//    public static String mascaraVinculo(String tipo, String vinculo) {
//        if (tipo.equals("1")) {
//            vinculo = vinculo.replaceAll("[^\\w]", "");
//            if (vinculo.length() == 9) {
//                String p1 = vinculo.substring(0,3);
//                String p2 = vinculo.substring(3,6);
//                String p3 = vinculo.substring(6);
//                vinculo = p1 + "." + p2 + "." + p3;
//            }
//            else {
//                String p1 = vinculo.substring(0,2);
//                String p2 = vinculo.substring(2,5);
//                String p3 = vinculo.substring(5);
//                vinculo = p1 + "." + p2 + "." + p3;
//            }
//        }
//        if (tipo.equals("2")) {
//            String p1 = vinculo.substring(0, vinculo.length()-1);
//            String p2 = vinculo.substring(vinculo.length()-1);
//            vinculo = p1 + "-" + p2;
//        }
//        return vinculo;                
//    }
//    
//    /*Máscara de Placa*/
//    public static String mascaraPlaca(String value) {
//        if (value != null && value.length() > 0) {
//            String p1 = value.substring(0, 3);
//            String p2 = value.substring(3);
//            value = p1 + "-" + p2;
//        }
//        return value;                
//    }
//    
	
}
