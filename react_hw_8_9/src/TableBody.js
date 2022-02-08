import React, {Component} from 'react';
import TableRow from "./TableRow";
import {Container} from "react-bootstrap";

class TableBody extends Component {

    render() {
        const elements = this.props.elements;
        const fieldNames = this.props.fieldNames;
        return (
            <tbody>
            {this.createTableRows(elements, fieldNames)}
            </tbody>
        );
    }

    createTableRows(elements, fieldNames) {
        const rows = [];
        if (Array.isArray(elements) && elements.length) {
            elements.forEach((element, i) => {
                const onClickEdit = this.props.onClickEdit;
                const onClickDelete = this.props.onClickDelete;
                const createRowColumns = this.props.createRowColumns;
                rows.push(
                    <TableRow
                        element={element}
                        key={element.id}
                        onClickEdit={onClickEdit}
                        onClickDelete={onClickDelete}
                        createRowColumns={createRowColumns}
                    />
                );
            })
        } else {
            rows.push(
                <tr key={-1}>
                    <td colSpan={fieldNames.length}>
                <Container>
                    <h4 style={{alignContent: "center"}}>
                        Не найдено
                    </h4>
                </Container>
                    </td>
                </tr>
            )
        }
        return rows;
    }

}

export default TableBody;