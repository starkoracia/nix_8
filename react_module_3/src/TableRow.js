import React, {Component} from 'react';

class TableRow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showEditDeleteIcons: false
        }
    }

    onMouseOver = () => {
        this.setState({showEditDeleteIcons: true})
    }

    onMouseOut = () => {
        this.setState({showEditDeleteIcons: false})
    }

    editDeleteDiv = () => {
        const element = this.props.element;
        const onClickDelete = this.props.onClickDelete;
        const showEditDeleteIcons = this.state.showEditDeleteIcons;
        return (
            <div className={'delete-edit-div'}>
                <img className={'delete-icon'} src={'images/icons/delete-icon.png'}
                     onClick={() => onClickDelete(element)}
                     style={{display: showEditDeleteIcons ? 'inline' : 'none'}}/>
            </div>
        );
    }

    render() {
        const element = this.props.element;
        const onClickEdit = this.props.onClickEdit;
        return (
            <tr onMouseOver={this.onMouseOver}
                onMouseOut={this.onMouseOut}
            onDoubleClick={() => onClickEdit(element)}>
                {this.props.createRowColumns(element, this.editDeleteDiv)}
            </tr>
        );
    }
}

export default TableRow;