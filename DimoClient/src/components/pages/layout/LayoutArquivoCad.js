import React, { Component } from 'react';
import {
    Form, Select, Input, Switch, Divider, Tabs, Row, Col, Button,
    InputNumber, Icon, Table, Collapse, notification
} from 'antd';
import debounce from 'lodash/debounce';

const { TabPane } = Tabs;

const ajax = require('../../../utils/ajax/ConexaoAjax');
const globalVariables = require('../../../utils/variaveisGlobais');

const { Option } = Select;

const formItemLayout = {
    labelCol: { span: 10 },
    wrapperCol: { span: 12 }
};

const FormItem = Form.Item;

const { Panel } = Collapse;

let copia = '';
let hiddenCopia = '';
let descricao = '';
let padrao = '';
let linhas = {};
let tabelas = [];
let item = [];



const jsonTeste = {
    layout: {
        descricao: "layout proceda",
        padrao: 1
    },
    cadastroLayoutArquivoDTOs: [
        {
            identificadorLinha: "584",
            cadastroLayoutArquivoDTOs: [
                {
                    caractere: "",
                    indexador: "",
                    posicaoInicial: "1",
                    posicaoFinal: "10",
                    nomeCampo: "",
                    nomeCampoTabela: "nome_empresa",
                    jsonDepara: "",
                    nomeTabela: ""
                },
                {
                    caractere: "",
                    indexador: "",
                    posicaoInicial: "11",
                    posicaoFinal: "25",
                    nomeCampo: "Código Produto",
                    nomeCampoTabela: "cod_produto",
                    jsonDepara: "",
                    nomeTabela: "tbl_produtos"
                },
                {
                    caractere: "",
                    indexador: "",
                    posicaoInicial: "26",
                    posicaoFinal: "40",
                    nomeCampo: "Descrição Produto",
                    nomeCampoTabela: "descricao_produto",
                    jsonDepara: "",
                    nomeTabela: "tbl_produtos"
                }
            ]
        }
    ]

};


const EditableContext = React.createContext();

const EditableRow = ({ form, index, ...props }) => (
    <EditableContext.Provider value={form}>
        <tr {...props} />
    </EditableContext.Provider>
);

const EditableFormRow = Form.create()(EditableRow);

class EditableCell extends React.Component {
    state = {
        editing: false,
    };

    toggleEdit = () => {
        const editing = !this.state.editing;
        this.setState({ editing }, () => {
            if (editing) {
                this.input.focus();
            }
        });
    };

    save = e => {
        const { record, handleSave } = this.props;
        this.form.validateFields((error, values) => {
            if (error && error[e.currentTarget.id]) {
                return;
            }
            this.toggleEdit();
            handleSave({ ...record, ...values });
        });
    };

    renderCell = form => {
        this.form = form;
        const { children, dataIndex, record, title } = this.props;
        const { editing } = this.state;
        return editing ? (
            <Form.Item style={{ margin: 0 }}>
                {form.getFieldDecorator(dataIndex, {
                    rules: [
                        {
                            required: true,
                            message: `${title} é necessário.`,
                        },
                    ],
                    initialValue: record[dataIndex],
                })(<Input ref={node => (this.input = node)} onPressEnter={this.save} onBlur={this.save} />)}
            </Form.Item>
        ) : (
                <div
                    className="editable-cell-value-wrap"
                    style={{ paddingRight: 24 }}
                    onClick={this.toggleEdit}
                >
                    {children}
                </div>
            );
    };

    render() {
        const {
            editable,
            dataIndex,
            title,
            record,
            index,
            handleSave,
            children,
            ...restProps
        } = this.props;
        return (
            <td {...restProps}>
                {editable ? (
                    <EditableContext.Consumer>{this.renderCell}</EditableContext.Consumer>
                ) : (
                        children
                    )}
            </td>
        );
    }
}

class LayoutArquivoCad extends Component {

    constructor(props) {
        super(props);

        //responsavel por pegar o input que se deseja jogar o foco
        this.inputFocus = React.createRef();

        this.key = 1;

        this.state = {
            loading: false,
            confirmDirty: false,
            data: [],
            value: [],
            fetching: false,
            linhas: [],
            tipo_delimitador_arquivo: '1',
            tipo_delimitador_empresa: '1',
            components: null,
            columns: []
        };

        this.hiddenCopia = true;

        if (this.props.location.params) {
            if (this.props.location.params.record) {
                this.copia = props.location.params.record.copia;
                this.descricao = props.location.params.record.descricao;
                this.padrao = props.location.params.record.padrao;
            }
        }

        this.fetchLayouts = debounce(this.fetchLayouts, 800);



        this.getColumnsTabela(1);
    }

