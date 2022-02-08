import React, {Component} from 'react';
import {Button, Card, Container, Form, Table as TableBs} from "react-bootstrap";
import HModal from "./HModal";
import Select from "react-select";
import TableBody from "./TableBody";

class AddOrderModalWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            customerSelectValue: '',
            productsToAdd: []
        }
    }

    onChangeCustomerSelect = (value) => {
        this.setState({customerSelectValue: value});
    }

    isClientNotValid = () => {
        const customerSelectValue = this.state.customerSelectValue;
        if (customerSelectValue === '') {
            return true;
        }
        return false;
    }

    isProductsToAddNotValid = () => {
        const productsToAdd = this.state.productsToAdd;
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

    createRowColumns = (product, editDeleteDiv) => {
        let rowColumns = [];

        let idColumn = <td key={product.id}> {product.id}</td>;
        rowColumns.push(idColumn);
        let nameColumn = <td key={product.productName}> {product.productName}</td>;
        rowColumns.push(nameColumn);
        let priceColumn = <td className={'last-row-column'} key={product.price}> {product.price}</td>;
        rowColumns.push(priceColumn);

        return rowColumns;
    }

    onDblClickProductTable = (product) => {
        const productsToAdd = this.state.productsToAdd;
        if (productsToAdd.includes(product)) {
            return;
        }
        productsToAdd.push(product);
        this.setState({productsToAdd: productsToAdd});
    }

    onDblClickProductToAddTable = (product) => {
        const productsToAdd = this.state.productsToAdd.filter(p => {
            return p !== product;
        });
        this.setState({productsToAdd: productsToAdd});
    }

    onSubmitAddNewOrder = () => {
        const order = {
            customer: this.state.customerSelectValue.value,
            products: this.state.productsToAdd
        }
        this.props.onSubmitAddNewOrder(order)
    }

    render() {
        const hystmodalId = this.props.hystmodalId;
        const windowClassName = this.props.windowClassName;
        const isFormNotValid = this.isFormNotValid;
        const onClickClearFormAndClose = this.onClickClearFormAndClose;
        const customerOptions = this.props.customerOptions;
        const customerSelectValue = this.state.customerSelectValue;
        const elements = this.props.elements;
        const productsToAdd = this.state.productsToAdd;
        return (
            <HModal
                hystmodalId={hystmodalId}
                windowClassName={windowClassName}>
                <Container className={'add-container'}>
                    <Card className={'add-card'}>
                        <Card.Header>
                            <h4>Новый заказ</h4>
                        </Card.Header>
                        <Card.Body className={'add-card-body'}>
                            <Form className={'add-form'}>
                                <Form.Group className={'add-form-group'}>
                                    <Form.Label>Клиент</Form.Label>
                                    <Select options={customerOptions}
                                            value={customerSelectValue}
                                            onChange={this.onChangeCustomerSelect}/>
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
                                                    onClickEdit={this.onDblClickProductTable}
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
                                                    onClickEdit={this.onDblClickProductToAddTable}
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
                                        onClick={(event) => {
                                            this.onSubmitAddNewOrder(event);
                                            onClickClearFormAndClose();
                                        }}>
                                    Добавить
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

export default AddOrderModalWindow;