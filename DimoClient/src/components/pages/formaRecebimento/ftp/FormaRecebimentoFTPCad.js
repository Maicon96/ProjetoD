import React, { Component } from 'react';
import { Form, Input, Select, Divider } from 'antd';
import ToolbarForm from '../../../../containers/layout/Form/ToolbarForm';

const ajax = require('../../../../utils/ajax/ConexaoAjax');
const globalVariables = require('../../../../utils/variaveisGlobais');

const formItemLayout = {
    labelCol: { span: 10 },
    wrapperCol: { span: 14 }
};

const FormItem = Form.Item;

const { Option } = Select;

let paramsFilter = {};
let hiddenCliente = true;

class FormaRecebimentoFTPCad extends Component {

    constructor(props) {
        super(props);

        this.hiddenCliente = true;

        // responsavel por pegar o input que se deseja jogar o foco
        this.inputFocus = React.createRef();

        this.state = {
            confirmDirty: false,
            loading: false,
        };
    }

    componentDidMount() {
        if (this.props.location.params) {

            this.props.form.setFieldsValue(this.props.location.params.data.selectedRows[0]);

            paramsFilter = this.props.location.params.data.paramsFilter || '';

            if (paramsFilter.id !== undefined) {                
                this.hiddenCliente = false;

                this.props.form.setFieldsValue({
                    idCliente: paramsFilter.id
                });
            }
        }
    }

    findFormaRecebimentoFTP(idRegistro) {
        const me = this;

        const json = {
            id: idRegistro
        }

        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/forma/recebimento/ftp/exists',
            data: json,
            fnSetLoading: me.onSetLoading,
            afterMsgSuccessTrue: function (response) {
                me.props.form.setFieldsValue(response.data.registro);
                //me.onFocus();
            }
        });
    }

    onSubmit = e => {

        const me = this;
        e.preventDefault();

        this.props.form.validateFields((err, values) => {
            if (!err) {
                ajax.Call({
                    url: globalVariables.default.baseURLServer + '/service/forma/recebimento/ftp/save',
                    data: values,
                    fnSetLoading: me.onSetLoading,
                    afterMsgSuccessTrue: function (response) {
                        me.props.form.setFieldsValue(response.data.registro);
                        //me.onFocus();
                    }
                });
            }
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
                url: globalVariables.default.baseURLServer + '/service/forma/recebimento/ftp/delete',
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

    render() {
        const me = this;
        const { getFieldDecorator } = this.props.form;


        return (
            <Form onSubmit={this.handleSubmit}>

                <Divider style={{ fontSize: 25 }}>Forma Recebimento - FTP</Divider>

                <ToolbarForm
                    onClickBtnSalvar={this.onSubmit}
                    onClickBtnNovo={this.onClickBtnNovo}
                    onClickBtnDelete={this.onClickBtnDelete} />

                <FormItem hidden={true} style={{ width: '40%', left: '0px' }}
                    {...formItemLayout} label="ID">
                    {getFieldDecorator('id', {})(<Input disabled={true} />)}
                </FormItem>

                <FormItem hidden={this.hiddenCliente}  style={{ width: '40%', left: '0px' }}
                    {...formItemLayout} label="ID Cliente">
                    {getFieldDecorator('idCliente', {})(<Input disabled={true} />)}
                </FormItem>

                <FormItem 
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 8 }
                    }
                    }
                    label="Servidor"
                    hasFeedback
                >
                    {getFieldDecorator('servidor', {
                        rules: [{ required: true, message: 'Servidor é campo obrigatório!' }],
                    })(
                        <Input />
                    )}
                </FormItem>

                <FormItem style={{ width: '40%', left: '0px' }}
                    {...formItemLayout}
                    label="Usuário"
                    hasFeedback
                >
                    {getFieldDecorator('usuario', {})(
                        <Input />
                    )}
                </FormItem>


                <FormItem style={{ width: '40%', left: '0px' }}
                    {...formItemLayout}
                    label="Senha"
                    hasFeedback
                >
                    {getFieldDecorator('senha', {})(<Input.Password />)}
                </FormItem>

                <FormItem 
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 3 }
                    }
                    }
                    label="Porta"
                    hasFeedback
                >
                    {getFieldDecorator('porta', {
                        rules: [{ required: true, message: 'Porta é campo obrigatório!' }],
                    })(
                        <Input />
                    )}
                </FormItem>

                <Form.Item 
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 3 }
                    }
                    }
                    label="TLS/SSL">
                    {getFieldDecorator('tlsSsl', {
                    initialValue: '1',
                    rules: [{ required: true, message: 'Este campo é obrigatório!' }]
                    })(
                    <Select placeholder="Selecione">
                        <Option value="0">0 - Nenhum</Option>
                        <Option value="1">1 - TLS</Option>
                        <Option value="2">2 - SSL</Option>
                    </Select>
                    )}
                </Form.Item>
                
                <FormItem 
                    {...
                    {
                        labelCol: { span: 4 },
                        wrapperCol: { span: 12 }
                    }
                    }
                    label="Diretório"
                    hasFeedback
                >
                    {getFieldDecorator('diretorio', {
                        rules: [{ required: true, message: 'Diretório é campo obrigatório!' }],
                    })(
                        <Input />
                    )}
                </FormItem>

            </Form>
        );
    }
}

export default Form.create({ name: 'formaRecebimentoFTPCad' })(FormaRecebimentoFTPCad);