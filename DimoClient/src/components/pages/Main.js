import React, { Component } from 'react';
import { Layout } from 'antd';

import LayoutHeader from '../../containers/layout/Header/LayoutHeader';
import LayoutMenu from '../../containers/layout/Menu/LayoutMenu';
import LayoutContent from '../../containers/layout/Content/LayoutContent';

const { Sider } = Layout;

export default class Main extends Component {

    constructor(props) {
        super(props);

        this.state = {
            collapsed: false,
            displayMenu: 'block'
        };
    }

    componentDidMount() {
        window.addEventListener("resize", this.resize.bind(this));
        this.resize();
    }

    resize() {
        //se o sistema rodar em uma tela menor que 760 de largura, entao o menu vai retrair
        if (window.innerWidth <= 760) {
            this.setState({ collapsed: true });            
        } else {
            this.setState({ collapsed: false, displayMenu: 'block' });           
        }
    }

    //faz menu lateral abrir/fechar
    toggleCollapsed = () => {
        if (window.innerWidth <= 500) {
            this.setState({ displayMenu: (this.state.displayMenu !== 'block' ? 'block' : 'none') });
        } else {
            this.setState({
                collapsed: !this.state.collapsed, 
                //logo: !this.state.collapsed ? logoMenor : logo
            });
        }
    }  

    render() {
        const props = this.props;
        let layoutContent = null;

        if (props.content) {
            layoutContent = <LayoutContent setComponent={props.content.component} />  
        }

        return (
            <Layout style={{ minHeight: '100vh'}}>
                                
                <LayoutHeader collapsed={this.state.collapsed}
                            toggleCollapsed={this.toggleCollapsed}                               
                            logout={this.logout} />

                <Layout style={{ backgroundColor: '#fff' }}>
                    <Sider
                        trigger={null}
                        collapsible
                        collapsed={this.state.collapsed}
                        theme={'light'}
                        width={300}
                        style={{ display: this.state.displayMenu}}
                    >

                        <div style={{ width: 'auto', left: 0, margin: 0, padding: 0 }}>
                            <LayoutMenu collapsed={this.state.collapsed} />
                        </div>

                    </Sider>

                    {layoutContent}

                </Layout>

            </Layout>                          
        );        
    }
} 