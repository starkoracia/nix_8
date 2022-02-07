import React, {Component} from 'react';
import Table from "./Table";
import axios from "axios";
import AddProductModalWindow from "./AddProductModalWindow";
import HystModal from "hystmodal";
import AddCustomerModalWindow from "./AddCustomerModalWindow";
import EditCustomerModalWindow from "./EditCustomerModalWindow";
import DeleteProductModalWindow from "./DeleteProductModalWindow";
import DeleteCustomerModalWindow from "./DeleteCustomerModalWindow";
import Navbar from "./Navbar";

class CustomerTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            numberOfRows: 10,
            pageNumber: 1,
            amountOfElements: 0,
            totalOfPages: 1,
            searchField: '',
            searchMatches: 0,
            sortField: 'id',
            isAsc: true,
            customerToEdit: {id: 0, firstName: '', lastName: '', phoneNumber: 0},
            customerToDelete: {id: 0, firstName: '', lastName: '', phoneNumber: 0},
            customers: []
        }
        this.myModal = new HystModal({
            linkAttributeName: "data-hystmodal",
        });
    }

    componentDidMount() {
        this.getElements();
    }

    getElements = () => {
        axios.post('http://localhost:8080/customers', {
            numberOfElementsOnPage: this.state.numberOfRows,
            pageNumber: this.state.pageNumber,
            searchString: this.state.searchField,
            isSortAsc: this.state.isAsc,
            sortBy: this.state.sortField
        })
            .then((response) => {
                console.log(response.data);
                this.setState({
                        customers: response.data.dtoEntities,
                        amountOfElements: response.data.amountOfElements
                    },
                    () => this.countAndSetTheTotalOfPages());

            })
            .catch(function (error) {
                console.log(error);
            })
    }

    getNumberOfSearchMatches = () => {
        const searchField = this.state.searchField;
        if (searchField !== '') {
            axios.post('http://localhost:8080/customers/coincidences', {
                searchString: searchField
            })
                .then((response) => {
                    const searchMatches = response.data;
                    console.log(response.data);
                    this.setState({
                        searchMatches: searchMatches
                    });
                })
                .catch(function (error) {
                    console.log(error);
                })
        } else {
            this.setState({
                searchMatches: 0
            }, () => this.getElements());
        }
    }

    createNewCustomer = (customer) => {
        axios.post('http://localhost:8080/customers/create', customer)
            .then((response) => {
                if (response.data) {
                    this.props.showMessage('Успешно создан!', 'success')
                    this.getElements();
                } else {
                    this.props.showMessage('Ошибка создания!', 'danger')
                }

            })
            .catch((error) => {
                this.props.showMessage('Ошибка создания!', 'danger')
                console.log(error);
            })
    }

    editCustomer = (customer) => {
        axios.post('http://localhost:8080/customers/edit', customer)
            .then((response) => {
                if (response.data) {
                    this.props.showMessage('Успешно обновлен!', 'success')
                    this.getElements();
                } else {
                    this.props.showMessage('Ошибка обновления!', 'danger')
                }
            })
            .catch((error) => {
                this.props.showMessage('Ошибка обновления!', 'danger')
                console.log(error);
            })
    }

    deleteCustomer = (customer) => {
        axios.post('http://localhost:8080/customers/delete', customer)
            .then((response) => {
                if (response.data) {
                    this.props.showMessage('Успешно удален!', 'success')
                    this.getElements();
                } else {
                    this.props.showMessage('Ошибка удаления!', 'danger')
                }
            })
            .catch((error) => {
                this.props.showMessage('Ошибка удаления!', 'danger')
                console.log(error);
            })
    }

    countAndSetTheTotalOfPages = () => {
        const pages = this.countTheTotalOfPages(this.state.numberOfRows, this.state.amountOfElements);
        this.setState({totalOfPages: pages});
    }

    countTheTotalOfPages = (numberOfRows, amountOfElements) => {
        const amount = amountOfElements;
        let pages = Math.floor(amount / numberOfRows);
        if (amount % numberOfRows !== 0) {
            return pages + 1;
        }
        return pages;
    }

    onChangeSearchField = (event) => {
        const text = event.target.value;
        this.setState({searchField: text},
            () => this.getNumberOfSearchMatches())
    }

    onChangeNumberOfRowsHandler = (event) => {
        let newTotalPages = this.countTheTotalOfPages(event.target.value, this.state.amountOfElements);
        newTotalPages === 0 && (newTotalPages = 1);
        if (newTotalPages < this.state.pageNumber) {
            this.setState({
                pageNumber: newTotalPages,
                numberOfRows: event.target.value
            }, () => this.getElements());
        } else {
            this.setState({numberOfRows: event.target.value},
                () => this.getElements());
        }
    }

    paginationOnClick = (pageNumber) => {
        this.setState({pageNumber: pageNumber},
            () => this.getElements());

    }

    onSearchSubmit = (event) => {
        event.preventDefault();
        const amountOfElements = this.state.searchMatches;
        const numberOfRows = this.state.numberOfRows;
        let pages = this.countTheTotalOfPages(numberOfRows, amountOfElements);
        pages === 0 && (pages = 1);
        if (amountOfElements === 0 && this.state.searchField === '') {
            return;
        }
        if (pages < this.state.pageNumber) {
            this.setState({pageNumber: pages},
                () => this.getElements());
        } else {
            this.getElements();
        }
    }

    onClickSortIcon = (sortName, isAsc) => {
        const lastSortField = this.state.sortField;
        let isAscNew = true;
        if (lastSortField === sortName) {
            isAscNew = !isAsc;
        }
        this.setState({
                sortField: sortName,
                isAsc: isAscNew
            },
            () => this.getElements());
    }

    onClickAdd = () => {
        this.myModal.open('#addCustomer');
    }

    onClickEdit = (customer) => {
        this.setState({customerToEdit: customer});
        this.myModal.open('#editCustomer');
    }

    onClickDelete = (customer) => {
        this.setState({customerToDelete: customer})
        this.myModal.open('#deleteCustomer')
    }

    onSubmitAddNewCustomer = (customer) => {
        this.createNewCustomer(customer);
    }

    onChangeFirstNameInput = (event) => {
        const firstName = event.target.value;
        this.setState({customerToEdit: {...this.state.customerToEdit, firstName: firstName}});
    }

    onChangeLastNameInput = (event) => {
        const lastName = event.target.value;
        this.setState({customerToEdit: {...this.state.customerToEdit, lastName: lastName}});
    }

    onChangePhoneNumberInput = (event) => {
        const phoneNumber = event.target.value;
        this.setState({customerToEdit: {...this.state.customerToEdit, phoneNumber: phoneNumber}});
    }

    onSubmitEditCustomer = () => {
        this.editCustomer(this.state.customerToEdit);
    }

    onSubmitDeleteCustomer = () => {
        this.deleteCustomer(this.state.customerToDelete);
    }

    getHeaderFieldNamesMap = () => {
        const headerFieldArray = [
            {headerName: 'Id', fieldName: 'id'},
            {headerName: 'Имя', fieldName: 'firstName'},
            {headerName: 'Фамилия', fieldName: 'lastName'},
            {headerName: 'Телефон', fieldName: 'phoneNumber'}
        ]
        const headerFieldMap = new Map();
        headerFieldArray.forEach(headerField => {
            headerFieldMap.set(headerField.headerName, headerField.fieldName)
        })
        return headerFieldMap;
    }

    createRowColumns = (customer, editDeleteDiv) => {
        let rowColumns = [];

        let idColumn = <td key={customer.id}> {customer.id}</td>;
        rowColumns.push(idColumn);
        let firstNameColumn = <td key={customer.firstName}> {customer.firstName}</td>;
        rowColumns.push(firstNameColumn);
        let lastNameColumn = <td key={customer.lastName}> {customer.lastName}</td>;
        rowColumns.push(lastNameColumn);
        let phoneNumberColumn = <td className={'last-row-column'} key={customer.phoneNumber}> {customer.phoneNumber} {editDeleteDiv()}</td>;
        rowColumns.push(phoneNumberColumn);

        return rowColumns;
    }

    render() {
        const sortField = this.state.sortField;
        const isAsc = this.state.isAsc;
        const elements = this.state.customers;
        const numberOfRows = this.state.numberOfRows;
        const pageNumber = this.state.pageNumber;
        const totalPages = this.state.totalOfPages;
        const amountOfElements = this.state.amountOfElements;
        const searchMatches = this.state.searchMatches;
        const headerFieldNamesMap = this.getHeaderFieldNamesMap();
        const onClickSortIcon = this.onClickSortIcon;
        const onClickEdit = this.onClickEdit;
        const onClickDelete = this.onClickDelete;
        const onSubmitAddNewCustomer = this.onSubmitAddNewCustomer;
        const myModal = this.myModal;
        const onChangeFirstNameInput = this.onChangeFirstNameInput;
        const onChangeLastNameInput = this.onChangeLastNameInput;
        const onChangePhoneNumberInput = this.onChangePhoneNumberInput;
        const onSubmitEditCustomer = this.onSubmitEditCustomer;
        const onSubmitDeleteCustomer = this.onSubmitDeleteCustomer;
        return (
            <>
                <Navbar linkName={'customers'}/>
                <Table
                    onChangeSearchField={this.onChangeSearchField}
                    onChangeNumberOfRowsHandler={this.onChangeNumberOfRowsHandler}
                    paginationOnClick={this.paginationOnClick}
                    onSearchSubmit={this.onSearchSubmit}
                    onClickSortIcon={onClickSortIcon}
                    onClickEdit={onClickEdit}
                    onClickDelete={onClickDelete}
                    headerFieldNamesMap={headerFieldNamesMap}
                    isAsc={isAsc}
                    sortField={sortField}
                    searchMatches={searchMatches}
                    amountOfElements={amountOfElements}
                    totalPages={totalPages}
                    currentPage={pageNumber}
                    numberOfRows={numberOfRows}
                    onClickAdd={this.onClickAdd}
                    tableName={'Клиенты'}
                    elements={elements}
                    createRowColumns={this.createRowColumns}/>
                <AddCustomerModalWindow
                    hystmodalId={'addCustomer'}
                    windowClassName={'add-edit-window'}
                    onSubmitAddNewCustomer={onSubmitAddNewCustomer}
                    myModal={myModal}/>
                <EditCustomerModalWindow
                    hystmodalId={'editCustomer'}
                    windowClassName={'add-edit-window'}
                    customerToEdit={this.state.customerToEdit}
                    onChangeFirstNameInput={onChangeFirstNameInput}
                    onChangeLastNameInput={onChangeLastNameInput}
                    onChangePhoneNumberInput={onChangePhoneNumberInput}
                    onSubmitEditCustomer={onSubmitEditCustomer}
                    myModal={myModal}/>
                <DeleteCustomerModalWindow
                    hystmodalId={'deleteCustomer'}
                    windowClassName={'delete-window'}
                    myModal={myModal}
                    onSubmitDeleteCustomer={onSubmitDeleteCustomer}/>
            </>
        );
    }
}

export default CustomerTable;