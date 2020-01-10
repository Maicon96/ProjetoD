import React, { Component, FormGroup, FormControl, Validators, CpfCnpjValidator } from 'react';
import { Table, Divider } from 'antd';

import Grid from '../../../containers/layout/Grid/Grid';
import moment from 'moment';

import ClienteCad from './ClienteCad';

const metodos = require('../../../utils/Metodos');

export default class ClienteCon extends Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: false,
            data: [],
            dataEdit: {},
            chave: "",
            dataDelete: [],
            visible: false
        };
    }

    componentDidMount() {
    }

    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }

    render() {

        const columns = [{
            title: 'ID',
            width: 100,
            id: "idCliente",
            align: 'center',
            dataIndex: 'idCliente',   
            key: 'idCliente',         
            hidden: true
        }, {
            title: 'CPF/CNPJ',
            width: 160,
            align: 'center',
            id: "cpfcnpj",
            dataIndex: 'cliente.cpfcnpj',
            render: (text, record) => {
                return metodos.formatCPFCNPJ(text);                
            }
        }, {
            title: 'Nome',
            width: 220,
            id: "nome",
            dataIndex: 'cliente.nome'        
        }, {
            title: 'Layout',
            width: 220,
            id: "idLayout",
            dataIndex: 'idLayout',
            render: (text, record, index) => {
                return record.idLayout + " - " + record.layout.descricao;
            }   
        }, {
            title: 'Data Início',
            width: 90,
            align: 'center',
            id: "dataInicio",
            dataIndex: 'dataInicio',
            render: (text, record) => {      
                return moment(record.dataInicio).format("DD/MM/YYYY");
            }
        }, {
            title: 'Data Fim',
            width: 90,
            align: 'center',
            id: "dataFim",            
            dataIndex: 'dataFim',
            render: (text, record) => {
                let data = "";
                if (record.dataFim) {
                    data = moment(record.dataFim).format("DD/MM/YYYY");
                }
                return data;
            }
        }];

        return (
            <Grid title={'Clientes'}
                showTitle={true}
                columns={columns}
                urlLoad={'/service/layout/arquivo/clientes/load'}
                urlDelete={'/service/layout/arquivo/clientes/delete'}
                urlChamaCadastro={'/cadastro/clientes'}
                register={{ title: 'Clientes', scream: <ClienteCad data={this.state.dataEdit} /> }}
            />
        );

    }

}