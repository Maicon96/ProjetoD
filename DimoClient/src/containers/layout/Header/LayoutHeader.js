import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Layout, Button, Icon, Row, Col, Drawer, Avatar, Tooltip } from 'antd';

const { Header } = Layout;

export default class LayoutHeader extends Component {
  state = { 
    visible: false, 
    placement: 'right'
  };

  showDrawer = () => {
    this.setState({
      visible: true,
    });
  };

  onClose = () => {
    this.setState({
      visible: false,
    });
  };

  render() {
    const props = this.props;

    const userName = sessionStorage.getItem("userName") || "";

    return (
      <div>
        <Header style={{ backgroundImage: 'linear-gradient(to right, #5F5CAB, #5F5CAB)', height: 64, padding: 0 }}>
          <Row>
            <Col span={14} align="left">
              {/*<img src={props.logo} alt="FisSy" style={{ marginLeft: 15}} /> */}
              <Tooltip title="Expandir/Retrair">
                <Button type="primary" icon={props.collapsed ? "menu-unfold" : "menu-fold"} 
                  style={{marginLeft: 20, backgroundColor: "#5F76C2", borderColor: "white" }} 
                  onClick={props.toggleCollapsed} />
              </Tooltip>
            </Col>

            <Col span={10} align="right">

              <Tooltip placement="bottomRight" title="Minha conta">
                <Button type="primary" 
                  style={{ marginRight: 5, backgroundColor: "#5F76C2", borderColor: "white"}}                   
                  onClick={this.showDrawer}>
                  <Icon type="user" />
                </Button>
              </Tooltip>
            </Col>
          </Row>
        </Header>

        <Drawer
          title="Minha Conta"
          placement={this.state.placement}
          onClose={this.onClose}
          visible={this.state.visible}
          >
          <Avatar size="large" icon="user" /> {userName}
          <br/><br/>
          <p><Link to="/logout">Sair</Link></p>
        </Drawer>        
      </div>
    );
  }
}