import React, { Component } from 'react';
import { Table, Divider, Button, Icon, Tooltip, notification, Row, Col } from 'antd';

import { Link } from 'react-router-dom';

import LayoutArquivoCad from './LayoutArquivoCad';

const ajax = require('../../../utils/ajax/ConexaoAjax');
const globalVariables = require('../../../utils/variaveisGlobais');

let record = {};

export default class LayoutArquivoCon extends Component {
    constructor(props) {
        super(props);

        this.myRefTable = React.createRef();

        this.state = {
            loading: false,
            data: [],
            dataEdit: {},
            dataDelete: [],
            selectedRows: [],
            record: {},
            visible: false
        };

    }

    componentDidMount() {
        this.onClickBtnRefresh();
    }

    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }

    onClickBtnEdit = (e) => {
        if (this.state.selectedRows.length > 1 || this.state.selectedRows.length === 0) {
            notification.warning({
                message: 'Aviso!',
                description: 'Selecione um registro para editar.',
                duration: 5
            });
            return;
        } else {
            record = this.state.selectedRows[0];
            this.onShowForm(this.state.selectedRows[0]);
        }
    }

    onClickBtnDelete = (e) => {
        const me = this,
            registros = this.state.selectedRows;

        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo/delete',
            data: registros,
            fnSetLoading: me.onSetLoading,
            afterMsgSuccessTrue: function (response) {
                me.onClickBtnRefresh("");
            }
        });
    }

    onClickBtnRefresh = () => {
        const me = this;

        ajax.Call({
            url: globalVariables.default.baseURLServer + '/service/layout/arquivo/load',
            fnSetLoading: me.onSetLoading,
            data: "",
            afterMsgSuccessTrue: function (response) {
                me.setState({ data: response.data.registros });
            }
        });
    }

    onClickBtnCopia = (record) => {
        const me = this;

        record = record;

        //form.resetFields();

        //this.setVisibleTabs(false, "");
        //this.onFocus();
    }

    onShowForm = (dataEdit) => {
        //const me = this;

        this.setState({
            dataEdit: dataEdit,
            visible: true
        });
    };

    onCloseForm = () => {
        this.setState({ visible: false });
        this.onClickBtnRefresh();
    };

    onClickRow = (record, rowIndex, event) => {

        //this.myRefTable.current.handleRadioSelect(record, rowIndex, event);        
    };

    render() {

        const columns = [{
            title: 'ID',
            width: 70,
            id: "id",
            align: 'center',
            dataIndex: 'id',
            key: 'id',
            hidden: true
        }, {
            title: 'Descrição',
            width: 500,
            id: "descricao",
            dataIndex: 'descricao'
        }, {
            title: 'Padrão',
            width: 100,
            id: "padrao",
            dataIndex: 'padrao',
            render: (text, record, index) => {
                if (text === 1) {
                    return '1 - Sim';
                } else if (text === 0) {
                    return '0 - Não';
                }
            }
        }, {
            id: "copia",
            title: 'Cópia',
            align: 'center',
            width: 50,
            render: (text, record, index) => {
                return (<div>
                    <Tooltip title='Criar Cópia do Layout'>
                        <Button shape="circle" type='primary' size='small' onClick={() => this.onClickBtnCopia(record)}
                            value={record}
                            style={{ width: 35, height: 35, backgroundColor: "#00BF39", borderColor: "#00BF39" }}>
                            <Link to={{ pathname: '/cadastro/layout/arquivo', params: { data: this.state, record: record } }} >
                                <Icon type="more" />
                            </Link>
                        </Button>
                    </Tooltip>
                </div>);
            }
        }];

        return (
            <div>
                <Divider style={{ fontSize: 25 }}>Layouts de Arquivos</Divider>

                <div>
                    <Row type="flex" justify="space-around" align="middle">
                        <Col span={12}>

                            <Tooltip title='Editar'>
                                <Button type='primary' size='small' onClick={this.onClickBtnEdit}
                                    style={{ marginLeft: 5 }}>
                                    <Link to={{ pathname: '/cadastro/layout/arquivo', params: { data: this.state, record: this.state.selectedRows[0] } }} >
                                        <Icon type="edit" />
                                    </Link>
                                </Button>
                            </Tooltip>

                            <Tooltip title="Excluir">
                                <Button type="danger" size='small' onClick={this.onClickBtnDelete}
                                    style={{ marginLeft: 5, backgroundColor: "red", borderColor: "#FFFFFF" }}>
                                    <Icon type="delete" />
                                </Button>
                            </Tooltip>

                            <Tooltip title="Atualizar">
                                <Button type="primary" size='small' onClick={this.onClickBtnRefresh}
                                    style={{
                                        justifyContent: 'center',
                                        alignItems: 'center', marginLeft: 5, backgroundColor: "black", borderColor: "black"
                                    }}>
                                    <Icon type="sync" style={{ color: 'white' }} />
                                </Button>
                            </Tooltip>
                        </Col>

                        <Col span={12}>
                            <Tooltip title="Criar novo Layout">
                                <Link to={{ pathname: '/cadastro/layout/arquivo' }} >
                                    <Button type="primary" size='small'
                                        style={{
                                            float: 'right',
                                            width: 200, height: 50, marginBottom: 15, backgroundColor: "#00BF39", borderColor: "#00BF39"
                                        }}>
                                        <Icon type="plus" />
                                        Criar novo Layout
                                    </Button>
                                </Link>
                            </Tooltip>
                        </Col>
                    </Row>
                </div>

                <Table
                    ref={this.myRefTable}
                    rowKey="id"
                    bordered showHeader={true}

                    //eventos de click e duplo click na linha 
                    onRow={(record, rowIndex) => {
                        return {
                            onClick: event => {
                                this.onClickRow(record, rowIndex, event);
                            },
                            onDoubleClick: event => {
                                this.onClickRow(record, rowIndex, event);
                            }
                        };
                    }}

                    //caixa de seleçao nos itens
                    rowSelection={{
                        onChange: (selectedRowKeys, selectedRows) => {

                            this.setState({ selectedRows: selectedRows });
                        },
                        onSelect: (record, selected, selectedRows) => {

                            this.setState({ selectedRows: selectedRows });
                        }
                    }
                    }

                    columns={columns}
                    dataSource={this.state.data}
                    loading={this.state.loading}
                />
            </div>
        );

    }

}