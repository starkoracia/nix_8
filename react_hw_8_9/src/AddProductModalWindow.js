import React, {Component} from 'react';
import HModal from "./HModal";
import {Button, Card, Container, Form, FormText} from "react-bootstrap";
import HystModal from "hystmodal";

class AddProductModalWindow extends Component {

    constructor(props) {
        super(props);
        this.state = {
            nameInputValue: '',
            priceInputValue: ''
        }
    }

    onChangeNameInput = (event) => {
        const value = event.target.value;
        this.setState({nameInputValue: value});
    }

    onChangePriceInput = (event) => {
        const value = event.target.value;
        this.setState({priceInputValue: value});
    }

    isNameNotValid = () => {
        const nameInputValue = this.state.nameInputValue;
        if (nameInputValue === '') {
            return true;
        }
        return false;
    }

    isPriceNotValid = () => {
        const priceInputValue = this.state.priceInputValue;
        if (priceInputValue === '') {
            return true;
        }
        return false;
    }

    isFormNotValid = () => {
        return this.isPriceNotValid() || this.isNameNotValid()
    }

    nameInvalidMessage = () => {
        const isNameNotValid = this.isNameNotValid;
        if (isNameNotValid()) {
            return (
                <Form.Text className={'invalid-message'}>
                    Наименование не должно быть пустым!
                </Form.Text>
            )
        }
    }

    priceInvalidMessage = () => {
        const isPriceNotValid = this.isPriceNotValid;
        if (isPriceNotValid()) {
            return (
                <Form.Text className={'invalid-message'}>
                    Вы должны указать цену товара!
                </Form.Text>
            )
        }
    }

    onClickClearFormAndClose = () => {
        this.setState({
            nameInputValue: '',
            priceInputValue: ''
        })
        this.props.myModal.close();
    }


    render() {
        const hystmodalId = this.props.hystmodalId;
        const windowClassName = this.props.windowClassName;
        const onSubmitAddNewProduct = this.props.onSubmitAddNewProduct;
        const nameInputValue = this.state.nameInputValue;
        const priceInputValue = this.state.priceInputValue;
        const changeNameInput = this.onChangeNameInput;
        const changePriceInput = this.onChangePriceInput;
        const isFormNotValid = this.isFormNotValid;
        const onClickClearFormAndClose = this.onClickClearFormAndClose;
        return (
            <HModal
                hystmodalId={hystmodalId}
                windowClassName={windowClassName}>
                <Container className={'add-container'}>
                    <Card className={'add-card'}>
                        <Card.Header>
                            <h4>Новый товар</h4>
                        </Card.Header>
                        <Card.Body className={'add-card-body'}>
                            <Form className={'add-form'}>
                                <Form.Group className={'add-form-group'}>
                                    <Form.Label>Наименование</Form.Label>
                                    <Form.Control type={'text'} placeholder={'Наименование'}
                                                  value={nameInputValue}
                                                  onChange={changeNameInput}/>
                                    {this.nameInvalidMessage()}
                                </Form.Group>

                                <Form.Group className={'add-product-price-form-group add-form-group'}>
                                    <Form.Label>Цена</Form.Label>
                                    <Form.Control type={'number'} placeholder={'Цена'}
                                                  min="0.00" max="999999999.99" step="0.01"
                                                  value={priceInputValue}
                                                  onChange={changePriceInput}/>
                                    {this.priceInvalidMessage()}
                                </Form.Group>

                            </Form>
                        </Card.Body>
                        <Card.Footer>
                            <div className={'add-cancel-buttons-div'}>
                                <Button className={'add-form-button'} variant={'secondary'} type={'button'}
                                        disabled={isFormNotValid()}
                                        onClick={(event) => {
                                            onSubmitAddNewProduct(event, nameInputValue, priceInputValue);
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

export default AddProductModalWindow;