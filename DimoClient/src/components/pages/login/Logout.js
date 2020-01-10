import { Component } from 'react';

export class Logout extends Component {  

  logout = () => {
    sessionStorage.clear();
  }

  render() {
    this.logout();

    const baseUrl = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
    window.location.href = baseUrl + "/";

    return ('');
  }
}

export default Logout;