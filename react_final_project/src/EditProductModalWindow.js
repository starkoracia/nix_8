import React, {Component} from 'react';
import HystModal from "hystmodal";
import {Button, Card, Container, Form} from "react-bootstrap";
import HModal from "./HModal";

class EditProductModalWindow extends Component {

    isNameNotValid = () => {
        const name = this.props.productToEdit.name;
        if (name === '') {
            return true;
        }
        return false;
    }

    isPriceNotValid = () => {
        const price = this.props.productToEdit.price;
        if (price === '') {
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
        this.props.myModal.close();
    }


    render() {
        const hystmodalId = this.props.hystmodalId;
        const windowClassName = this.props.windowClassName;
        const isFormNotValid = this.isFormNotValid;
        const onClickClearFormAndClose = this.onClickClearFormAndClose;
        const name = this.props.productToEdit.productName;
        const onChangeNameInput = this.props.onChangeNameInput;
        const price = this.props.productToEdit.price;
        const onChangePriceInput = this.props.onChangePriceInput;
        const onSubmitEditProduct = this.props.onSubmitEditProduct;
        return (
            <HModal
                hystmodalId={hystmodalId}
                windowClassName={windowClassName}>
                <Container className={'add-container'}>
                    <Card className={'add-card'}>
                        <Card.Header>
                            <h4>Изменение товара</h4>
                        </Card.Header>
                        <Card.Body className={'add-card-body'}>
                            <Form className={'add-form'} onSubmit={() => console.log('Submit')}>
                                <Form.Group className={'add-form-group'}>
                                    <Form.Label>Наименование</Form.Label>
                                    <Form.Control type={'text'} placeholder={'Наименование'}
                                                  value={name}
                                                  onChange={onChangeNameInput}/>
                                    {this.nameInvalidMessage()}
                                </Form.Group>

                                <Form.Group className={'add-product-price-form-group add-form-group'}>
                                    <Form.Label>Цена</Form.Label>
                                    <Form.Control type={'number'} placeholder={'Цена'}
                                                  min="0.00" max="999999999.99" step="0.01"
                                                  value={price}
                                                  onChange={onChangePriceInput}/>
                                    {this.priceInvalidMessage()}
                                </Form.Group>

                            </Form>
                        </Card.Body>
                        <Card.Footer>
                            <div className={'add-cancel-buttons-div'}>
                                <Button className={'add-form-button'} variant={'secondary'} type={'button'}
                                        disabled={isFormNotValid()}
                                        onClick={(event) => {
                                            onSubmitEditProduct(event);
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

export default EditProductModalWindow;