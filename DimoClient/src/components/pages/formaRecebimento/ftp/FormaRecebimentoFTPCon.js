import React, { Component } from 'react';

import Grid from '../../../../containers/layout/Grid/Grid';
import FormaRecebimentoFTPCad from '../ftp/FormaRecebimentoFTPCad';

let showTitle;
let cliente;
let urlLoad;

export default class FormaRecebimentoFTPCon extends Component {

    constructor(props) {
        super(props);
        showTitle = true;
                
        cliente = props.cliente;
        urlLoad = '/service/forma/recebimento/ftp/load'

        if (typeof props.showTitle !== 'undefined') {
            showTitle = props.showTitle;
        }
        if (cliente > 0) {
            urlLoad = '/service/forma/recebimento/ftp/load/filter/cliente';
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
    }

    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }

    render() {
        const columns = [{
            title: 'ID',
            width: 50,
            hidden: true,
            id: "id",
            dataIndex: 'id',        
        }, {
            title: 'Servidor',
            width: 150,
            id: "servidor",
            dataIndex: 'servidor',
        }, {
            title: 'Usuário',
            width: 70,
            id: "usuario",
            dataIndex: 'usuario'
        }, {
            title: 'Porta',
            width: 70,
            id: "porta",
            dataIndex: 'porta'
        }, {
            title: 'TLS/SSL',
            width: 70,
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
        }, {
            title: 'Diretório',
            width: 180,
            id: "diretorio",
            dataIndex: 'diretorio'
        }];

        return (
            <Grid title={'Forma de Recebimento - FTP'}
                showTitle={showTitle}
                columns={columns}
                paramsFilter={{id: cliente}}
                urlLoad={urlLoad}
                //urlLoad={'/service/forma/recebimento/ftp/load'}
                urlDelete={'/service/forma/recebimento/ftp/delete'}
                urlChamaCadastro={'/cadastro/forma/recebimento/ftp'}
                register={{ title: 'Forma de Recebimento - FTP', scream: <FormaRecebimentoFTPCad data={this.state.dataEdit} /> }}
            />
        );

    }

}