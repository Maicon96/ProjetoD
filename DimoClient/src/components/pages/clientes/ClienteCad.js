import React, { Component } from 'react';
import { Form, Select, Input, Divider, Tabs, Col, Row, DatePicker } from 'antd';
import ToolbarForm from '../../../containers/layout/Form/ToolbarForm';
import FormaRecebimentoPastaCon from '../formaRecebimento/pasta/FormaRecebimentoPastaCon';
import FormaRecebimentoEmailCon from '../formaRecebimento/email/FormaRecebimentoEmailCon';
import FormaRecebimentoFTPCon from '../formaRecebimento/ftp/FormaRecebimentoFTPCon';
import moment from 'moment';
import debounce from 'lodash/debounce';

const { TabPane } = Tabs;

const ajax = require('../../../utils/ajax/ConexaoAjax');
const globalVariables = require('../../../utils/variaveisGlobais');
const metodos = require('../../../utils/Metodos');

const { Option } = Select;

const formItemLayout = {
    labelCol: { span: 10 },
    wrapperCol: { span: 12 }
};

const FormItem = Form.Item;

let cliente = '';
let cpfcnpj = '';
let dataInicio = '';
let dataFim = '';
let activeTab = '';
/*let tabPasta = "";
let tabEmail = "";
let tabFTP = "";*/


class ClienteCad extends Component {

    constructor(props) {
        super(props);

        //responsavel por pegar o input que se deseja jogar o foco
        this.inputFocus = React.createRef();

        this.state = {
            loading: false,
            confirmDirty: false,
            loading: false,
            data: [],
            value: [],
            fetching: false,
            tabPasta: '',
            tabEmail: '',
            tabFTP: ''
        };
        
        if (this.props.location.params) {
            if (this.props.location.params.data.selectedRows[0]) {
                this.cliente = props.location.params.data.selectedRows[0].idCliente;
                this.dataInicio = props.location.params.data.selectedRows[0].dataInicio;
                this.dataFim = props.location.params.data.selectedRows[0].dataFim;
            }
        }

        this.fetchLayouts = debounce(this.fetchLayouts, 800);
    }

    callback(key) {
        activeTab = key;
    }

