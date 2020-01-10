import { notification } from 'antd';
import axios from 'axios';


const variaveisGlobais = require('../variaveisGlobais');

export function Call(props) {
	console.log(props);

    async function Request(props) {
        if (props.url.indexOf("login") === -1) {
			axios.defaults.headers.post['Authorization'] = sessionStorage.getItem("Authorization");
			//axios.defaults.headers.post['idEmpresa'] = sessionStorage.getItem("idEmpresa");
			axios.defaults.headers.post['login'] = sessionStorage.getItem("login");
        }
        
        if (props.fnSetLoading) {
			props.fnSetLoading(true);
        }
        
        await axios({
			crossdomain: true,
			method: 'POST',
			timeout: 100000,
			headers: {
				'Content-Type': 'application/json; charset=UTF-8',
				'Access-Control-Allow-Origin': '*'
			},
			url: props.url,
			data: props.data
		})
		.then(response => {			
			if (props.fnSetLoading) {
				props.fnSetLoading(false);
			}

			if (props.beforeMsgSuccessTrue) {
				props.beforeMsgSuccessTrue(response);
			}

			if (response.data.msg !== "" && response.data.showMsg) {
				notification.success({
					message: 'Sucesso!',
					description: response.data.msg || response.message,
					duration: 3,
					onClose: () => {
						if (props.afterMsgSuccessTrue) {
							props.afterMsgSuccessTrue(response);
						}
					}
				});
			} else if (props.afterMsgSuccessTrue) {
				props.afterMsgSuccessTrue(response);
			}
			return;
		})
		.catch(error => {
			if (props.fnSetLoading) {
				props.fnSetLoading(false);
			}

			console.log(error);

			if (error.response) {
				const response = error.response;
				
				console.log(error.response);
				console.log(response.data);

				if (response.data.message === 'Falha ao gravar LOG de Auditoria! Exception: Token inválido!') {
					notification.error({
						message: response.data.error,
						description: response.data.message + ' Você será redirecionado para o login.',
						duration: 3,						
						onClose: () => {
							window.location.href = variaveisGlobais.default.baseURLClient + "/logout";
						}
					});
				} else if (response.data.message === 'Token expirado!') {
					notification.error({
						message: response.data.error,
						description: response.data.message + ' Você terá que se credenciar para continuar.',
						duration: 3,
						onClose: () => {
							// chamar aqui a tela de bloqueio
						}
					});
				} else {

					console.log("aquiii");

					if (props.beforeMsgSuccessFalse) {
						props.beforeMsgSuccessFalse(response);
					}

					let description = ''; 

					console.log(response);

					if (response.status == 404) {
						description = 'Status: ' + response.status + ' - Erro: Serviço indisponível - Url: ' + response.request.responseURL;
					} else {
						description = 'Status: ' + response.status + ' - Erro: ' + (response.data.msg || response.data.errors[0].msg) + ' - Url: ' + response.request.responseURL;
					}
										
					notification.error({
						message: (response.data.message || response.data.msg),						
						description:  description,
						duration: 10,
						style: {
							width: 600,
							marginLeft: 335 - 600,
						},
						onClose: () => {
							if (props.afterMsgSuccessFalse) {
								props.afterMsgSuccessFalse(response);
							}
						}
					});
				}
				return;
			} else {
				if (props.beforeMsgSuccessFailure) {
					props.beforeMsgSuccessFailure();
				}
				
				notification.error({
					message: 'Falha!',
					description: 'Falha na requisição.',
					duration: 10,
					onClose: () => {
						if (props.afterMsgSuccessFailure) {
							props.afterMsgSuccessFailure();
						}
					}
				});
				return;
			}
		});
    
    }
    
    Request(props);
    
}