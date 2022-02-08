import React, {Component} from 'react';
import {Container, Nav, Navbar as NavbarBs} from "react-bootstrap";
import {Link} from "react-router-dom";


class Navbar extends Component {

    activeLink = (linkName) => {
        if (linkName === this.props.linkName) {
            return 'active-link';
        }
        return '';
    }

    render() {
        const activeLink = this.activeLink;
        return (
            <NavbarBs bg="light" expand="lg">
                <Container fluid>
                    <NavbarBs.Brand as={Link} to={"/"}>
                        My-Service
                    </NavbarBs.Brand>
                    <NavbarBs.Toggle aria-controls="basic-navbar-nav"/>
                    <NavbarBs.Collapse id="basic-navbar-nav">
                        <Container>
                            <Nav className="me-auto">
                                <Link to={"/customers"} className={'nav-link ' + activeLink('customers')}>Клиенты</Link>
                                <Link to={"/products"} className={'nav-link ' + activeLink('products')}>Товары</Link>
                                <Link to={"/orders"} className={'nav-link ' + activeLink('orders')}>Заказы</Link>
                            </Nav>
                        </Container>
                    </NavbarBs.Collapse>
                </Container>
            </NavbarBs>
        );
    }
}

export default Navbar;