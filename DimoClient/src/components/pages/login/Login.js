import React, { Component } from 'react';
import { Form, Icon, Input, Button, notification } from 'antd';

import './login.css';


const ajax = require('../../../utils/ajax/ConexaoAjax');
const globalVariables = require('../../../utils/variaveisGlobais');


class Login extends React.Component {

  constructor(props) {
    super(props);

    // responsavel por pegar o input que se deseja jogar o foco
    this.usuario = React.createRef();
    this.senha = React.createRef();

    this.state = {
      passwordLocked: true,
      activeTab: '1',
      password: ''
    };
  }

  handleSubmit = e => {
    const me = this;

    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        ajax.Call({
          url: globalVariables.default.baseURLServer + '/login/autenticar/usuario',
          data: {
            login: values.usuario,
            senha: values.senha
          },
          afterMsgSuccessTrue: function (response) {
            sessionStorage.setItem("Authorization", 'Bearer ' + response.data.token);
            sessionStorage.setItem("login", response.data.registro.login);
            
            window.location.href = globalVariables.default.baseURLClient + "/";
          },
          afterMsgSuccessFalse: function () {
            notification.error({
              message: 'Erro ao logar!',
              description: 'Credenciais não conferem.',
              duration: 10
            });

            //this.usuario.current.focus();

          }               
        });
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;

    return (
      <div className="App-login">
        <div className="App-login-container">

          <Form onSubmit={this.handleSubmit} className="login-form">
            
            <Form.Item style={{ textAlign: 'center' }}>
              <h1>Login</h1>
            </Form.Item>

            <Form.Item>
              {getFieldDecorator('usuario', {
                rules: [{ required: true, message: 'Informe seu Usuário!' }],
              })(
                <Input
                  ref={this.usuario}
                  prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                  placeholder="Usuário"
                />,
              )}
            </Form.Item>
            <Form.Item>
              {getFieldDecorator('senha', {
                rules: [{ required: true, message: 'Informe sua Senha!' }],
              })(
                <Input
                  ref={this.senha}
                  prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
                  type="password"
                  placeholder="Senha"
                />,
              )}
            </Form.Item>
            <Form.Item style={{ textAlign: 'center'}}>
              <Button type="primary" htmlType="submit" style={{ backgroundColor: '#5F5CAB', borderColor: '#5F5CAB'}} >
                Entrar
              </Button>              
            </Form.Item>
          </Form>
        </div>
      </div>
    );
  }
}

const WrappedNormalLoginForm = Form.create()(Login);

export default WrappedNormalLoginForm;