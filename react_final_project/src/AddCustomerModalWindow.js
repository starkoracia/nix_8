import React, {Component} from 'react';
import {Button, Card, Container, Form} from "react-bootstrap";
import HModal from "./HModal";

class AddCustomerModalWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            firstNameInputValue: '',
            lastNameInputValue: '',
            phoneNumberInputValue: ''
        }
    }

    onClickClearFormAndClose = () => {
        this.setState({
            firstNameInputValue: '',
            lastNameInputValue: '',
            phoneNumber: ''
        })
        this.props.myModal.close();
    }

    onChangeFirstNameInput = (event) => {
        const value = event.target.value;
        this.setState({firstNameInputValue: value});
    }

    onChangeLastNameInput = (event) => {
        const value = event.target.value;
        this.setState({lastNameInputValue: value});
    }

    onChangePhoneNumberInput = (event) => {
        const value = event.target.value;
        this.setState({phoneNumberInputValue: value});
    }

    onSubmitAdd = () => {
        let customer = {
            firstName: this.state.firstNameInputValue,
            lastName: this.state.lastNameInputValue,
            phoneNumber: this.state.phoneNumberInputValue
        }
        this.props.onSubmitAddNewCustomer(customer);
    }

    isFirstNameNotValid = () => {
        const firstNameInputValue = this.state.firstNameInputValue;
        if(firstNameInputValue === '') {
            return true;
        }
        return false;
    }

    isPhoneNumberNotValid = () => {
        const phoneNumberInputValue = this.state.phoneNumberInputValue;
        if(phoneNumberInputValue === '' || phoneNumberInputValue.toString().length < 10) {
            return true;
        }
        return false;
    }

    isFormNotValid = () => {
        return this.isFirstNameNotValid() || this.isPhoneNumberNotValid()
    }

    firstNameInvalidMessage = () => {
        const firstNameNotValid = this.isFirstNameNotValid;
        if(firstNameNotValid()) {
            return (
                <Form.Text className={'invalid-message'}>
                    Имя не должно быть пустым!
                </Form.Text>
            )
        }
    }

    phoneNumberInvalidMessage = () => {
        const phoneNumberNotValid = this.isPhoneNumberNotValid;
        if(phoneNumberNotValid()) {
            return (
                <Form.Text className={'invalid-message'}>
                    Телефон не должен быть пустым или меньше 10 цифр!
                </Form.Text>
            )
        }
    }


    render() {
        const onClickClearFormAndClose = this.onClickClearFormAndClose;
        const hystmodalId = this.props.hystmodalId;
        const windowClassName = this.props.windowClassName;
        const firstNameInputValue = this.state.firstNameInputValue;
        const lastNameInputValue = this.state.lastNameInputValue;
        const phoneNumberInputValue = this.state.phoneNumberInputValue;
        const changeFirstNameInput = this.onChangeFirstNameInput;
        const changeLastNameInput = this.onChangeLastNameInput;
        const changePhoneNumberInput = this.onChangePhoneNumberInput;
        const onSubmitAdd = this.onSubmitAdd;
        return (
            <HModal
                hystmodalId={hystmodalId}
                windowClassName={windowClassName}>
                <Container className={'add-container'}>
                    <Card className={'add-card'}>
                        <Card.Header>
                            <h4>Новый клиент</h4>
                        </Card.Header>
                        <Card.Body className={'add-card-body'}>
                            <Form className={'add-form'}>
                                <Form.Group className={'add-form-group'}>
                                    <Form.Label>Имя</Form.Label>
                                    <Form.Control type={'text'} placeholder={'Имя'}
                                                  value={firstNameInputValue}
                                                  onChange={changeFirstNameInput}/>
                                    {this.firstNameInvalidMessage()}
                                </Form.Group>

                                <Form.Group className={'add-form-group'}>
                                    <Form.Label>Фамилия</Form.Label>
                                    <Form.Control type={'text'} placeholder={'Фамилия'}
                                                  value={lastNameInputValue}
                                                  onChange={changeLastNameInput}/>
                                </Form.Group>

                                <Form.Group className={' add-form-group'}>
                                    <Form.Label>Телефон</Form.Label>
                                    <Form.Control type={'number'} placeholder={'Телефон'}
                                                  min="0" max="999999999999" step="1"
                                                  value={phoneNumberInputValue}
                                                  onChange={changePhoneNumberInput}/>
                                    {this.phoneNumberInvalidMessage()}
                                </Form.Group>

                            </Form>
                        </Card.Body>
                        <Card.Footer>
                            <div className={'add-cancel-buttons-div'}>
                                <Button className={'add-form-button'} variant={'secondary'} type={'button'}
                                        disabled={this.isFormNotValid()}
                                        onClick={(event) => {
                                            onSubmitAdd();
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

export default AddCustomerModalWindow;