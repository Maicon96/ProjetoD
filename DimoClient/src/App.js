import React from 'react';

import RouterGeral from './config/router/RouterGeral';
import RouterLogin from './config/router/RouterLogin';

import DndTest from './containers/dnd/index';

const auth = sessionStorage.getItem("Authorization") || null;
const login = sessionStorage.getItem("login") || null;


class App extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    if (auth && login) {
      return (
        <div className="App">
          <RouterGeral />
        </div>
      );
    } else {
      return (
        <div className="App">
          <RouterLogin />
        </div>
      );
    }    
  }
}

export default App;
