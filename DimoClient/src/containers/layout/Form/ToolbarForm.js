import React, { Component } from 'react';
import { Button, Icon, Tooltip } from 'antd';


export class ToolbarForm extends Component {

  render() {
    const props = this.props;

    return (
      <div>
        <div style={{ marginBottom: 10 }}>  
          
          <Tooltip title="Salvar">
            <Button type="primary" size='small' onClick={props.onClickBtnSalvar}
                style={{ marginLeft: 5, backgroundColor: "#00BF39", borderColor: "#00BF39" }}>
              <Icon type="save" />
            </Button>
          </Tooltip>

          <Tooltip title="Novo registro">
            <Button type="primary" size='small' onClick={props.onClickBtnNovo}
                style={{ marginLeft: 5, backgroundColor: "#FFFFFF", borderColor: "black" }}>
              <Icon type="file" style={{ color: 'black' }} />
            </Button>
          </Tooltip>

          <Tooltip title="Excluir">
            <Button type="primary" size='small' onClick={props.onClickBtnDelete} 
                style={{ marginLeft: 5, backgroundColor: "red", borderColor: "#FFFFFF" }}>                
              <Icon type="delete" />
            </Button>
          </Tooltip>

        </div>
      </div>
    );
  }
}

export default ToolbarForm;