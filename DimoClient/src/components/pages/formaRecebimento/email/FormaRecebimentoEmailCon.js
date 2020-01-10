import React, { Component } from 'react';

import Grid from '../../../../containers/layout/Grid/Grid';
import FormaRecebimentoEmailCad from '../email/FormaRecebimentoEmailCad';

let showTitle;
let cliente;
let urlLoad;

export default class FormaRecebimentoEmailCon extends Component {

    constructor(props) {
        super(props);

        showTitle = true;        
        cliente = props.cliente;
        urlLoad = '/service/forma/recebimento/email/load'

        if (typeof props.showTitle !== 'undefined') {
            showTitle = props.showTitle;
        }
        if (cliente > 0) {
            urlLoad = '/service/forma/recebimento/email/load/filter/cliente';
        }
        
        this.state = {
            loading: false,
            chave: "",
            data: [],
            dataEdit: {},
            dataDelete: [],
            visible: false
        };
    }

    componentDidMount() {
        //this.onClickBtnRefresh();
        console.log("FormaRecebimentoEmailCon");
    }

    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }
    
    render() {

        const columns = [{        
            title: 'ID',
            width: 50,
            hidden: true,
            align: 'center',
            id: "id",
            dataIndex: 'id',
        }, {
            title: 'POP3',
            width: 150,
            id: "pop3",
            dataIndex: 'pop3',
        }, {
            title: 'Usuário',
            width: 120,
            id: "usuario",
            dataIndex: 'usuario'        
        }, {
            title: 'Porta',
            width: 70,
            align: 'center',
            id: "porta",
            dataIndex: 'porta'        
        }, {
            title: 'TLS/SSL',
            width: 100,
            id: "tlsSsl",
            dataIndex: 'tlsSsl',
            render: (text, record, index) => {
                if (text === "0") {
                    return '0 - Nenhum';
                } else if (text === "1") {
                    return '1 - TLS';
                } else if (text === "2") {
                    return '2 - SSL';
                }
            }
        }]; 

        return (
            <Grid title={'Forma de Recebimento - E-mail'}
                showTitle={showTitle}
                columns={columns}
                paramsFilter={{id: cliente}}
                urlLoad={urlLoad}
                //urlLoad={'/service/forma/recebimento/email/load'}
                urlDelete={'/service/forma/recebimento/email/delete'}
                urlChamaCadastro={'/cadastro/forma/recebimento/email'}                
                register={{ title: 'Forma de Recebimento - E-mail', scream: <FormaRecebimentoEmailCad data={this.state.dataEdit} /> }}
            />
        );

    }

}