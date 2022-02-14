import React, {Component} from 'react';
import Table from "./Table";
import HystModal from "hystmodal";
import axios from "axios";
import AddProductModalWindow from "./AddProductModalWindow";
import AddOrderModalWindow from "./AddOrderModalWindow";
import EditProductModalWindow from "./EditProductModalWindow";
import EditOrderModalWindow from "./EditOrderModalWindow";
import DeleteProductModalWindow from "./DeleteProductModalWindow";
import DeleteOrderModalWindow from "./DeleteOrderModalWindow";
import Navbar from "./Navbar";

class OrderTable extends Component {
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
            orderToEdit: {id: 0, customer: {id: 0, firstName: '', lastName: '', phoneNumber: 0}, products: []},
            customerOptions: [],
            products: [],
            orders: []
        }
        this.myModal = new HystModal({
            linkAttributeName: "data-hystmodal",
        });
    }

    componentDidMount() {
        this.getElements();
    }

    getElements = () => {
        axios.post('http://localhost:8080/orders', {
            numberOfElementsOnPage: this.state.numberOfRows,
            pageNumber: this.state.pageNumber,
            searchString: this.state.searchField,
            isSortAsc: this.state.isAsc,
            sortBy: this.state.sortField
        })
            .then((response) => {
                this.setState({
                        orders: response.data.dtoEntities,
                        amountOfElements: response.data.amountOfElements
                    },
                    () => this.countAndSetTheTotalOfPages());

            })
            .catch(function (error) {
                console.log(error);
            })
    }

    createNewOrder = (order) => {
        axios.post('http://localhost:8080/orders/create', order)
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

    editOrder = (order) => {
        axios.post('http://localhost:8080/orders/edit', order)
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

    deleteOrder = (order) => {
        axios.post('http://localhost:8080/orders/delete', order)
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

    getNumberOfSearchMatches = () => {
        const searchField = this.state.searchField;
        if (searchField !== '') {
            axios.post('http://localhost:8080/orders/coincidences', {
                searchString: searchField
            })
                .then((response) => {
                    const searchMatches = response.data;
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

    getCustomerOptionsAndOpenWindow = (windowId) => {
        axios.get('http://localhost:8080/customers/options')
            .then((response) => {
                const customers = response.data;
                const customerOptions = customers.map(customer => {
                    const label = customer.firstName + ' ' + customer.lastName + ' ' + customer.phoneNumber;
                    return {
                        label: label,
                        value: customer
                    };
                })
                return customerOptions;
            })
            .then((customerOptions) => {
                axios.get('http://localhost:8080/products')
                    .then((response) => {
                        this.setState({
                                products: response.data,
                                customerOptions: customerOptions
                            },
                            () => this.myModal.open(windowId));

                    })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    getProducts = () => {
        axios.get('http://localhost:8080/products')
            .then((response) => {
                this.setState({
                    products: response.data
                });

            })
            .catch(function (error) {
                console.log(error);
            })
    }

    getProductsFromOrder = (order, callback) => {
        axios.post('http://localhost:8080/orders/products', order)
            .then((response) => {
                const productsFromOrder = response.data;
                callback(productsFromOrder);
            })
            .catch(function (error) {
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

    onChangeNumberOfRowsHandler = (event) => {
        const newTotalPages = this.countTheTotalOfPages(event.target.value, this.state.amountOfElements);
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
        this.getCustomerOptionsAndOpenWindow('#addOrder');
    }

    onClickEdit = (order) => {
        this.getProductsFromOrder(order,
            (products) => {
                order.products = products;
                this.setState({orderToEdit: order},
                    () => this.getCustomerOptionsAndOpenWindow('#editOrder'));
            })
        this.setState({orderToEdit: order},
            () => this.getCustomerOptionsAndOpenWindow('#editOrder'));
    }

    onClickDelete = (order) => {
        this.setState({orderToDelete: order});
        this.myModal.open('#deleteOrder');
    }

    onSubmitDeleteOrder = () => {
        const orderToDelete = this.state.orderToDelete;
        this.deleteOrder(orderToDelete);
    }

    onSubmitEditNewOrder = () => {
        const orderToEdit = this.state.orderToEdit;
        this.editOrder(orderToEdit);
        console.log(orderToEdit);
    }

    onChangeCustomerSelect = (selectCustomer) => {
        let customer = selectCustomer.value;
        this.setState({orderToEdit: {...this.state.orderToEdit, customer: customer}});
    }

    onDblClickProductTable = (product) => {
        const productsToAdd = this.state.orderToEdit.products;
        if (productsToAdd.includes(product)) {
            return;
        }
        productsToAdd.push(product);
        this.setState({orderToEdit: {...this.state.orderToEdit, products: productsToAdd}});
    }

    onDblClickProductToAddTable = (product) => {
        const productsToAdd = this.state.orderToEdit.products.filter(p => {
            return p !== product;
        });
        this.setState({orderToEdit: {...this.state.orderToEdit, products: productsToAdd}});
    }

    onSubmitAddNewOrder = (order) => {
        this.createNewOrder(order);
    }

    getHeaderFieldNamesMap = () => {
        const headerFieldArray = [
            {headerName: 'Id', fieldName: 'id'},
            {headerName: 'Клиент', fieldName: 'customer'},
            {headerName: 'Товары', fieldName: 'countProducts'}
        ]
        const headerFieldMap = new Map();
        headerFieldArray.forEach(headerField => {
            headerFieldMap.set(headerField.headerName, headerField.fieldName)
        })
        return headerFieldMap;
    }

    onChangeSearchField = (event) => {
        const text = event.target.value;
        this.setState({searchField: text},
            () => this.getNumberOfSearchMatches())
    }

    createRowColumns = (order, editDeleteDiv) => {
        let rowColumns = [];

        let idColumn = <td key={order.id}> {order.id}</td>;
        rowColumns.push(idColumn);
        let customerColumn = <td
            key={order.id + 1}> {order.customer.firstName + ' ' + order.customer.lastName }</td>;
        rowColumns.push(customerColumn);
        let countProductsColumn = <td className={'last-row-column'}
                                      key={order.id + 2}> {order.countProducts} {editDeleteDiv()}</td>;
        rowColumns.push(countProductsColumn);

        return rowColumns;
    }

    render() {
        const elements = this.state.orders;
        const sortField = this.state.sortField;
        const numberOfRows = this.state.numberOfRows;
        const pageNumber = this.state.pageNumber;
        const totalPages = this.state.totalOfPages;
        const amountOfElements = this.state.amountOfElements;
        const searchMatches = this.state.searchMatches;
        const isAsc = this.state.isAsc;
        const headerFieldNamesMap = this.getHeaderFieldNamesMap();
        const onClickSortIcon = this.onClickSortIcon;
        const onClickEdit = this.onClickEdit;
        const onClickDelete = this.onClickDelete;
        const myModal = this.myModal;
        const onSubmitAddNewOrder = this.onSubmitAddNewOrder;
        const customerOptions = this.state.customerOptions;
        const products = this.state.products;
        const orderToEdit = this.state.orderToEdit;
        return (
            <>
                <Navbar linkName={'orders'}/>
                <Table
                    onClickAdd={this.onClickAdd}
                    isAsc={isAsc}
                    sortField={sortField}
                    searchMatches={searchMatches}
                    amountOfElements={amountOfElements}
                    totalPages={totalPages}
                    currentPage={pageNumber}
                    numberOfRows={numberOfRows}
                    tableName={'Заказы'}
                    elements={elements}
                    headerFieldNamesMap={headerFieldNamesMap}
                    onChangeSearchField={this.onChangeSearchField}
                    createRowColumns={this.createRowColumns}
                    onChangeNumberOfRowsHandler={this.onChangeNumberOfRowsHandler}
                    paginationOnClick={this.paginationOnClick}
                    onSearchSubmit={this.onSearchSubmit}
                    onClickSortIcon={onClickSortIcon}
                    onClickEdit={onClickEdit}
                    onClickDelete={onClickDelete}/>
                <AddOrderModalWindow
                    hystmodalId={'addOrder'}
                    windowClassName={'add-edit-window'}
                    onSubmitAddNewOrder={onSubmitAddNewOrder}
                    customerOptions={customerOptions}
                    onChangeCustomerSelect={this.onChangeCustomerSelect}
                    elements={products}
                    myModal={myModal}/>
                <EditOrderModalWindow
                    hystmodalId={'editOrder'}
                    windowClassName={'add-edit-window'}
                    elements={products}
                    orderToEdit={orderToEdit}
                    customerOptions={customerOptions}
                    onChangeCustomerSelect={this.onChangeCustomerSelect}
                    onDblClickProductTable={this.onDblClickProductTable}
                    onDblClickProductToAddTable={this.onDblClickProductToAddTable}
                    onSubmitEditNewOrder={this.onSubmitEditNewOrder}
                    myModal={myModal}/>
                <DeleteOrderModalWindow
                    hystmodalId={'deleteOrder'}
                    windowClassName={'delete-window'}
                    myModal={myModal}
                    onSubmitDeleteOrder={this.onSubmitDeleteOrder}/>
            </>
        );
    }
}

export default OrderTable;