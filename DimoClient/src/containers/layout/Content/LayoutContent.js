import React, { Component } from 'react';
import { Layout } from 'antd';

const { Content } = Layout;

export class LayoutContent extends Component {

  render() {
    const props = this.props;

    return (
      <Content style={{ overflowY: 'auto', overflowX: 'hidden', height: 'calc(100vh - 72px)', 
        margin: 4, padding: 5, background: '#F0F2F5' }}>
        {props.setComponent}
      </Content>
    );
  }
}

export default LayoutContent;