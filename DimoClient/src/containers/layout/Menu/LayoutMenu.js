import React from 'react';

import { Menu, Icon } from 'antd';
import { Link } from 'react-router-dom';

const SubMenu = Menu.SubMenu;

export default class LayoutMenu extends React.Component {

  render() {
    const props = this.props;

    return (
      <Menu
        defaultSelectedKeys={[props.setItemMenu]}
        defaultOpenKeys={['sub1']}
        mode="inline"
        theme="light"
        inlineCollapsed={props.collapsed}
        style={{ overflowY: 'auto', overflowX: 'hidden', height: 'calc(100vh - 64px)' }}
      >

        <SubMenu key="sub1" title={<span><Icon type="file-text" /><span>Importação de Arquivos</span></span>}>

          <Menu.Item key="2">
            <Link to="/clientes">
              <Icon type="team" />
              <span>Clientes</span>
            </Link>
          </Menu.Item>

          <Menu.Item key="4">
            <Link to="/layout/arquivo">
              <Icon type="file-text" />
              <span>Layout</span>
            </Link>
          </Menu.Item>     

          <SubMenu key="sub2" title={<span><Icon type="setting" /><span>Configurações de Recebimento</span></span>}>
            <Menu.Item key="5">
              <Link to="/forma/recebimento/pasta">
                <Icon type="setting" />
                <span>Configurações de Pasta</span>
              </Link>
            </Menu.Item>     

            <Menu.Item key="7">
              <Link to="/forma/recebimento/email">
                <Icon type="setting" />
                <span>Configurações de E-mail</span>
              </Link>
            </Menu.Item>     

            <Menu.Item key="9">
              <Link to="/forma/recebimento/ftp">
                <Icon type="setting" />
                <span>Configurações de FTP</span>
              </Link>
            </Menu.Item>                    

          {/*
            <Menu.Item key="6">
              <Icon type="setting" />
              <span>Configurações de E-mail</span>
            </Menu.Item>

            <Menu.Item key="7">
              <Icon type="setting" />
              <span>FTP</span>
            </Menu.Item>
            */}

          </SubMenu>
        </SubMenu>
      </Menu>
    );
  }
}