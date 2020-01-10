import React, { Component } from 'react';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';

import Login from '../../components/pages/login/Login';


export class RouterLogin extends Component {

  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route path="/login" render={() => <Login/>} />
          <Redirect to="/login" />
        </Switch>
      </ BrowserRouter>
    );
  }
}

export default RouterLogin;