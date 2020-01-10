import React, { Component } from 'react';
import { Drawer, Form, Divider } from 'antd';


export class FormTeste extends Component {

    constructor(props) {
        super(props);

        this.state = {
            widthDrawer: '50%'
        };
    } 

    render() {
        const props = this.props;

        return (
            <Form visible={props.visible}>                    
                <Divider>Cadastro de Layouts</Divider>
            </Form>
        );
    }
}


export default FormTeste;