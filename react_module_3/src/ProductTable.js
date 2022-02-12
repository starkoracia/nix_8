import React, {Component} from 'react';
import Table from "./Table";
import axios from "axios";
import AddProductModalWindow from "./AddProductModalWindow";
import EditProductModalWindow from "./EditProductModalWindow";
import HystModal from "hystmodal";
import DeleteProductModalWindow from "./DeleteProductModalWindow";
import Navbar from "./Navbar";


class ProductTable extends Component {

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
            productToEdit: {id: 0, productName: '', price: 0},
            productToDelete: {id: 0, productName: '', price: 0},
            products: []
        }
        this.myModal = new HystModal({
            linkAttributeName: "data-hystmodal",
        });
    }

    getElements = () => {
        axios.post('http://localhost:8080/products', {
            numberOfElementsOnPage: this.state.numberOfRows,
            pageNumber: this.state.pageNumber,
            searchString: this.state.searchField,
            isSortAsc: this.state.isAsc,
            sortBy: this.state.sortField
        })
            .then((response) => {
                this.setState({
                        products: response.data.dtoEntities,
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
            axios.post('http://localhost:8080/products/coincidences', {
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

    createNewProduct = (name, price) => {
        axios.post('http://localhost:8080/products/create', {
            productName: name,
            price: price
        })
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

    editProduct = (product) => {
        axios.post('http://localhost:8080/products/edit', product)
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

    deleteProduct = (product) => {
        axios.post('http://localhost:8080/products/delete', product)
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

    onChangeSearchField = (event) => {
        const text = event.target.value;
        this.setState({searchField: text},
            () => this.getNumberOfSearchMatches())
    }

    componentDidMount() {
        this.getElements();
    }

    paginationOnClick = (pageNumber) => {
        this.setState({pageNumber: pageNumber},
            () => this.getElements());

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

    onSubmitAddNewProduct = (event, n, p) => {
        this.createNewProduct(n, p);
    }

    onSubmitEditProduct = () => {
        this.editProduct(this.state.productToEdit);
    }

    onSubmitDeleteProduct = () => {
        this.deleteProduct(this.state.productToDelete);
    }

    onClickAdd = () => {
        this.myModal.open('#addProduct');
    }

    onClickEdit = (product) => {
        this.setState({productToEdit: product});
        this.myModal.open('#editProduct');
    }

    onClickDelete = (product) => {
        this.setState({productToDelete: product})
        this.myModal.open('#deleteProduct')
    }

    onChangeNameInput = (event) => {
        const name = event.target.value;
        this.setState({productToEdit: {...this.state.productToEdit, productName: name}});
    }

    onChangePriceInput = (event) => {
        const price = event.target.value;
        this.setState({productToEdit: {...this.state.productToEdit, price: price}});
    }

    getHeaderFieldNamesMap = () => {
        const headerFieldArray = [
            {headerName: 'Id', fieldName: 'id'},
            {headerName: 'Наименование', fieldName: 'productName'},
            {headerName: 'Цена', fieldName: 'price'}
        ]
        const headerFieldMap = new Map();
        headerFieldArray.forEach(headerField => {
            headerFieldMap.set(headerField.headerName, headerField.fieldName)
        })
        return headerFieldMap;
    }


    createRowColumns = (product, editDeleteDiv) => {
        let rowColumns = [];

        let idColumn = <td key={product.id}> {product.id}</td>;
        rowColumns.push(idColumn);
        let nameColumn = <td key={product.productName}> {product.productName}</td>;
        rowColumns.push(nameColumn);
        let priceColumn = <td className={'last-row-column'} key={product.price}> {product.price} {editDeleteDiv()}</td>;
        rowColumns.push(priceColumn);

        return rowColumns;
    }


    render() {
        const sortField = this.state.sortField;
        const elements = this.state.products;
        const numberOfRows = this.state.numberOfRows;
        const pageNumber = this.state.pageNumber;
        const totalPages = this.state.totalOfPages;
        const amountOfElements = this.state.amountOfElements;
        const searchMatches = this.state.searchMatches;
        const isAsc = this.state.isAsc;
        const headerFieldNamesMap = this.getHeaderFieldNamesMap();
        const onClickSortIcon = this.onClickSortIcon;
        const onSubmitAddNewProduct = this.onSubmitAddNewProduct;
        const onClickEdit = this.onClickEdit;
        const onClickDelete = this.onClickDelete;
        const onChangeNameInput = this.onChangeNameInput;
        const onChangePriceInput = this.onChangePriceInput;
        const onSubmitEditProduct = this.onSubmitEditProduct;
        const myModal = this.myModal;
        const onSubmitDeleteProduct = this.onSubmitDeleteProduct;
        return (
            <>
                <Navbar linkName={'products'}/>
                <Table
                    onClickAdd={this.onClickAdd}
                    isAsc={isAsc}
                    sortField={sortField}
                    searchMatches={searchMatches}
                    onChangeSearchField={this.onChangeSearchField}
                    amountOfElements={amountOfElements}
                    totalPages={totalPages}
                    currentPage={pageNumber}
                    numberOfRows={numberOfRows}
                    onChangeNumberOfRowsHandler={this.onChangeNumberOfRowsHandler}
                    paginationOnClick={this.paginationOnClick}
                    onSearchSubmit={this.onSearchSubmit}
                    onClickSortIcon={onClickSortIcon}
                    onClickEdit={onClickEdit}
                    onClickDelete={onClickDelete}
                    tableName={'Товары'}
                    elements={elements}
                    headerFieldNamesMap={headerFieldNamesMap}
                    createRowColumns={this.createRowColumns}/>
                <AddProductModalWindow
                    hystmodalId={'addProduct'}
                    windowClassName={'add-edit-window'}
                    onSubmitAddNewProduct={onSubmitAddNewProduct}
                    myModal={myModal}/>
                <EditProductModalWindow
                    hystmodalId={'editProduct'}
                    windowClassName={'add-edit-window'}
                    productToEdit={this.state.productToEdit}
                    onChangeNameInput={onChangeNameInput}
                    onChangePriceInput={onChangePriceInput}
                    onSubmitEditProduct={onSubmitEditProduct}
                    myModal={myModal}/>
                <DeleteProductModalWindow
                    hystmodalId={'deleteProduct'}
                    windowClassName={'delete-window'}
                    myModal={myModal}
                    onSubmitDeleteProduct={onSubmitDeleteProduct}/>
            </>
        );
    }
}

export default ProductTable;