    callback(key) {

        console.log(key);

        //activeTab = key;

        //console.log(activeTab);
    }

    teste() {
        console.log("entrou nessa merda");
    }


    handleSave = row => {
        let linhas = this.state.linhas;
        let tabela = linhas[row.codLinha].tabelas[row.nomeTabela];
        const newData = [...tabela.dados];

        const index = newData.findIndex(item => row.key === item.key);

        const item = newData[index];

        let ok = row.posInicial < row.posFinal;

        if (index !== 0) {
            const itemAbove = newData[index - 1];
            ok &= row.posInicial > itemAbove.posFinal;
        }


        if (ok) {
            linhas[row.codLinha].tabelas[row.nomeTabela].problema = false;
            newData.splice(index, 1, {
                ...item,
                ...row,
            });
            linhas[row.codLinha].tabelas[row.nomeTabela].dados = newData;
        } else
            linhas[row.codLinha].tabelas[row.nomeTabela].problema = true;
        this.setState({ linhas });
    };

    componentDidMount() {
        const me = this;

        if (this.props.location.params) {

            console.log(this.props.location.params);
            console.log(this.props.location.params.record);

            if (this.props.location.params.record) {

                this.props.form.setFieldsValue(this.props.location.params.record);

                //this.props.form.setFieldsValue({ idLayout: layoutDescricao });
            }
        }
    }


    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }

    lerPasta = () => {
        const me = this,
            form = this.props.form


        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo/ler/arquivos',
            afterMsgSuccessTrue: function () {

            }
        });

    }

    getLayouts = (form) => {
        let values = [];

        const linhas = this.state.linhas;

        for (let l of Object.keys(linhas)) {
            let d = [];
            let tmp = {
                identificadorLinha: l,
                cadastroLayoutArquivoDTOs: []
            };
            for (let t of Object.keys(linhas[l].tabelas)) {
                for (let row of linhas[l].tabelas[t].dados) {
                    d.push({
                        caractere: form.caractere,
                        indexador: "",
                        posicaoInicial: row.posInicial,
                        posicaoFinal: row.posFinal,
                        nomeCampo: row.Campo,
                        nomeCampoTabela: row.Campo,
                        jsonDepara: "",
                        nomeTabela: t
                    });
                }
            }
            tmp.cadastroLayoutArquivoDTOs = d;

            values.push(tmp);
        }
        return values;
    }

    onSubmit = e => {
        const me = this;
        e.preventDefault();

        this.props.form.validateFields((err, values) => {
            if (err) {

                notification.error({
                    message: 'Erro ao salvar layout!',
                    description: 'Há informações que não foram preenchidas corretamente.',
                    duration: 3,
                });
                return;
            }
            const json = {
                layout: {
                    linha: values.linha_empresa,
                    posicao_inicial: values.posicao_inicial,
                    posicao_final: values.posicao_final,
                    delimitador: values.caractere_empresa,
                    indexador: "",

                    descricao: values.descricao,
                    padrao: values.padrao ? 1 : 0
                },
                cadastroLayoutArquivoDTOs: this.getLayouts(values)
            };

            ajax.Call({
                url: globalVariables.default.baseURLServer + '/service/layout/arquivo/save',
                data: json,
                fnSetLoading: me.onSetLoading,
                afterMsgSuccessTrue: function (response) {
                    //me.props.form.setFieldsValue(response.data.registro);
                    //me.onFocus();
                }
            });
        });
    };

    onClickBtnNovo = () => {
        const form = this.props.form;
        form.resetFields();

        //this.onFocus();
    }

    onClickBtnDelete = () => {
        const me = this,
            form = this.props.form,
            id = form.getFieldValue('id');

        if (id) {
            ajax.Call({
                url: globalVariables.default.baseURLServer + '/service/clientes/delete',
                data: [{
                    id: id
                }],
                afterMsgSuccessTrue: function () {
                    me.onClickBtnNovo();
                }
            });
        }
    }

    onFocus = () => {
        this.inputFocus.current.focus();
    }

    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }

    onConfirmBlur = e => {
        const value = e.target.value;
        this.setState({ confirmDirty: this.state.confirmDirty || !!value });
    };

    handlechangeCPF(value) {

        console.log("entrou no handlechange");

        /*this.setState({
            cpfcnpj: metodos.formatCPFCNPJ(value.target.value), value,
            data: [],
            fetching: false,
        })*/

        //this.props.form.setFieldsValue({ cpfcnpj: metodos.formatCPFCNPJ(value.target.value) });

    }

    fetchLayouts = value => {
        const me = this;

        this.lastFetchId += 1;
        const fetchId = this.lastFetchId;
        this.setState({ data: [], fetching: true });

        this.buscarLayouts(value);
    };

    onFocusLayout = (e) => {
        this.buscarLayouts("");
    }

    buscarLayouts(descricao) {
        const me = this;

        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo/load/filter',
            fnSetLoading: me.onSetLoading,
            data: {
                descricao: descricao
            },
            afterMsgSuccessTrue: function (response) {
                const data = response.data.registros.map(user => ({
                    text: `${user.id} - ${user.descricao}`,
                    value: user.id,
                }));

                me.setState({ data: data, fetching: false });
            }
        });
    }


    buscarTabela(nome) {
        const me = this;

        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo//buscar/tabela',
            fnSetLoading: me.onSetLoading,
            data: {
                nomeTabela: nome
            },
            afterMsgSuccessTrue: function (response) {
                const data = response.data.registros.map(user => ({
                    text: `${user.id} - ${user.descricao}`,
                    value: user.id,
                }));

                me.setState({ data: data, fetching: false });
            }
        });
    }


    handlechangeDataInicio(date, dateString) {
        const me = this;

        if (dateString != null) {
            me.dataInicio = dateString.toString();
        }
    }

    handlechangeDataFim(date, dateString) {
        const me = this;

        if (dateString != null) {
            me.dataFim = dateString.toString();
        }
    }

    nomeTabelaChanged = (e) => {
        let linhas = this.state.linhas;
        linhas[e.target.name].inputTabela = e.target.value;
        this.setState({ linhas });
    }

    adicionarLinha = () => {
        this.props.form.validateFields((err, values) => {
            if (values.linha > 0) {


                let codLinha = values.linha;
                let descricaoLinha = 'Linha ' + codLinha;

                const config = {
                    codLinha: codLinha,
                    descricaoLinha: descricaoLinha,
                    tabelas: {},
                    nomeTabelas: [],
                    inputTabela: ""
                };

                linhas[codLinha] = config;

                this.setState({ linhas });

            }
        });
    }

    deleteTabela = (codLinha, nomeTabela) => {
        let linhas = this.state.linhas;
        let tabelas = linhas[codLinha].tabelas;
        let index = linhas[codLinha].nomeTabelas.indexOf(nomeTabela);
        delete linhas[codLinha].nomeTabelas[index];
        delete tabelas[nomeTabela];
        linhas[codLinha].tabelas = tabelas;
        this.setState({ linhas });
    }

    deleteLinha = (codLinha) => {
        let linhas = this.state.linhas;
        delete linhas[codLinha];
        this.setState({ linhas });
    }

    adicionarTabela = (codLinha) => {
        const me = this; 

        this.getColumnsTabela(this.state.tipo_delimitador_arquivo);

        let linha = this.state.linhas[codLinha];

        //this.buscarTabela("planos");

        console.log(linha.inputTabela);

        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo//buscar/tabela',
            fnSetLoading: me.onSetLoading,
            data: {
                nomeTabela: linha.inputTabela
            },            
            afterMsgSuccessTrue: function (response) {               
                console.log(response);
                console.log(response.data.registro.campos);

                const data = response.data.registro.campos.map(campo => ({                                 
                    Campo: campo.nomeCampo,
                    tipoCampo: "String",
                    posInicial: 0,
                    posFinal: 0,
                    codLinha: codLinha,
                    nomeTabela: linha.inputTabela,
                    key: this.key++
                }));

                linha.tabelas[linha.inputTabela] = {
                    dados: data,
                    nome: linha.inputTabela,
                    problema: false,
                    key: this.key++,
                };

                console.log("aqqq");
                console.log(linha.tabelas);
        
                linha.nomeTabelas.push(linha.inputTabela);
                linha.inputTabela = "";      
                linhas[codLinha] = linha;
                me.setState({ linhas: linhas });
            }
        });


        /*const data = [
            {
                Campo: "Nome",
                tipoCampo: "string",
                posInicial: 10,
                posFinal: 15,
                codLinha: codLinha,
                nomeTabela: linha.inputTabela,
                key: this.key++,
                posicao: 1,
            },
            {
                Campo: "Nome",
                tipoCampo: "string",
                posInicial: 16,
                posFinal: 20,
                codLinha: codLinha,
                nomeTabela: linha.inputTabela,
                key: this.key++,
                posicao: 2,
            }
        ];

        linha.tabelas[linha.inputTabela] = {
            dados: data,
            nome: linha.inputTabela,
            problema: false,
            key: this.key++,
        };

        linha.nomeTabelas.push(linha.inputTabela);

        linha.inputTabela = "";

        linhas[codLinha] = linha;

        this.setState({ linhas: linhas });

        */

    }

    tipoDelimitadorInfosChanged = (value) => {
        console.log(`VALOR: ${value}`);
        this.setState({ tipo_delimitador_arquivo: value });
        console.log(`VALOR: ${value}`);
        this.getColumnsTabela(value);
        console.log(`VALOR: ${value}`);

        // console.log(this.state.columns);
    }

    tipoDelimitadorEmpresaChanged = (value) => {
        this.setState({ tipo_delimitador_empresa: value });
    }

    getColumnsTabela = (tipo) => {

        var colunasTabela = [
            {
                title: 'Campo',
                dataIndex: 'Campo',
            },
            {
                title: 'Tipo Campo',
                dataIndex: 'tipoCampo'
            },
        ];

        console.log(tipo);

        if (tipo == 1) {
            colunasTabela.push(
                {
                    title: 'Posição',
                    dataIndex: 'posição',
                    editable: true
                });
        } else if (tipo == 2) {
            colunasTabela.push(
                {
                    title: 'Pos Inicial',
                    dataIndex: 'posInicial',
                    editable: true
                });
            colunasTabela.push(
                {
                    title: 'Pos Final',
                    dataIndex: 'posFinal',
                    editable: true
                });
        }
        const components = {
            body: {
                row: EditableFormRow,
                cell: EditableCell,
            },
        };
        const columns = colunasTabela.map(col => {
            if (!col.editable) {
                return col;
            }
            return {
                ...col,
                onCell: record => ({
                    record,
                    editable: col.editable,
                    dataIndex: col.dataIndex,
                    title: col.title,
                    handleSave: this.handleSave,
                }),
            };
        });

        console.log(columns);

        this.setState({ components, columns });
    }





    render() {
        const { getFieldDecorator } = this.props.form;

        const data = [];
        data.push({
            key: 1,
            linha: 'Linha'
        });

        return (
            < Form onSubmit={this.handleSubmit} >

                {this.descricao}
                <Divider style={{ fontSize: 25 }} >Cadastro de Layout</Divider>


                <FormItem style={{ width: '100%', left: '0px' }}
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 16 }
                    }
                    }
                    label="Descrição:">
                    {getFieldDecorator('descricao', {
                        initialValue: '1',
                        rules: [{
                            required: true,
                            message: 'Este campo é obrigatório!'
                        }]
                    })(<Input />)}
                </FormItem>


                <Divider style={{ fontSize: 22 }} >Identificador da empresa</Divider>
                <Row gutter={20}>
                    <Col span={8}>
                        <FormItem style={{ width: '100%', left: '0px' }}
                            {...
                            {
                                labelCol: { span: 12 },
                                wrapperCol: { span: 12 }
                            }
                            }
                            label="Identificador de Linha:">
                            {getFieldDecorator('identificadorLinha_empresa', {
                                initialValue: false,
                                valuePropName: 'checked',
                                rules: [{
                                    required: true,
                                    message: 'Este campo é obrigatório!'
                                }]
                            })(<Switch />)}
                        </FormItem>
                    </Col>

                    <Col span={6}>
                        <FormItem style={{ width: '100%', left: '0px' }}
                            {...
                            {
                                labelCol: { span: 8 },
                                wrapperCol: { span: 8 }
                            }
                            }
                            label="Delimitador:">
                            {getFieldDecorator('delimitador_empresa', {
                                initialValue: this.state.tipo_delimitador_empresa,
                                valuePropName: 'checked',
                                rules: [{
                                    required: true,
                                    message: 'Este campo é obrigatório!'
                                }]
                            })(<Select
                                value={this.state.tipo_delimitador_empresa}
                                onChange={this.tipoDelimitadorEmpresaChanged}
                                style={{ width: 120 }}
                            >
                                <Option value="1">Caractere</Option>
                                <Option value="2">Posições</Option>
                            </Select>)}
                        </FormItem>


                    </Col>

                    <Col span={6}>
                        <FormItem style={{ width: '100%', left: '0px' }}
                            {...
                            {
                                labelCol: { span: 8 },
                                wrapperCol: { span: 8 }
                            }
                            }
                            label="Cabeçalho:">
                            {getFieldDecorator('cabecalho_empresa', {
                                initialValue: false,
                                valuePropName: 'checked',
                                rules: [{
                                    required: true,
                                    message: 'Este campo é obrigatório!'
                                }]
                            })(<Switch />)}
                        </FormItem>
                    </Col>

                </Row>
                <Row>
                    <div hidden={this.state.tipo_delimitador_empresa != 2}>
                        <Col md={{ offset: 10 }}>
                            <FormItem
                                {...
                                {
                                    labelCol: { span: 6 },
                                    wrapperCol: { span: 4 }
                                }
                                }
                                label="Posição inicial:">
                                {getFieldDecorator('posicao_inicial', {
                                    initialValue: '',
                                    rules: [{
                                        required: true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Input />)}
                            </FormItem>

                        </Col>


                        <Col md={{ offset: 10 }}>
                            <FormItem
                                {...
                                {
                                    labelCol: { span: 6 },
                                    wrapperCol: { span: 4 }
                                }
                                }
                                label="Posição Final:">
                                {getFieldDecorator('posicao_final', {
                                    initialValue: '',
                                    rules: [{
                                        required: true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Input />)}
                            </FormItem>

                        </Col>

                    </div>
                    <div hidden={this.state.tipo_delimitador_empresa != 1}>
                        <Col md={{ offset: 10 }}>
                            <FormItem
                                {...
                                {
                                    labelCol: { span: 6 },
                                    wrapperCol: { span: 4 }
                                }
                                }
                                label="Caractere:">
                                {getFieldDecorator('caractere_empresa', {
                                    initialValue: '',
                                    rules: [{
                                        required: true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Input />)}
                            </FormItem>

                        </Col>
                    </div>
                </Row>
                <FormItem style={{ width: '100%', left: '0px' }}
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 5 }
                    }
                    }
                    label="Linha:">
                    <Row gutter={8}>
                        <Col span={12}>
                            {getFieldDecorator('linha_empresa', {
                                initialValue: ''
                            })(
                                <InputNumber />
                            )}
                        </Col>
                    </Row>
                </FormItem>



                {/* 
                    Informações do arquivo
                */}

                <Divider style={{ fontSize: 22 }} >Arquivo</Divider>

                <FormItem hidden={true} style={{ width: '40%', left: '0px' }}
                    {...formItemLayout} label="ID">
                    {getFieldDecorator('id', {})(<Input disabled={true} />)}
                </FormItem>

                <FormItem hidden={this.hiddenCopia} style={{ width: '100%', left: '0px' }}
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 16 }
                    }
                    }
                    label="Cópia:">
                    {getFieldDecorator('copia', {})(<Input disabled={true} />)}
                </FormItem>



                <Row gutter={20}>
                    <Col span={4}>
                        <FormItem style={{ width: '100%', left: '0px' }}
                            {...
                            {
                                labelCol: { span: 8 },
                                wrapperCol: { span: 8 }
                            }
                            }
                            label="Padrão:">
                            {getFieldDecorator('padrao', {
                                initialValue: false,
                                valuePropName: 'checked',
                                rules: [{
                                    required: true,
                                    message: 'Este campo é obrigatório!'
                                }]
                            })(<Switch />)}
                        </FormItem>
                    </Col>

                    <Col span={8}>
                        <FormItem style={{ width: '100%', left: '0px' }}
                            {...
                            {
                                labelCol: { span: 12 },
                                wrapperCol: { span: 12 }
                            }
                            }
                            label="Identificador de Linha:">
                            {getFieldDecorator('identificadorLinha', {
                                initialValue: false,
                                valuePropName: 'checked',
                                rules: [{
                                    required: true,
                                    message: 'Este campo é obrigatório!'
                                }]
                            })(<Switch />)}
                        </FormItem>
                    </Col>

                    <Col span={6}>
                        <FormItem style={{ width: '100%', left: '0px' }}
                            {...
                            {
                                labelCol: { span: 8 },
                                wrapperCol: { span: 8 }
                            }
                            }
                            label="Delimitador:">
                            {getFieldDecorator('delimitador', {
                                initialValue: this.state.tipo_delimitador_arquivo,
                                valuePropName: 'checked',
                                rules: [{
                                    required: true,
                                    message: 'Este campo é obrigatório!'
                                }]
                            })(<Select
                                value={this.state.tipo_delimitador_arquivo}
                                onChange={this.tipoDelimitadorInfosChanged}
                                style={{ width: 120 }}
                            >
                                <Option value="1">Caractere</Option>
                                <Option value="2">Posições</Option>
                            </Select>)}
                        </FormItem>


                    </Col>

                    <Col span={6}>
                        <FormItem style={{ width: '100%', left: '0px' }}
                            {...
                            {
                                labelCol: { span: 8 },
                                wrapperCol: { span: 8 }
                            }
                            }
                            label="Cabeçalho:">
                            {getFieldDecorator('cabecalho', {
                                initialValue: false,
                                valuePropName: 'checked',
                                rules: [{
                                    required: true,
                                    message: 'Este campo é obrigatório!'
                                }]
                            })(<Switch />)}
                        </FormItem>
                    </Col>

                </Row>
                <Row>
                    <Col md={{ offset: 10 }}>
                        <FormItem hidden={this.state.tipo_delimitador_arquivo != 1}
                            {...
                            {
                                labelCol: { span: 6 },
                                wrapperCol: { span: 4 }
                            }
                            }
                            label="Caractere:">
                            {getFieldDecorator('caractere', {
                                initialValue: '',
                                rules: [{
                                    required: true,
                                    message: 'Este campo é obrigatório!'
                                }]
                            })(<Input />)}
                        </FormItem>

                    </Col>

                </Row>
                <FormItem style={{ width: '100%', left: '0px' }}
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 5 }
                    }
                    }
                    label="Linha:">
                    <Row gutter={8}>
                        <Col span={12}>
                            {getFieldDecorator('linha', {
                                initialValue: ''
                            })(
                                <InputNumber />
                            )}
                        </Col>

                        <Col span={12}>
                            <Button onClick={this.adicionarLinha} icon="plus" type="primary" >
                                Add Linha
                                </Button>
                        </Col>
                    </Row>
                </FormItem>

                <Button onClick={this.onSubmit} icon="save" type="primary" size='small'
                    style={{
                        float: 'right',
                        width: 100, height: 35, marginBottom: 15, backgroundColor: "#00BF39", borderColor: "#00BF39"
                    }}>
                    Salvar
                </Button>

                <br></br>
                <br></br>
                <br></br>

                <div>
                    <Collapse accordion>

                        {Object.entries(this.state.linhas).map(([key, item]) => (

                            <Panel header={item.descricaoLinha} key={item.codLinha}
                                extra={<Icon type="delete" onClick={event => {
                                    this.deleteLinha(item.codLinha)
                                    event.stopPropagation();
                                }} />}
                            >
                                <div>
                                    <Row gutter={8} type="flex" justify="start">
                                        <Col span={6}>
                                            <Input name={item.codLinha} value={item.inputTabela} onChange={this.nomeTabelaChanged} />
                                        </Col>
                                        <Col span={18}>
                                            <Button icon="plus" type="primary"
                                                onClick={() => this.adicionarTabela(item.codLinha)}
                                            >
                                                Buscar Tabela
                                            </Button>                                            
                                        </Col>
                                    </Row>
                                </div>
                                <Row>
                                    {item.nomeTabelas.map(nome => (
                                        <Col key={item.tabelas[nome].key} span={12} style={{ padding: '5px' }}>
                                            <h1>
                                                <Divider style={{ fontSize: 15 }}>{item.tabelas[nome].nome}
                                                    <Icon type="delete" style={{ marginLeft: '10px' }} onClick={event => {
                                                        this.deleteTabela(item.codLinha, item.tabelas[nome].nome)
                                                        event.stopPropagation();
                                                    }} /></Divider>

                                                <Table
                                                    key={item.tabelas[nome].key}
                                                    components={this.state.components}
                                                    rowClassName={() => 'editable-row'}
                                                    bordered
                                                    dataSource={item.tabelas[nome].dados}
                                                    columns={this.state.columns}
                                                />
                                                {item.tabelas[nome].problema ?
                                                    <span style={{ color: '#F00' }}>Valor inserido é inválido.</span>
                                                    :
                                                    ''
                                                }
                                            </h1>

                                        </Col>
                                    ))}
                                </Row>

                            </Panel>
                        ))}

                    </Collapse>
                </div>

            </Form >
        );
    }
}


export default Form.create({ name: 'layoutArquivoCad' })(LayoutArquivoCad);