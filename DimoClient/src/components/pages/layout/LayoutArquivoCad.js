import React, { Component } from 'react';
import {
    Form, Select, Input, Switch, Divider, Tabs, Row, Col, Button,
    InputNumber, Icon, Table, Collapse, notification, Steps
} from 'antd';
import debounce from 'lodash/debounce';

const { TabPane } = Tabs;

const { Step } = Steps;

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
let identificadorLinha = '';
let tipoDelimitador = '';
let hiddenCopia = '';
let descricao = '';
let padrao = '';
let linhas = {};
let tabelas = [];
let item = [];


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


        this.hiddenCopia = true;

        if (this.props.location.params) {
            if (this.props.location.params.record) {
                this.record = props.location.params.record;
                this.copia = props.location.params.record.copia;
                this.descricao = props.location.params.record.descricao;
                this.padrao = props.location.params.record.padrao;
                this.identificadorLinha = props.location.params.record.identificadorLinha;
                this.tipoDelimitador = props.location.params.record.tipoDelimitador;
                console.log(this.record);
            }
        }

        this.fetchLayouts = debounce(this.fetchLayouts, 800);


        this.state = {
            loading: false,
            confirmDirty: false,
            data: [],
            value: [],
            fetching: false,
            linhas: [],
            nome_tabela: "",
            tabelas: [],
            tipo_delimitador: this.tipoDelimitador ? this.tipoDelimitador.toString() : '',
            components: null,
            columns: [],
            current: 0
        };
    }

    loadTableLayoutByList = (linhas, tableList) => {

        let linhaTableColumns = {};

        // console.log("Linhas");
        // console.log(linhas);
        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo//buscar/tabelas',
            fnSetLoading: this.onSetLoading,
            data: {
                tabelas: tableList.map(t => ({ nomeTabela: t }))
            },
            afterMsgSuccessTrue: (response) => {
                const tabelas = response.data.registro.tabelas;

                // console.log(tabelas);

                for (let tabela of tabelas) {
                    const nomeTabela = tabela.nomeTabela;
                    const campos = tabela.campos;
                    for (let linha of Object.keys(linhas)) {

                        linhaTableColumns[linha] = {};

                        if (linhas[linha].tabelas[nomeTabela]) {
                            linhaTableColumns[linha][nomeTabela] = linhas[linha].tabelas[nomeTabela].dados.map(c => c.Campo);

                            // console.log(`Colunas ${linha} - ${nomeTabela}`);

                            // console.log(linhaTableColumns[linha][nomeTabela])
                            for (let campo of campos) {
                                if (linhaTableColumns[linha][nomeTabela].indexOf(campo.nomeCampo) === -1) {
                                    // Insere a linha que não estiver na tabela salva
                                    linhas[linha].tabelas[nomeTabela].dados.push({
                                        Campo: campo.nomeCampo,
                                        tipoCampo: "String",
                                        posInicial: 0,
                                        posFinal: 0,
                                        codLinha: linha,
                                        nomeTabela: nomeTabela,
                                        indexador: 0,
                                        key: ++this.key,
                                        jsonDepara: "{}"
                                    });
                                    linhaTableColumns[linha][nomeTabela].push(campo.nomeCampo);
                                }
                            }
                        }
                    }
                }
            }
        });
        return linhas;
    }

    handleLoadTables = (data) => {
        let linhas = {};
        let tableList = [];

        if (data.linhas && data.linhas.length > 0) {
            for (let linha of data.linhas) {
                if (!linhas[linha.identificadorLinha]) {
                    linhas[linha.identificadorLinha] = {
                        id: linha.id,
                        codLinha: linha.identificadorLinha,
                        descricaoLinha: 'Linha ' + linha.identificadorLinha,
                        tabelas: {},
                        nomeTabelas: [],
                        inputTabela: ""
                    }
                }

                if (linha.layoutArquivoCampoTabelas.length > 0) {
                    for (let col of linha.layoutArquivoCampoTabelas) {
                        const nomeTabela = col.nomeTabela;
                        if (tableList.indexOf(nomeTabela) === -1) {
                            tableList.push(nomeTabela);
                        }
                        if (!linhas[linha.identificadorLinha].tabelas[nomeTabela]) {
                            linhas[linha.identificadorLinha].tabelas[nomeTabela] = {
                                dados: [],
                                nome: nomeTabela,
                                problema: false,
                                key: this.key++,
                            };
                        }

                        const row = {
                            id: col.id,
                            idLayoutArquivo: col.idLayoutArquivo,
                            idLayout: col.idLayout,
                            Campo: col.nomeCampo,
                            tipoCampo: "String",
                            posInicial: parseInt(col.posicaoInicial) || null,
                            posFinal: parseInt(col.posicaoFinal) || null,
                            codLinha: linha.identificadorLinha,
                            nomeTabela: nomeTabela,
                            indexador: col.indexador || null,
                            key: this.key++,
                            jsonDepara: col.jsonDepara
                        }
                        linhas[linha.identificadorLinha].tabelas[nomeTabela].dados.push(row);
                    }
                }

                linhas[linha.identificadorLinha].nomeTabelas = Object.keys(linhas[linha.identificadorLinha].tabelas);
            }
        }



        linhas = this.loadTableLayoutByList(linhas, tableList);
        var tabelas = [];
        for (let l of Object.keys(linhas)) {
            const linha = linhas[l];
            const tabs = linha.tabelas;
            for (let t of Object.keys(tabs)) {
                tabelas.push(tabs[t]);
            }
        }

        this.setState({ linhas, tabelas });
        this.getColumnsTabela(this.state.tipo_delimitador);
    }


    callback(key) {

        // console.log(key);

        //activeTab = key;

        //console.log(activeTab);
    }

    teste() {
        // console.log("entrou nessa merda");
    }

    validaTabelas = () => {

    }


    handleSave = row => {
        let linhas;
        let tabela;
        let tabelas;
        let i;

        if (this.state.tipo_delimitador == 2) {
            linhas = this.state.linhas;
            tabela = linhas[row.codLinha].tabelas[row.nomeTabela];
        } else {
            tabelas = this.state.tabelas;
            console.log(tabelas);
            i = tabelas.findIndex(t => t.nome == row.nomeTabela);
            tabela = tabelas[i];
        }

        const newData = [...tabela.dados];

        const index = newData.findIndex(item => row.key === item.key);

        const item = newData[index];

        row.posInicial = parseInt(row.posInicial);
        row.posFinal = parseInt(row.posFinal);

        newData.splice(index, 1, {
            ...item,
            ...row,
        });
        if (this.state.tipo_delimitador == 2) {
            linhas[row.codLinha].tabelas[row.nomeTabela].dados = newData;

            this.setState({ linhas });
        }
        else {
            tabela.dados = newData;
            tabelas[i] = tabela;
            console.log(tabelas);
            this.setState({ tabelas });
        }
    };

    componentDidMount() {
        if (this.record)
            this.handleLoadTables(this.record);

        this.getColumnsTabela(this.state.tipo_delimitador);

        const me = this;

        if (this.props.location.params) {

            // console.log(this.props.location.params);
            // console.log(this.props.location.params.record);

            if (this.props.location.params.record) {

                // this.props.form.setFieldsValue(this.props.location.params.record);

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

        let linhas = {};
        if (this.state.tipo_delimitador == 1) {
            let tabelas = {};
            linhas[0] = { id: null };
            this.state.tabelas.map(t => {
                tabelas[t.nome] = t;
                linhas[0].id = t.dados[0].idLayoutArquivo ? t.dados[0].idLayoutArquivo : linhas[0].id;
            })
            linhas[0].tabelas = tabelas;
        } else {
            linhas = this.state.linhas;
        }

        for (let l of Object.keys(linhas)) {
            let d = [];
            let tmp = {
                id: linhas[l].id,
                identificadorLinha: l,
                cadastroLayoutArquivoDTOs: []
            };
            for (let t of Object.keys(linhas[l].tabelas)) {
                for (let row of linhas[l].tabelas[t].dados) {


                    if (this.state.tipo_delimitador == 1) {

                        if (row.indexador !== 0 && !row.indexador) {
                            continue;
                        }
                    } else {
                        if (!row.posInicial && !row.posFinal) {
                            continue;
                        }
                    }

                    const posicao = this.state.tipo_delimitador == 1
                        ?
                        { indexador: row.indexador }
                        :
                        {
                            posicaoInicial: row.posInicial,
                            posicaoFinal: row.posFinal
                        };


                    const id = row.id ? {
                        id: row.id,
                        idLayoutArquivo: row.idLayoutArquivo,
                        idLayout: row.idLayout,
                    } : {};

                    d.push({
                        ...id,
                        caractere: form.caractere,
                        indexador: "",
                        nomeCampo: row.Campo,
                        nomeCampoTabela: row.Campo,
                        jsonDepara: row.jsonDepara,
                        nomeTabela: t,
                        ...posicao
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

            const tableWithError = this.checkTableValues();
            if (tableWithError) {
                notification.error({
                    message: 'Erro ao salvar layout!',
                    description: `As posições informadas na tabela ${tableWithError} estão incorretas.`,
                    duration: 7,
                });
                return;
            }

            const send_char = this.state.tipo_delimitador == 1;

            console.log(values.identificadorLinha_empresa);
            console.log(values.delimitador_empresa);

            const id = this.record && this.record.id ? { id: this.record.id } : {};
            const json = {
                layout: {
                    ...id,
                    nomeEmpresa: values.cpf_empresa,
                    identificadorLinha: values.identificadorLinha_empresa ? 1 : 0,
                    tipoDelimitador: this.state.tipo_delimitador,
                    posicaoInicial: !send_char ? values.posicao_inicial : null,
                    posicaoFinal: !send_char ? values.posicao_final : null,
                    delimitador: send_char ? values.caractere : null,
                    indexador: send_char ? values.indexador_empresa : null,
                    descricao: values.descricao,
                    cabecalho: values.cabecalho,
                    padrao: values.padrao ? 1 : 0,
                },
                cadastroLayoutArquivoDTOs: this.getLayouts(values)
            };

            console.log("Json enviado")
            console.log(json);
            console.log("--------------")


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

        // console.log("entrou no handlechange");

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
        let t = e.target.value;
        if (this.state.tipo_delimitador == 2) {
            linhas[e.target.name].inputTabela = t;
        }
        this.setState({ linhas, nome_tabela: t });
    }

    adicionarLinha = () => {
        this.props.form.validateFields((err, values) => {
            if (values.linha > 0) {

                let linhas = this.state.linhas;
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

        if (this.state.tipo_delimitador == 2) {
            let linhas = this.state.linhas;
            let tabelas = linhas[codLinha].tabelas;
            let index = linhas[codLinha].nomeTabelas.indexOf(nomeTabela);
            delete linhas[codLinha].nomeTabelas[index];
            delete tabelas[nomeTabela];
            linhas[codLinha].tabelas = tabelas;
            this.setState({ linhas });
        } else {
            let tabelas = this.state.tabelas;
            let index = tabelas.findIndex(t => t.nome === nomeTabela);
            delete tabelas[index];
            this.setState({ tabelas });
        }
    }

    deleteLinha = (codLinha) => {
        let linhas = this.state.linhas;
        delete linhas[codLinha];
        this.setState({ linhas });
    }

    adicionarTabela = (codLinha) => {

        const me = this;

        let linhas;
        let tabelas = this.state.tabelas;
        let linha;

        this.getColumnsTabela(this.state.tipo_delimitador);

        if (this.state.tipo_delimitador == 2) {
            linhas = this.state.linhas;
            linha = linhas[codLinha];

            if (Object.keys(linha.tabelas).indexOf(linha.inputTabela) !== -1) {
                notification.error({
                    message: 'Erro ao adicionar tabela!',
                    description: 'Esta tabela já foi adicionada a esta linha.',
                    duration: 3,
                });
                return;
            }
        }

        const nomeTabela = linha ? linha.inputTabela : this.state.nome_tabela;
        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo//buscar/tabela',
            fnSetLoading: me.onSetLoading,
            data: {
                nomeTabela: nomeTabela
            },
            afterMsgSuccessTrue: (response) => {
                const data = response.data.registro.campos.map((value, index) => ({
                    Campo: value.nomeCampo,
                    tipoCampo: "String",
                    posInicial: 0,
                    posFinal: 0,
                    codLinha: codLinha,
                    nomeTabela: nomeTabela,
                    indexador: index,
                    key: index,
                    jsonDepara: "{}"
                }));

                const t = {
                    dados: data,
                    nome: nomeTabela,
                    problema: false,
                    key: this.key++,
                };

                tabelas.push(t);
                this.setState({ tabelas });

                if (linha) {
                    linha.tabelas[linha.inputTabela] = t;


                    linha.nomeTabelas.push(linha.inputTabela);
                    linha.inputTabela = "";
                    linhas[codLinha] = linha;
                    me.setState({ linhas: linhas });
                }

            }
        });

    }

    checkTableValues = () => {
        const linhas = this.state.linhas;
        for (let codigo of Object.keys(linhas)) {
            const linha = linhas[codigo];
            for (let nomeTabela of Object.keys(linha.tabelas)) {
                const dados = linha.tabelas[nomeTabela].dados;
                for (let i = 0; i < dados.length; i++) {
                    if (this.state.tipo_delimitador == 2) {
                        const item = dados[i];
                        try {
                            const json = JSON.parse(item.jsonDepara);
                        } catch (e) {
                            // console.log("Error json: " + e);
                            // return nomeTabela;
                        }
                        var ok = true;
                        if (item.posInicial && item.posFinal) {
                            ok &= item.posInicial < item.posFinal
                        }
                        // if (i > 0) {
                        //     ok &= item.posInicial > dados[i - 1].posFinal;
                        // }
                        if (!ok) return nomeTabela;
                    }
                }
            }
        }
        return false;
    }

    tipoDelimitadorChanged = (value) => {
        this.setState({ tipo_delimitador: value });
        this.getColumnsTabela(value);
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

        if (tipo == 1) {
            colunasTabela.push(
                {
                    title: 'Posição',
                    dataIndex: 'indexador',
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


        colunasTabela.push(
            {
                title: 'De Para',
                dataIndex: 'jsonDepara',
                editable: true
            });
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

        this.setState({ components, columns });
    }

    onStepChange = current => this.setState({ current });





    render() {
        const { getFieldDecorator } = this.props.form;

        const data = [];
        data.push({
            key: 1,
            linha: 'Linha'
        });

        return (
            < Form style={{ margin: '16px' }} onSubmit={this.handleSubmit} >

                <Steps current={this.state.current} onChange={this.onStepChange} style={{ marginBottom: '20px' }}>
                    <Step title="Cadastro do layout" />
                    <Step title="Identificação da Empresa" />
                    <Step title="Arquivo" />
                </Steps>

                <div hidden={this.state.current != 0} >
                    <Row>
                        <Col span={12}>
                            <FormItem style={{ width: '100%', left: '0px' }}
                                {...
                                {
                                    labelCol: { span: 6 },
                                    wrapperCol: { span: 6 }
                                }
                                }
                                label="Descrição:">
                                {getFieldDecorator('descricao', {
                                    initialValue: this.record ? this.record.descricao : '',
                                    rules: [{
                                        required: true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Input />)}
                            </FormItem>
                        </Col>
                        <Col span={12}>
                            <FormItem style={{ width: '100%', left: '0px' }}
                                {...
                                {
                                    labelCol: { span: 6 },
                                    wrapperCol: { span: 6 }
                                }
                                }
                                label="Padrão:">
                                {getFieldDecorator('padrao', {
                                    initialValue: this.record ? this.record.padrao == 1 : false,
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
                                {getFieldDecorator('identificadorLinha_empresa', {
                                    initialValue: this.record ? this.record.identificadorLinha == 1 : false,
                                    valuePropName: 'checked',
                                    rules: [{
                                        required: true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Switch />)}
                            </FormItem>
                        </Col>

                        <Col span={10}>
                            <FormItem style={{ width: '100%', left: '0px' }}
                                {...
                                {
                                    labelCol: { span: 8 },
                                    wrapperCol: { span: 8 }
                                }
                                }
                                label="Tipo Delimitador:">
                                {getFieldDecorator('delimitador_empresa', {
                                    initialValue: this.state.tipo_delimitador,
                                    valuePropName: 'checked',
                                    rules: [{
                                        required: true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Select
                                    value={this.state.tipo_delimitador}
                                    onChange={this.tipoDelimitadorChanged}
                                    style={{ width: 120 }}
                                >
                                    <Option value="1">Caractere</Option>
                                    <Option value="2">Posições</Option>
                                </Select>)}
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>

                        <Col hidden={this.state.tipo_delimitador != 1}>
                            <FormItem style={{ width: '100%', left: '0px' }}
                                {...
                                {
                                    labelCol: { span: 4 },
                                    wrapperCol: { span: 4 }
                                }
                                }
                                label="Delimitador:">
                                {getFieldDecorator('caractere', {
                                    initialValue: this.record ? this.record.delimitador : '',
                                    rules: [{
                                        required: this.state.tipo_delimitador != 1 ? false : true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Input />)}
                            </FormItem>
                        </Col>

                        <Col hidden={this.state.tipo_delimitador != 1}>
                            <FormItem style={{ width: '100%', left: '0px' }}
                                {...
                                {
                                    labelCol: { span: 4 },
                                    wrapperCol: { span: 4 }
                                }
                                }
                                label="Cabeçalho:">
                                {getFieldDecorator('cabecalho', {
                                    initialValue: this.record && this.record.cabecalho ? this.record.cabecalho == true : false,
                                    valuePropName: 'checked',
                                    rules: [{
                                        required: this.state.tipo_delimitador != 1 ? false : true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Switch />)}
                            </FormItem>
                        </Col>

                    </Row>

                </div>

                <div hidden={this.state.current != 1}>
                    <Row gutter={20}>

                        <Col span={16}>
                            <FormItem style={{ width: '100%', left: '0px' }}
                                {...
                                {
                                    labelCol: { span: 12 },
                                    wrapperCol: { span: 12 }
                                }
                                }
                                label="CPF da Empresa:">
                                {getFieldDecorator('cpf_empresa', {
                                    initialValue: this.record ? this.record.nomeEmpresa : '',
                                    rules: [{
                                        required: true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Input />)}
                            </FormItem>
                        </Col>

                        {/* 
                        <Col span={6}>

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
                                            initialValue: this.record ? this.record.linha : ''
                                        })(
                                            <InputNumber />
                                        )}
                                    </Col>
                                </Row>
                            </FormItem>
                        </Col> */}

                    </Row>
                    {/* <Row>
                        <Col offset={1} span={2} hidden={this.state.tipo_delimitador != 2}>
                            <FormItem
                                label="Posição Inicial:">
                                {getFieldDecorator('posicao_inicial', {
                                    initialValue: this.record ? this.record.posicaoInicial : '',
                                    rules: [{
                                        required: this.state.tipo_delimitador == 2,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<InputNumber />)}
                            </FormItem>

                        </Col>


                        <Col offset={1} span={2} hidden={this.state.tipo_delimitador != 2}>
                            <FormItem
                                label="Posição Final:">
                                {getFieldDecorator('posicao_final', {
                                    initialValue: this.record ? this.record.posicaoFinal : '',
                                    rules: [{
                                        required: this.state.tipo_delimitador == 2,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<InputNumber />)}
                            </FormItem>

                        </Col>

                        <Col offset={1} span={2} hidden={this.state.tipo_delimitador != 1}>
                            <FormItem
                                label="Delimitador:">
                                {getFieldDecorator('caractere_empresa', {
                                    initialValue: this.record ? this.record.delimitador : '',
                                    rules: [{
                                        required: this.state.tipo_delimitador != 1 ? false : true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Input />)}
                            </FormItem>

                        </Col>
                        <Col offset={1} span={2} hidden={this.state.tipo_delimitador != 1}>
                            <FormItem
                                label="Indexador:">
                                {getFieldDecorator('indexador_empresa', {
                                    initialValue: this.record ? this.record.indexador : '',
                                    rules: [{
                                        required: this.state.tipo_delimitador != 1 ? false : true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Input />)}
                            </FormItem>

                        </Col>
                    </Row> */}
                </div>

                <div hidden={this.state.current != 2}>

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

                        {/*<Col span={8}>
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
                            </Col>*/}

                        {/*        
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
                                    initialValue: this.record && this.record.cabecalho ? this.record.cabecalho == true : false,
                                    valuePropName: 'checked',
                                    rules: [{
                                        required: true,
                                        message: 'Este campo é obrigatório!'
                                    }]
                                })(<Switch />)}
                            </FormItem>
                        </Col>
                        */}

                    </Row>

                    {this.state.tipo_delimitador == 2
                        ?

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


                        :


                        <FormItem style={{ width: '100%', left: '0px' }}
                            {...
                            {
                                labelCol: { span: 4 },
                                wrapperCol: { span: 5 }
                            }
                            }
                            label="Tabela:">
                            <Row gutter={8}>
                                <Col span={12}>
                                    {getFieldDecorator('linha', {
                                        initialValue: ''
                                    })(
                                        <Input onChange={this.nomeTabelaChanged} />
                                    )}
                                </Col>

                                <Col span={12}>
                                    <Button onClick={this.adicionarTabela} icon="plus" type="primary" >
                                        Add Tabela
                                    </Button>
                                </Col>
                            </Row>
                        </FormItem>
                    }


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


                            {

                                this.state.tipo_delimitador == 2
                                    ?

                                    Object.entries(this.state.linhas).map(([key, item]) => (

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
                                                            Nova Tabela
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
                                    ))

                                    :
                                    < Row >

                                        {this.state.tabelas.map(tabela => (
                                            <Col key={tabela.key} span={12} style={{ padding: '5px' }}>
                                                <h1>
                                                    <Divider style={{ fontSize: 15 }}>{tabela.nome}
                                                        <Icon type="delete" style={{ marginLeft: '10px' }} onClick={event => {
                                                            this.deleteTabela(item.codLinha, tabela.nome)
                                                            event.stopPropagation();
                                                        }} /></Divider>

                                                    <Table
                                                        key={tabela.key}
                                                        components={this.state.components}
                                                        rowClassName={() => 'editable-row'}
                                                        bordered
                                                        dataSource={tabela.dados}
                                                        columns={this.state.columns}
                                                    />
                                                    {tabela.problema ?
                                                        <span style={{ color: '#F00' }}>Valor inserido é inválido.</span>
                                                        :
                                                        ''
                                                    }
                                                </h1>

                                            </Col>
                                        ))
                                        }
                                    </Row>

                            }

                        </Collapse>
                    </div>
                </div>

            </Form >
        );
    }
}


export default Form.create({ name: 'layoutArquivoCad' })(LayoutArquivoCad);