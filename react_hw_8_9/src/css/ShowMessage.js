import React, {Component} from 'react';
import {Alert} from "react-bootstrap";

class ShowMessage extends Component {
    render() {
        let style = {}
        const variant = this.props.variant;
        const show = this.props.show;
        if (variant === 'success') {
            style = {borderColor: 'yellowgreen'};
        } else if (variant === 'danger') {
            style = {borderColor: 'pink'};
        }
        return (
                <Alert className={'show-message'}
                       show={show}
                       style={style}
                       variant={variant}>
                    {this.props.message}
                </Alert>
            );
    }
}

export default ShowMessage;