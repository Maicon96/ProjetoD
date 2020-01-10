import React, { Component } from 'react';

import Grid from '../../../../containers/layout/Grid/Grid';
import FormaRecebimentoPastaCad from '../pasta/FormaRecebimentoPastaCad';

let showTitle;
let cliente;
let urlLoad;

export default class FormaRecebimentoPastaCon extends Component {

    constructor(props) {
        super(props);
        
        showTitle = true;
        cliente = props.cliente;

        urlLoad = '/service/forma/recebimento/pasta/load'

        if (typeof props.showTitle !== 'undefined') {
            showTitle = props.showTitle;
        }
        
        if (cliente > 0) {
            urlLoad = '/service/forma/recebimento/pasta/load/filter/cliente';
        }       

        this.state = {
            loading: false,
            cliente: "",
            data: [],
            dataEdit: {},
            dataDelete: [],
            visible: false
        };
    }

    componentDidMount() {        
        console.log("componentDidMount FormaRecebimentoPastaCon");
    }

    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }
    
    render() {

        const columns = [{        
            title: 'ID',
            width: 50,
            align: 'center',
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
            title: 'Diretório',
            width: 220,
            id: "caminho",
            dataIndex: 'caminho'        
        }];

        return (
            <Grid title={'Forma de Recebimento - Pasta'}
                showTitle={showTitle}
                columns={columns}
                paramsFilter={{id: cliente}}
                urlLoad={urlLoad}
                //urlLoad={'/service/forma/recebimento/pasta/load'}
                urlDelete={'/service/forma/recebimento/pasta/delete'}
                urlChamaCadastro={'/cadastro/forma/recebimento/pasta'}
                register={{ title: 'Forma de Recebimento - Pasta', scream: <FormaRecebimentoPastaCad data={this.state.dataEdit} /> }}
            />
        );

    }

}