import React, { Component } from 'react';
import { Button, Icon, Tooltip } from 'antd';

import { Link } from 'react-router-dom';

let urlChamaCadastro = '';

export class ToolbarGrid extends Component {

  render() {
    const props = this.props;

    //console.log("ToolbarGrid");
    //console.log(props);
    //console.log(props.state);
    
    return (
      <div>
        <div style={{ marginBottom: 10 }}>  
          <Tooltip title='Inserir'>
            <Button type='primary' size='small' onClick={props.onClickBtnAdd}
                style={{ marginLeft: 5, backgroundColor: "#FFFFFF", borderColor: "black" }}>
              <Link to={{ pathname: `${this.props.state.urlChamaCadastro}`, params: { data: this.props.state } }} >
                <Icon type="file" style={{ color: 'black' }} />
              </Link>
            </Button>           
          </Tooltip>

          <Tooltip title='Editar'>
            <Button type='primary' size='small' onClick={props.onClickBtnEdit}
                style={{ marginLeft: 5 }}>
              <Link to={{ pathname: `${this.props.state.urlChamaCadastro}`, params: { data: this.props.state } }} >
                <Icon type="edit" />
              </Link>
            </Button>           
          </Tooltip>

          <Tooltip title="Excluir">
            <Button type="danger" size='small' onClick={props.onClickBtnDelete} 
                style={{marginLeft: 5, backgroundColor: "red", borderColor: "#FFFFFF" }}>
              <Icon type="delete" />
            </Button>
          </Tooltip>

          <Tooltip title="Atualizar">
            <Button type="primary" size='small' onClick={props.onClickBtnRefresh} 
                style={{marginLeft: 5, backgroundColor: "black", borderColor: "black" }}>
              <Icon type="sync" style={{ color: 'white' }} />
            </Button>
          </Tooltip>
        </div>
      </div>
    );
  }
}

export default ToolbarGrid;