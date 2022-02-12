import React, {Component, useEffect, useState} from 'react';
import {Button, Card, Container, Form, FormText} from "react-bootstrap";
import Select from "react-select";
import axios from "axios";

function CreatePayment({customerToPayment, cancelCustomer}) {

    const [accountFromSelectOptions, setAccountFromSelectOptions] = useState([]);
    const [accountToSelectOptions, setAccountToSelectOptions] = useState([]);
    const [customerToSelectOptions, setCustomerToSelectOptions] = useState([]);
    const [categorySelectOptions, setCategorySelectOptions] = useState([]);
    const [accountFromSelectValue, setAccountFromSelectValue] = useState([]);
    const [accountToSelectValue, setAccountToSelectValue] = useState([]);
    const [customerToSelectValue, setCustomerToSelectValue] = useState([]);
    const [categorySelectValue, setCategorySelectValue] = useState([]);
    const [accountFromIsSelected, setAccountFromIsSelected] = useState(false);
    const [accountToIsSelected, setAccountToIsSelected] = useState(false);
    const [customerToIsSelected, setCustomerToIsSelected] = useState(false);
    const [categoryIsSelected, setCategoryIsSelected] = useState(false);
    const [amount, setAmount] = useState(0);
    const [categories, setCategories] = useState([]);
    const [customers, setCustomers] = useState([]);

    useEffect(() => {
        getAndSetCategories()
    }, [])

    useEffect(() => {
        createAndSetCategoryOptions(categories)
    }, [categories])

    useEffect(() => {
        createAndSetAccountFromSelectOptions(customerToPayment);
        clearForm();

    }, [customerToPayment])

    useEffect(() => {
        createAndSetCustomerSelectOptions();
    }, [customers])

    function clearForm() {
        setAccountFromSelectValue([]);
        setCustomerToSelectValue([]);
        setAccountToSelectValue([]);
        setCategorySelectOptions([]);
        setCategorySelectValue([]);
        setAccountFromIsSelected(false);
        setCustomerToIsSelected(false);
        setAccountToIsSelected(false);
        setCategoryIsSelected(false);
        setAmount(0);
    }

    const onChangeAccountFromSelect = (value) => {
        getAndSetCustomers();
        setAccountFromSelectValue(value);
        onAccountFromIsSelected(value.value);
    }

    function onChangeAccountToSelect(value) {
        getAndSetCategories();
        setAccountToSelectValue(value);
        setAccountToIsSelected(true);
    }

    function onChangeCustomerToSelect(value) {
        setCustomerToSelectValue(value);
        onCustomerToIsSelected(value.value)
    }

    function onAccountFromIsSelected() {
        setAccountFromIsSelected(true);
        createAndSetCustomerSelectOptions();
    }

    function onCustomerToIsSelected(customer) {
        getCustomerAccounts(customer);
        setCustomerToIsSelected(true);
        console.log(`customerTo ${JSON.stringify(customer)}`);
    }

    function onChangeCategorySelect(category) {
        setCategorySelectValue(category);
        setCategoryIsSelected(true);
    }

    function createAccountOptionsFromCustomer(customer) {
        const options = customer.accounts.map(acc => {
            const label = <div className={'account-select-row'}><h5>{acc.name}</h5>
                <div className={'balance-div'}><h4>${acc.balance}</h4></div>
            </div>
            return {
                label: label,
                value: acc
            };
        })
        return options;
    }

    const createAndSetAccountFromSelectOptions = (customer) => {
        const options = createAccountOptionsFromCustomer(customer);
        setAccountFromSelectOptions(options);
    }

    const createAndSetCustomerSelectOptions = () => {
        const options = customers
            .filter(customer => customer.id !== customerToPayment.id)
            .map(customer => {
                const label = <div className={'account-select-row'}><h5>{customer.name}</h5></div>
                return {
                    label: label,
                    value: customer
                };
            });
        setCustomerToSelectOptions(options);
    }

    function createAndSetCategoryOptions(categories) {
        const options = categories.filter(cat => !cat.isIncome)
            .map(cat => {
                const label = <div className={'account-select-row'}><h5>{`${cat.name}  -`}</h5>
                </div>
                return {
                    label: label,
                    value: cat
                }
            })
        setCategorySelectOptions(options);
    }

    function createAndSetAccountToSelectOptions(customer) {
        const options = createAccountOptionsFromCustomer(customer);
        setAccountToSelectOptions(options);
    }

    function getAndSetCustomers() {
        axios.get('http://localhost:8080/customers')
            .then((response) => {
                console.log(response.data);
                setCustomers(response.data);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    const getCustomerAccounts = (customer) => {
        axios.post('http://localhost:8080/customers/accounts', customer)
            .then((response) => {
                const accountsFromCustomer = response.data;
                customer.accounts = accountsFromCustomer;
                createAndSetAccountToSelectOptions(customer);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    const getAndSetCategories = () => {
        axios.get('http://localhost:8080/categories')
            .then((response) => {
                const categories = response.data;
                setCategories(categories);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    function addNewTransaction(transaction) {
        axios.post('http://localhost:8080/transactions/create', transaction)
            .then((response) => {

            })
            .catch(function (error) {
                console.log(error);
            })
    }

    function getCsvFromAccount(account) {
        axios.post('http://localhost:8080/transactions/csv', account)
            .then((response) => {
                const stringCsv = response.data;
                console.log(stringCsv);
                download_csv_file(stringCsv);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    function isAmountInvalid() {
        const accountFromBalance = accountFromSelectValue.value.balance;
        if (accountFromBalance < amount || amount === 0) {
            return true;
        }
        return false;
    }

    function amountInvalidMessage() {
        if (isAmountInvalid()) {
            return (
                <Form.Text className={'invalid-message'}>
                    Вы не можете перевести больше, чем имеется на аккаунте! Или меньше 1!
                </Form.Text>
            )
        }
    }

    function onChangeAmount(v) {
        setAmount(v.target.value);
    }

    function onSubmitTransaction() {
        const transaction = {
            accountFrom: accountFromSelectValue.value,
            accountTo: accountToSelectValue.value,
            category: categorySelectValue.value,
            amount: amount
        }
        addNewTransaction(transaction)
    }

    function onClickDownloadCsv() {
        getCsvFromAccount(accountFromSelectValue.value)
    }

    function download_csv_file(csv) {
        const accountName = accountFromSelectValue.value.name;
        let hiddenElement = document.createElement('a');
        hiddenElement.href = 'data:text/csv;charset=utf-8,' + encodeURI(csv);
        hiddenElement.target = '_blank';

        hiddenElement.download = `Выписка по счету ${accountName}.csv`;
        hiddenElement.click();
    }

    return (
        <>
            <Container className={'payment-container'}>
                <Card className={'payment-card'}>
                    <Card.Header>
                        <h4>{customerToPayment.name}</h4>
                    </Card.Header>
                    <Form>
                        <Form.Group className={'payment-form-group'}>
                            <Form.Label>Счет откуда переводим</Form.Label>
                            <Select className={"account-select"}
                                    options={accountFromSelectOptions}
                                    value={accountFromSelectValue}
                                    onChange={onChangeAccountFromSelect}/>
                            {accountFromIsSelected && (
                                <Button className={'download-csv-button'} onClick={onClickDownloadCsv}>Выписка по счету</Button>
                            )}
                            <hr/>
                        </Form.Group>
                        {accountFromIsSelected && (
                            <Form.Group className={'payment-form-group'}>
                                <Form.Label>Кому перевести деньги</Form.Label>
                                <Select className={"account-select"}
                                        options={customerToSelectOptions}
                                        value={customerToSelectValue}
                                        onChange={onChangeCustomerToSelect}/>
                            </Form.Group>
                        )}
                        {customerToIsSelected && (
                            <Form.Group className={'payment-form-group'}>
                                <Form.Label>На какой счет</Form.Label>
                                <Select className={"account-select"}
                                        options={accountToSelectOptions}
                                        value={accountToSelectValue}
                                        onChange={onChangeAccountToSelect}/>
                                <hr/>
                            </Form.Group>
                        )}
                        {accountToIsSelected && (
                            <Form.Group className={'payment-form-group'}>
                                <Form.Label>Категория</Form.Label>
                                <Select className={"account-select"}
                                        options={categorySelectOptions}
                                        value={categorySelectValue}
                                        onChange={onChangeCategorySelect}/>
                            </Form.Group>
                        )}
                        {categoryIsSelected && (
                            <Form.Group className={'payment-form-group'}>
                                <Form.Label>Сумма перевода</Form.Label>
                                <Form.Control type={'number'} placeholder={'Сумма перевода'}
                                              className={'payment-amount-input'}
                                              min="0.00" max="999999999.99" step="0.01"
                                              value={amount}
                                              onChange={onChangeAmount}
                                />
                                {amountInvalidMessage()}
                                <hr/>
                            </Form.Group>
                        )}
                    </Form>
                    {categoryIsSelected && (
                        <Card.Footer className={'payment-footer'}>
                            <div className={'add-cancel-buttons-div'}>
                                <Button className={'add-form-button'} variant={'danger'} type={'button'}
                                        disabled={isAmountInvalid()}
                                        onClick={() => {
                                            onSubmitTransaction();
                                            clearForm();
                                            cancelCustomer();
                                        }}>
                                    Перевести
                                </Button>
                                <Button className={'cancel-form-button'} variant={'warning'} type={'button'}
                                        onClick={() => {
                                            clearForm();
                                            cancelCustomer();
                                        }}>
                                    Отменить
                                </Button>
                            </div>
                        </Card.Footer>
                    )}
                </Card>
            </Container>
        </>
    )

}

export default CreatePayment;