    componentDidMount() {
        const me = this;
        
        //this.props.form.setFieldsValue(this.props.location.params.data.selectedRows[0]);

        if (this.props.location.params) {

            this.props.form.setFieldsValue(this.props.location.params.data.selectedRows[0]);

            if (this.props.location.params.data.selectedRows[0]) {
                const layoutDescricao = this.props.location.params.data.selectedRows[0].idLayout + " - " +
                    this.props.location.params.data.selectedRows[0].layout.descricao;

                this.props.form.setFieldsValue({ idLayout: layoutDescricao });

                this.setVisibleTabs(true, this.cliente);
            } else {
                this.setVisibleTabs(false, "");
            }
        } else {
            this.setVisibleTabs(false, "");
        }
    }


    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }

    onSubmit = e => {
        const me = this;
        e.preventDefault();

        this.props.form.validateFields((err, values) => {
            var numb = values.idLayout.match(/\d/g);
            
            const json = {
                idCliente: values.idCliente,
                cliente: {
                    id: values.idCliente,
                    cpfcnpj: values.cliente.cpfcnpj
                },
                idLayout: numb[0],
                dataInicio: values.dataInicio,
                dataFim: values.dataFim
            };

            if (!err) {
                ajax.Call({
                    url: globalVariables.default.baseURLServer + '/service/layout/arquivo/clientes/save',
                    data: json,
                    fnSetLoading: me.onSetLoading,
                    afterMsgSuccessTrue: function (response) {
                        //me.props.form.setFieldsValue(response.data.registro);
                        //me.onFocus();

                        me.setVisibleTabs(true, response.data.registro.idCliente);
                    }
                });
            }
        });
    };

    onClickBtnNovo = () => {
        const form = this.props.form;
        form.resetFields();
        this.setVisibleTabs(false, "");

        //this.onFocus();
    }

    onClickBtnDelete = () => {

        const me = this,
            form = this.props.form,
            idCliente = form.getFieldValue('idCliente'),
            numbLayout = form.getFieldValue('idLayout').match(/\d/g);
                        
        if (idCliente && numbLayout) {
            ajax.Call({
                url: globalVariables.default.baseURLServer + '/service/layout/arquivo/clientes/delete',
                data: [{
                    idCliente: idCliente,
                    idLayout: numbLayout[0]
                }],
                afterMsgSuccessTrue: function () {
                    me.onClickBtnNovo();
                }
            });
        }
    }

    setVisibleTabs(visible, cliente) {

        console.log("setVisibleTabs");
        console.log(visible);
        console.log(cliente);

        if (visible) {
            console.log("ativa");
            this.setState({ 
                tabPasta: <FormaRecebimentoPastaCon tabAtiva={activeTab} cliente={cliente} showTitle={false} />, 
                tabEmail: <FormaRecebimentoEmailCon tabAtiva={activeTab} cliente={cliente} showTitle={false} />,
                tabFTP: <FormaRecebimentoFTPCon tabAtiva={activeTab} cliente={cliente} showTitle={false} />    
            });

            /*tabPasta = <FormaRecebimentoPastaCon tabAtiva={activeTab} cliente={cliente} showTitle={false} />;
            tabEmail = <FormaRecebimentoEmailCon tabAtiva={activeTab} cliente={cliente} showTitle={false} />;
            tabFTP = <FormaRecebimentoFTPCon tabAtiva={activeTab} cliente={cliente} showTitle={false} />;*/
        } else {
            this.setState({ 
                tabPasta: "", 
                tabEmail: "",
                tabFTP: ""    
            });
            /*tabPasta = "";
            tabEmail = "";
            tabFTP = "";*/
        }

        console.log(this.state.tabPasta);
        console.log(this.state.tabEmail);
        console.log(this.state.tabFTP);
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

        console.log("fetchLayouts");
        console.log(value);

        this.buscarLayouts(value);
    };

    onFocusLayout = (e) => {
        
        console.log("onFocusLayout");

        this.buscarLayouts("");
    }

    buscarLayouts(descricao) {
        const me = this;

        console.log("buscarLayouts");
        console.log(descricao);

        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo/load/filter',
            fnSetLoading: me.onSetLoading,
            data:
            {
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

    render() {
        const me = this;
        const { getFieldDecorator } = this.props.form;
        const { fetching, data, value } = this.state;

        /*console.log(tabPasta);

        tabPasta = <FormaRecebimentoPastaCon tabAtiva={activeTab} cliente={cliente} showTitle={false} />;

        console.log(tabPasta);*/

        return (
            <Form onSubmit={this.handleSubmit}>

                <Divider style={{ fontSize: 25 }}>Clientes</Divider>

                <ToolbarForm
                    onClickBtnSalvar={this.onSubmit}
                    onClickBtnNovo={this.onClickBtnNovo}
                    onClickBtnDelete={this.onClickBtnDelete} />

                <FormItem hidden={true} style={{ width: '40%', left: '0px' }}
                    {...formItemLayout} label="ID">
                    {getFieldDecorator('idCliente', {})(<Input disabled={true} />)}
                </FormItem>

                <FormItem
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 4 }
                    }
                    }
                    label="CPF/CNPJ">
                    {getFieldDecorator('cliente.cpfcnpj', {
                        initialValue: '',
                        rules: [{
                            required: true,
                            message: 'Este campo é obrigatório!'
                        }]
                    })(<Input maxLength={18}
                        onChange={function (e) {

                            e.target.value = e.target.value.replace(/[^a-zA-Z0-9]/g, "");

                            if (e.target.value.length == 11) {                                
                                e.target.value = e.target.value.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/g,"\$1.\$2.\$3\-\$4");
                            } else if (e.target.value.length == 14) {                                                                
                                e.target.value = e.target.value.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g, "\$1.\$2.\$3\/\$4\-\$5");                                
                            }
                        }}

                    />)}
                </FormItem>

                <FormItem style={{ width: '100%', left: '0px' }}
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 16 }
                    }
                    }
                    label="Nome"
                    hasFeedback
                >
                    {getFieldDecorator('cliente.nome', {
                        initialValue: ''
                    })(
                        <Input disabled={true} />
                    )}
                </FormItem>
                
                <FormItem
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 8 }
                    }
                    }
                    label="Layout"
                    hasFeedback
                >
                    {getFieldDecorator('idLayout', {
                        initialValue: '',
                        //rules: [{ required: true, message: 'Informe um Layout válido!' }],
                    })(
                        <Select
                            showSearch
                            //value={this.state.value}
                            placeholder={this.props.placeholder}
                            style={this.props.style}
                            defaultActiveFirstOption={false}
                            showArrow={false}
                            filterOption={false}
                            onSearch={this.fetchLayouts}
                            onFocus={this.onFocusLayout}
                        >
                            {data.map(d => (
                                <Option key={d.value}>{d.text}</Option>
                            ))}
                        </Select>
                    )}
                </FormItem>
                
                
                <Row gutter={20}>

                    <Col span={3}>
                        <FormItem
                            label="Data Início"
                        >
                            {
                                getFieldDecorator('dataInicio', {
                                    initialValue: moment('0000-00-00', "YYYY-MM-DD"),                                    
                                    rules: [{ 
                                        required: true, 
                                        type: 'object',
                                        message: 'Informe uma data válida!' 
                                    }],
                                    normalize: (value) => {                                
                                        if (value !== undefined) {
                                            return moment(value, "YYYY-MM-DD");
                                        }
                                    }
                                })(
                                    <DatePicker   
                                        placeholder=""
                                        //defaultValue={moment(this.dataInicio, 'YYYY-MM-DD')}
                                        format='DD/MM/YYYY'                           
                                    />
                                )
                            }

                        </FormItem>
                    </Col>,

                    <Col span={3} >
                        <FormItem
                            label="Data Fim"
                        >
                            {
                                getFieldDecorator('dataFim', {
                                    initialValue: moment('0000-00-00', "YYYY-MM-DD"),                         
                                    normalize: (value) => {                                
                                        if (value !== undefined) {
                                            return moment(value, "YYYY-MM-DD");
                                        }
                                    }
                                })(
                                    <DatePicker   
                                        placeholder=""                                        
                                        //defaultValue={moment(this.dataFim, 'YYYY-MM-DD')}
                                        format='DD/MM/YYYY'            
                                    />
                                )
                            }
                           
                        </FormItem>
                    </Col>
                </Row>

                <Divider>Formas de Recebimentos</Divider>

                <Tabs defaultActiveKey="1" onChange={this.callback}>
                    <TabPane forceRender={false} tab="Pasta" key="1">                        
                        {this.state.tabPasta}               
                    </TabPane>
                    <TabPane forceRender={false} tab="E-mail" key="2">                                                
                        {this.state.tabEmail} 
                    </TabPane>
                    <TabPane forceRender={false} tab="FTP" key="3">                        
                        {this.state.tabFTP} 
                    </TabPane>
                </Tabs>
                
            </Form>
        );
    }
}


export default Form.create({ name: 'clienteCad' })(ClienteCad);