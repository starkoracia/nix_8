import React, {Component} from 'react';
import HModal from "./HModal";
import {Button, Card, Container} from "react-bootstrap";

class DeleteOrderModalWindow extends Component {

    onClickClearFormAndClose = () => {
        this.props.myModal.close();
    }

    render() {
        const hystmodalId = this.props.hystmodalId;
        const windowClassName = this.props.windowClassName;
        const onClickClearFormAndClose = this.onClickClearFormAndClose;
        const onSubmitDeleteOrder = this.props.onSubmitDeleteOrder;
        return (
            <HModal
                hystmodalId={hystmodalId}
                windowClassName={windowClassName}>
                <Container className={'delete-container'}>
                    <Card className={'add-card'}>
                        <Card.Header>
                            <h4>Удаление заказа</h4>
                        </Card.Header>
                        <Card.Body className={'delete-card-body'}>
                            <h5>Подтвердите удаление заказа!</h5>
                        </Card.Body>
                        <Card.Footer>
                            <div className={'add-cancel-buttons-div'}>
                                <Button className={'add-form-button'} variant={'danger'} type={'button'}
                                        onClick={() => {
                                            onSubmitDeleteOrder();
                                            onClickClearFormAndClose();
                                        }}>
                                    Удалить
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

export default DeleteOrderModalWindow;