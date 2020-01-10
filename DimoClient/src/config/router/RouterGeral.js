import React from 'react';

import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom';

//pages
import Main from '../../components/pages/Main';

import LayoutArquivoCon from '../../components/pages/layout/LayoutArquivoCon';
import LayoutArquivoCad from '../../components/pages/layout/LayoutArquivoCad';
import ClienteCon from '../../components/pages/clientes/ClienteCon';
import ClienteCad from '../../components/pages/clientes/ClienteCad';
import Logout from '../../components/pages/login/Logout';
import FormaRecebimentoPastaCon from '../../components/pages/formaRecebimento/pasta/FormaRecebimentoPastaCon';
import FormaRecebimentoPastaCad from '../../components/pages/formaRecebimento/pasta/FormaRecebimentoPastaCad';
import FormaRecebimentoEmailCon from '../../components/pages/formaRecebimento/email/FormaRecebimentoEmailCon';
import FormaRecebimentoEmailCad from '../../components/pages/formaRecebimento/email/FormaRecebimentoEmailCad';
import FormaRecebimentoFTPCon from '../../components/pages/formaRecebimento/ftp/FormaRecebimentoFTPCon';
import FormaRecebimentoFTPCad from '../../components/pages/formaRecebimento/ftp/FormaRecebimentoFTPCad';

const PrivateRoute = () => (
    <Route></Route>
)

const RouterGeral = () => (
    <BrowserRouter>
        <Switch>            
            <Route exact path="/" render={() => <Main/>} />            
            <Route exact path="/clientes" render={(props) => <Main content={{component:<ClienteCon {...props} />, menuKey:"2"}} />} />            
            <Route exact path="/cadastro/clientes" render={(props) => <Main content={{component:<ClienteCad {...props} />, menuKey:"3"}} />} />   
            <Route exact path="/layout/arquivo" render={(props) => <Main content={{component:<LayoutArquivoCon {...props} />, menuKey:"4"}} />} />
            <Route exact path="/cadastro/layout/arquivo" render={(props) => <Main content={{component:<LayoutArquivoCad {...props} />, menuKey:"5"}} />} />
            <Route exact path="/forma/recebimento/pasta" render={(props) => <Main content={{component:<FormaRecebimentoPastaCon {...props} />, menuKey:"6"}} />} />
            <Route exact path="/cadastro/forma/recebimento/pasta" render={(props) => <Main content={{component:<FormaRecebimentoPastaCad {...props} />, menuKey:"7"}} />} />
            <Route exact path="/forma/recebimento/email" render={(props) => <Main content={{component:<FormaRecebimentoEmailCon {...props} />, menuKey:"8"}} />} />
            <Route exact path="/cadastro/forma/recebimento/email" render={(props) => <Main content={{component:<FormaRecebimentoEmailCad {...props} />, menuKey:"9"}} />} />
            <Route exact path="/forma/recebimento/ftp" render={(props) => <Main content={{component:<FormaRecebimentoFTPCon {...props} />, menuKey:"10"}} />} />
            <Route exact path="/cadastro/forma/recebimento/ftp" render={(props) => <Main content={{component:<FormaRecebimentoFTPCad {...props} />, menuKey:"11"}} />} />
            <Route exact path="/logout" render={() => <Logout/>} />
            <Redirect to="/" />
        </Switch>
    </BrowserRouter>
);


export default RouterGeral;