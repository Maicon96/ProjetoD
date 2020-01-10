import React from 'react';
import { Table, Divider, Input, Button, Icon, notification, Form } from 'antd';
import { Link } from 'react-router-dom';

//import DrawerGrid from './DrawerGrid';
import ToolbarGrid from './ToolbarGrid';

const ajax = require('../../../utils/ajax/ConexaoAjax');
const globalVariables = require('../../../utils/variaveisGlobais');

let paramsFilter = {};
let urlLoad = '';
let urlDelete = '';
let urlChamaCadastro = '';
let showTitle;

class Grid extends React.Component {

    constructor(props) {
        super(props);

        paramsFilter = props.paramsFilter || '';
        this.urlLoad = props.urlLoad || '';
        this.urlDelete = props.urlDelete || '';
        urlChamaCadastro = props.urlChamaCadastro || '';    
        showTitle = props.showTitle;

        this.myRefTable = React.createRef();

        this.state = {
            loading: false,
            data: [],
            dataEdit: {},
            selectedRows: [],
            visible: false,
            visibleOutros: false,
            urlChamaCadastro: urlChamaCadastro,
            paramsFilter: paramsFilter
        };
    }

    componentDidMount() {
        this.onClickBtnRefresh();
    }

    onSetLoading = (cond) => {
        this.setState({ loading: cond });
    }

    onClickBtnAdd = () => {        

        this.setState({
            dataEdit: {}
        });

        this.onShowForm();
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
            this.onShowForm();
        }       
    }

    onClickBtnDelete = (e) => {
        const me = this,
        registros = this.state.selectedRows;

        ajax.Call({
            url: globalVariables.default.baseURLServer + this.urlDelete,
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
            url: globalVariables.default.baseURLServer + this.urlLoad,
            fnSetLoading: me.onSetLoading,
            data: paramsFilter,
            afterMsgSuccessTrue: function (response) {
                me.setState({ data: response.data.registros });
            }
        });
    }

    onShowForm = (dataEdit) => {
        this.setState({
            dataEdit: dataEdit,           
            visible: true
        });
    };

    onCloseForm = () => {
        this.setState({ visible: false });
        this.onClickBtnRefresh();
    };

    /*onCloseDrawerForm = () => {
        this.setState({ visible: false });
        this.onClickBtnRefresh();
    };*/

    onClickRow = (record, rowIndex, event) => {

        //console.log("row");
        //console.log(this.myRefTable);
        //console.log(this.myRefTable.current);

        //this.myRefTable.current.handleRadioSelect(record, rowIndex, event);
    };
    
    render() {
        const props = this.props,
            columns = props.columns ? props.columns : [],
            title = props.title ? props.title : '',
            register = props.register ? props.register : {},
            registerTitle = register.title ? register.title : '',
            registerScream = register.scream;


        // popula a propriedade data com o estado do dataEdit  
        //Object.assign(registerScream.props.data, this.state.dataEdit);

        //descomentar isso

        //console.log(this.state.dataEdit);

        // pegamos as colunas e juntamos com as propriedades do getColumnSearchProps
        //columns.forEach(element => {
        //  Object.assign(element, this.getColumnSearchProps(element.id));
        //});

        return (
            <div>
                {showTitle ? (
                    <Divider style={{ fontSize: 25 }}>{title}</Divider>
                ) : (
                    <h1/>
                )}
                

                {/* botoes topo grid */}
                <ToolbarGrid
                    state={this.state}
                    onClickBtnAdd={this.onClickBtnAdd}
                    onClickBtnEdit={this.onClickBtnEdit}
                    onClickBtnDelete={this.onClickBtnDelete}
                    onClickBtnRefresh={this.onClickBtnRefresh}
                />

                <Table 
                    //visible={false}
                    ref={this.myRefTable}
                    rowKey="id"
                    bordered showHeader={true}
                    //pagination={{
                    //    showSizeChanger: true
                    //pageSizeOptions: ['10', '20', '30', '40']
                    //}}

                    //eventos de click e duplo click na linha 
                    onRow={(record, rowIndex) => {
                        return {
                            onClick: event => {
                                this.onClickRow(record, rowIndex, event);
                            },
                            onDoubleClick: event => {
                                this.onClickRow(record, rowIndex, event);
                                //this.onShowDrawerForm(record);
                            }
                        };
                    }}

                    //caixa de seleçao nos itens
                    rowSelection={{
                            onChange: (selectedRowKeys, selectedRows) => {                                                            
                                this.setState({ selectedRows: selectedRows });
                            },
                            onSelect: (record, selected, selectedRows) => {

                                console.log(selectedRows);

                                this.setState({ selectedRows: selectedRows });

                                console.log(this.state);
                                console.log(this.state.selectedRows);
                            }       
                        }                                        
                    }

                    columns={columns}
                    dataSource={this.state.data}
                    loading={this.state.loading}
                    //style={{backgroundColor: "#0391AD", borderColor: "#276D7A" }}
                />

            </div>
        );
    }

}

export default Grid;