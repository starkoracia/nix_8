import React, {Component} from 'react';
import {Button, Card, Container, Form, Table as TableBs} from "react-bootstrap";
import HModal from "./HModal";
import Select from "react-select";
import TableBody from "./TableBody";

class EditOrderModalWindow extends Component {

    onChangeCustomerSelect = (value) => {
        this.setState({customerSelectValue: value});
    }

    isClientNotValid = () => {
        const customerSelectValue = this.getCustomerSelectValue();
        if (customerSelectValue == undefined) {
            return true;
        }
        return false;
    }

    isProductsToAddNotValid = () => {
        const productsToAdd = this.props.orderToEdit.products;
        if (productsToAdd.length === 0) {
            return true;
        }
        return false;
    }

    isFormNotValid = () => {
        return this.isProductsToAddNotValid() || this.isClientNotValid()
    }

    clientInvalidMessage = () => {
        const isNameNotValid = this.isClientNotValid;
        if (isNameNotValid()) {
            return (
                <Form.Text className={'invalid-message'}>
                    Поле клиента не должно быть пустым!
                </Form.Text>
            )
        }
    }

    productsInvalidMessage = () => {
        const isPriceNotValid = this.isProductsToAddNotValid;
        if (isPriceNotValid()) {
            return (
                <Form.Text className={'invalid-message'}>
                    Вы должны добавить минимум один продукт!
                </Form.Text>
            )
        }
    }

    onClickClearFormAndClose = () => {
        this.setState({
            customerSelectValue: '',
            productsToAdd: []
        })
        this.props.myModal.close();
    }

    createTableHeader = () => {
        let tableHeader = [];
        tableHeader.push(<th key={'Id'}>Id</th>);
        tableHeader.push(<th key={'Наименование'}>Наименование</th>);
        tableHeader.push(<th key={'Цена'}>Цена</th>);
        return tableHeader;
    }

    getProductFieldNames = () => {
        return ['id', 'productName', 'price']
    }

    createRowColumns = (product) => {
        let rowColumns = [];

        let idColumn = <td key={product.id}> {product.id}</td>;
        rowColumns.push(idColumn);
        let nameColumn = <td key={product.productName}> {product.productName}</td>;
        rowColumns.push(nameColumn);
        let priceColumn = <td className={'last-row-column'} key={product.price}> {product.price}</td>;
        rowColumns.push(priceColumn);

        return rowColumns;
    }

    onSubmitEditNewOrder = () => {
        const order = {
            customer: this.state.customerSelectValue.value,
            products: this.state.productsToAdd
        }
        this.props.onSubmitAddNewOrder(order)
    }

    getCustomerSelectValue = () => {
        const customer = this.props.orderToEdit.customer;
        const label = customer.firstName + ' ' + customer.lastName + ' ' + customer.phoneNumber;
        return {
            label: label,
            value: customer
        };
    }

    render() {
        const hystmodalId = this.props.hystmodalId;
        const windowClassName = this.props.windowClassName;
        const isFormNotValid = this.isFormNotValid;
        const onClickClearFormAndClose = this.onClickClearFormAndClose;
        const customerOptions = this.props.customerOptions;
        const elements = this.props.elements;
        const customerSelectValue = this.getCustomerSelectValue();
        const productsToAdd = this.props.orderToEdit.products;
        const onChangeCustomerSelect = this.props.onChangeCustomerSelect;
        const onDblClickProductTable = this.props.onDblClickProductTable;
        const onDblClickProductToAddTable = this.props.onDblClickProductToAddTable;
        const onSubmitEditNewOrder = this.props.onSubmitEditNewOrder;
        return (
            <HModal
                hystmodalId={hystmodalId}
                windowClassName={windowClassName}>
                <Container className={'add-container'}>
                    <Card className={'add-card'}>
                        <Card.Header>
                            <h4>Изменение заказа</h4>
                        </Card.Header>
                        <Card.Body className={'add-card-body'}>
                            <Form className={'add-form'}>
                                <Form.Group className={'add-form-group'}>
                                    <Form.Label>Клиент</Form.Label>
                                    <Select options={customerOptions}
                                            value={customerSelectValue}
                                            onChange={onChangeCustomerSelect}/>
                                    {this.clientInvalidMessage()}
                                </Form.Group>

                                <Form.Group className={'add-product-price-form-group add-form-group'}>
                                    <Form.Label>Товары</Form.Label>
                                    <Card>
                                        <Card.Body className={'table-card-body scroll'}>
                                            <TableBs striped bordered hover className={'table-sm'}>
                                                <thead>
                                                <tr>
                                                    {this.createTableHeader()}
                                                </tr>
                                                </thead>
                                                <TableBody
                                                    elements={elements}
                                                    fieldNames={this.getProductFieldNames()}
                                                    onClickEdit={onDblClickProductTable}
                                                    createRowColumns={this.createRowColumns}
                                                />
                                            </TableBs>
                                        </Card.Body>
                                    </Card>
                                </Form.Group>

                                <Form.Group className={'add-product-price-form-group add-form-group'}>
                                    <Form.Label>Добавленные в заказ</Form.Label>
                                    <Card>
                                        <Card.Body className={'table-card-body scroll'}>
                                            <TableBs striped bordered hover className={'table-sm'}>
                                                <thead>
                                                <tr>
                                                    {this.createTableHeader()}
                                                </tr>
                                                </thead>
                                                <TableBody
                                                    elements={productsToAdd}
                                                    fieldNames={this.getProductFieldNames()}
                                                    onClickEdit={onDblClickProductToAddTable}
                                                    createRowColumns={this.createRowColumns}
                                                />
                                            </TableBs>
                                        </Card.Body>
                                    </Card>
                                    {this.productsInvalidMessage()}
                                </Form.Group>
                            </Form>
                        </Card.Body>
                        <Card.Footer>
                            <div className={'add-cancel-buttons-div'}>
                                <Button className={'add-form-button'} variant={'secondary'} type={'button'}
                                        disabled={isFormNotValid()}
                                        onClick={() => {
                                            onSubmitEditNewOrder();
                                            onClickClearFormAndClose();
                                        }}>
                                    Изменить
                                </Button>
                                <Button className={'cancel-form-button'} variant={'warning'} type={'button'}
                                        onClick={onClickClearFormAndClose}>
                                    Отменить
                                </Button>
                            </div>
                        </Card.Footer>
                    </Card>
                </Container>
            </HModal>
        );
    }
}

export default EditOrderModalWindow;