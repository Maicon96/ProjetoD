

export function formatCPFCNPJ(cpfcnpj) {

    cpfcnpj=cpfcnpj.replace(/\D/g,"")

    if (cpfcnpj.length <= 14) { //CPF            
        //Coloca um ponto entre o terceiro e o quarto dígitos
        cpfcnpj=cpfcnpj.replace(/(\d{3})(\d)/,"$1.$2")            
        //Coloca um ponto entre o terceiro e o quarto dígitos
        //de novo (para o segundo bloco de números)
        cpfcnpj=cpfcnpj.replace(/(\d{3})(\d)/,"$1.$2")            
        //Coloca um hífen entre o terceiro e o quarto dígitos
        cpfcnpj=cpfcnpj.replace(/(\d{3})(\d{1,2})$/,"$1-$2")

    } else { //CNPJ            
        //Coloca ponto entre o segundo e o terceiro dígitos
        cpfcnpj=cpfcnpj.replace(/^(\d{2})(\d)/,"$1.$2")            
        //Coloca ponto entre o quinto e o sexto dígitos
        cpfcnpj=cpfcnpj.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")            
        //Coloca uma barra entre o oitacpfcnpjo e o nono dígitos
        cpfcnpj=cpfcnpj.replace(/\.(\d{3})(\d)/,".$1/$2")            
        //Coloca um hífen depois do bloco de quatro dígitos
        cpfcnpj=cpfcnpj.replace(/(\d{4})(\d)/,"$1-$2")            
    }

    return cpfcnpj
}