
import React, { Component } from 'react';
import { Button, Icon, Tooltip } from 'antd';


export class ToolbarRowGrid extends Component {

  render() {
    const props = this.props;
    const record = JSON.stringify(props.record);

    return (
      <div>
        <Tooltip title='Excluir'>
          <Button type='danger' size='small' value={record} onClick={props.onClickBtnDelete}>
            <Icon type='delete' />
          </Button>
        </Tooltip>

        <Tooltip title='Editar'>
          <Button type='primary' size='small' value={record} onClick={props.onClickBtnEdit} style={{ marginLeft: 5 }}>
            <Icon type='edit' />
          </Button>
        </Tooltip>
      </div>
    );
  }
}

export default ToolbarRowGrid;