import React, {useEffect, useRef, useState} from 'react';
import Table from "./Table";
import axios from "axios";
import HystModal from "hystmodal";
import Navbar from "./Navbar";
import {Route, useNavigate, Routes} from 'react-router-dom';
import {Button} from "react-bootstrap";
import CreatePayment from "./CreatePayment";

function CustomerTable() {
    const [numberOfRows, setNumberOfRows] = useState(10);
    const [pageNumber, setPageNumber] = useState(1);
    const [amountOfElements, setAmountOfElements] = useState(0);
    const [totalOfPages, setTotalOfPages] = useState(1);
    const [searchField, setSearchField] = useState('');
    const [searchMatches, setSearchMatches] = useState(0);
    const [sort, setSort] = useState({sortField: 'id', isAsc: true});
    const [customerToPayment, setCustomerToPayment] = useState({id: 0, name: '', accounts: []});
    const [customers, setCustomers] = useState([]);
    const myModal = useRef(new HystModal({linkAttributeName: "data-hystmodal"}));
    const firstTimeRender = useRef(true);
    const navigate = useNavigate();

    useEffect(() => {
        countAndSetTheTotalOfPages();
    }, [amountOfElements, customers])

    useEffect(() => {
        getElements();
    }, [numberOfRows, pageNumber, sort])

    useEffect(() => {
        if (!firstTimeRender.current) {
            if (searchField !== '') {
                getNumberOfSearchMatches();
            } else {
                setSearchMatches(0);
                getElements();
            }
        } else {
            firstTimeRender.current = false;
        }
    }, [searchField])

    useEffect(() => {
        if(customerToPayment.id === 0) {
            return;
        }
    }, [customerToPayment])

    const getElements = () => {
        axios.post('http://localhost:8080/customers', {
            numberOfElementsOnPage: numberOfRows,
            pageNumber: pageNumber,
            searchString: searchField,
            isSortAsc: sort.isAsc,
            sortBy: sort.sortField
        })
            .then((response) => {
                console.log(response.data);
                setCustomers(response.data.dtoEntities);
                setAmountOfElements(response.data.amountOfElements);
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
                setCustomerToPayment(customer);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    const getNumberOfSearchMatches = () => {
        axios.post('http://localhost:8080/customers/coincidences', {
            searchString: searchField
        })
            .then((response) => {
                const searchMatches = response.data;
                console.log(searchMatches)
                setSearchMatches(searchMatches);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    const countAndSetTheTotalOfPages = () => {
        const pages = countTheTotalOfPages(numberOfRows, amountOfElements);
        setTotalOfPages(pages);
    }

    const countTheTotalOfPages = (numberOfRows, amountOfElements) => {
        const amount = amountOfElements;
        let pages = Math.floor(amount / numberOfRows);
        if (amount % numberOfRows !== 0) {
            return pages + 1;
        }
        return pages;
    }

    const onChangeSearchField = (event) => {
        const text = event.target.value;
        setSearchField(text);
    }

    const onChangeNumberOfRowsHandler = (event) => {
        const newNumbersOfRows = event.target.value;
        let newTotalPages = countTheTotalOfPages(newNumbersOfRows, amountOfElements);
        newTotalPages === 0 && (newTotalPages = 1);
        if (newTotalPages < pageNumber) {
            setPageNumber(newTotalPages);
        }
        setNumberOfRows(newNumbersOfRows);
    }

    const paginationOnClick = (pageNumber) => {
        setPageNumber(pageNumber);
    }

    const onSearchSubmit = (event) => {
        event.preventDefault();

        let pages = countTheTotalOfPages(numberOfRows, searchMatches);
        pages === 0 && (pages = 1);
        if (searchMatches === 0 && searchField === '') {
            return;
        }
        if (pages < pageNumber) {
            setPageNumber(pages);
        } else {
            console.log('onSearchSubmit')
            getElements();
        }
    }

    const onClickSortIcon = (sortName, isAsc) => {
        const lastSortField = sort.sortField;
        let isAscNew = true;
        if (lastSortField === sortName) {
            isAscNew = !isAsc;
        }
        setSort({sortField: sortName, isAsc: isAscNew});
    }

    const onDblClick = (customer) => {
        getCustomerAccounts(customer);
        navigate('/customers/payment');
        console.log(customerToPayment);
    }

    const getHeaderFieldNamesMap = () => {
        const headerFieldArray = [
            {headerName: 'Id', fieldName: 'id'},
            {headerName: 'Имя', fieldName: 'name'}
        ]
        const headerFieldMap = new Map();
        headerFieldArray.forEach(headerField => {
            headerFieldMap.set(headerField.headerName, headerField.fieldName)
        })
        return headerFieldMap;
    }

    const createRowColumns = (customer, editDeleteDiv) => {
        let rowColumns = [];

        let idColumn = <td key={customer.id}> {customer.id}</td>;
        rowColumns.push(idColumn);
        let firstNameColumn = <td key={customer.name}> {customer.name}</td>;
        rowColumns.push(firstNameColumn);

        return rowColumns;
    }

    function cancelCustomer() {
        navigate('/customers');
        getElements();
    }

    return (
        <>
            <Navbar/>
            <div className={'main-board-div'}>
                <Table
                    onChangeSearchField={onChangeSearchField}
                    onChangeNumberOfRowsHandler={onChangeNumberOfRowsHandler}
                    paginationOnClick={paginationOnClick}
                    onSearchSubmit={onSearchSubmit}
                    onClickSortIcon={onClickSortIcon}
                    onClickEdit={onDblClick}
                    headerFieldNamesMap={getHeaderFieldNamesMap()}
                    isAsc={sort.isAsc}
                    sortField={sort.sortField}
                    searchMatches={searchMatches}
                    amountOfElements={amountOfElements}
                    totalPages={totalOfPages}
                    currentPage={pageNumber}
                    numberOfRows={numberOfRows}
                    tableName={'Клиенты'}
                    elements={customers}
                    createRowColumns={createRowColumns}/>
                <Routes>
                    <Route path="/payment" element={
                        <CreatePayment
                            customerToPayment={customerToPayment}
                            cancelCustomer={cancelCustomer}
                        />
                    }/>
                </Routes>
            </div>
        </>
    );
}

export default CustomerTable;