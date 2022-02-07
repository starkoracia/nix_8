import React, {Component} from 'react';
import HystModal from "hystmodal";

class HModal extends Component {

    render() {
        const windowClassName = this.props.windowClassName ? this.props.windowClassName : '';
        const wrapClassName = this.props.wrapClassName ? this.props.wrapClassName : '';
        const hystmodalId = this.props.hystmodalId ? this.props.hystmodalId : 'myModal';
        return (
            <div className="hystmodal" id={hystmodalId} aria-hidden="true">
                <div className={'hystmodal__wrap ' + wrapClassName}>
                    <div className={'hystmodal__window ' + windowClassName} role="dialog" aria-modal="true">
                        {this.props.children}
                    </div>
                </div>
            </div>
        );
    }
}

export default HModal;