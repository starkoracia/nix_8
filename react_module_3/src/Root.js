import React, {Component} from 'react';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import CustomerTable from "./CustomerTable";
import ShowMessage from "./css/ShowMessage";
import {Button} from "react-bootstrap";


export default class Root extends Component {
    constructor(props) {
        super(props);
        this.state = {
            elements: [],
            alertIsShow: false,
            alertVariant: 'success',
            alertMessage: ''
        }
    }

    showMessage = (message, variant) => {
        this.setState({
            alertIsShow: true,
            alertVariant: variant,
            alertMessage: message
        }, () => setTimeout(
            () => this.setState({alertIsShow: false}),
            5000
        ))
    }

    render() {
        const showMessage = this.showMessage;
        const alertVariant = this.state.alertVariant;
        const alertIsShow = this.state.alertIsShow;
        const alertMessage = this.state.alertMessage;
        return (
            <BrowserRouter>
                <ShowMessage
                    show={alertIsShow}
                    variant={alertVariant}
                    message={alertMessage}/>
                {/*<Navbar/>*/}
                <Routes>
                    <Route exact path="/" element={<Navigate replace to={"/customers"}/>}/>
                    <Route path="/customers/*" element={<CustomerTable showMessage={showMessage}/>}/>
                </Routes>
            </BrowserRouter>
        );
    }
}

