import React, {Component} from 'react';
import {Container, Nav, Navbar as NavbarBs} from "react-bootstrap";
import {Link} from "react-router-dom";


class Navbar extends Component {

    render() {
        return (
            <NavbarBs bg="light" expand="lg">
                <Container fluid>
                    <NavbarBs.Brand as={Link} to={"/"}>
                        $My-Payments
                    </NavbarBs.Brand>
                    <NavbarBs.Toggle aria-controls="basic-navbar-nav"/>
                </Container>
            </NavbarBs>
        );
    }
}

export default Navbar;