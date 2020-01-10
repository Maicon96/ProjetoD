import React, { Component } from 'react';
import { Drawer } from 'antd';


export class DrawerGrid extends Component {

    constructor(props) {
        super(props);

        this.state = {
            widthDrawer: '50%'
        };
    }
    
    componentDidMount() {
        window.addEventListener("resize", this.resize.bind(this));
        this.resize();
    }

    resize() {
        if (window.innerWidth <= 600) {
            this.setState({widthDrawer: '70%'});
        } else {
            this.setState({widthDrawer: '50%'});
        }
    }

    render() {
        const props = this.props;

        return (
            <Drawer
                title={props.title}
                placement={'right'}
                onClose={props.onClose}
                visible={props.visible}
                destroyOnClose={true}
                width={this.state.widthDrawer}
            >
                {props.form}
            </Drawer>
        );
    }
}


export default DrawerGrid;