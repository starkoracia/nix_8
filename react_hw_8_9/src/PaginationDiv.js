import React, {Component} from "react";
import {PageItem, Pagination} from "react-bootstrap";

class PaginationDiv extends Component {

    createItems(currentPage, totalPages) {
        const items = [];
        if (currentPage > 2) {
            items.push(<Pagination.Item key={1} onClick={() => this.props.paginationOnClick(1)}>{1}</Pagination.Item>)
        }
        if (currentPage > 3) {
            items.push(<Pagination.Ellipsis key={-1} disabled={true}/>)
        }
        if (currentPage > 1) {
            items.push(<Pagination.Item key={currentPage - 1}
                onClick={() => this.props.paginationOnClick(currentPage - 1)}>{currentPage - 1}</Pagination.Item>)
        }
        items.push(
            <Pagination.Item
                key={currentPage}
                disabled={true}
                className={'current-page-pagination-item'}>
                <b>{currentPage}</b>
            </Pagination.Item>)
        if (totalPages > currentPage) {
            items.push(<PageItem
                key={currentPage + 1}
                onClick={() => this.props.paginationOnClick(currentPage + 1)}>{currentPage + 1}</PageItem>)
        }
        if (currentPage + 2 < totalPages) {
            items.push(<Pagination.Ellipsis key={-1} disabled={true}/>)
        }
        if (currentPage + 1 < totalPages) {
            items.push(<Pagination.Item
                key={totalPages}
                onClick={() => this.props.paginationOnClick(totalPages)}>{totalPages}</Pagination.Item>)
        }
        return items;
    }

    isFirstPrevDisabled() {
        return this.props.currentPage <= 1;
    }

    isLastNextDisabled() {
        return this.props.totalPages <= this.props.currentPage;
    }

    render() {
        const currentPage = this.props.currentPage;
        const totalPages = this.props.totalPages == null ? 1 : this.props.totalPages;

        return (
            <Pagination size="sm">
                {this.createItems(currentPage, totalPages)}
                <Pagination.Item className={'back-pagination-button'}
                    disabled={this.isFirstPrevDisabled()}
                                 onClick={
                                     () => this.props.paginationOnClick(this.props.currentPage - 1)
                                 }>
                    Назад
                </Pagination.Item>
                <Pagination.Item disabled={this.isLastNextDisabled()}
                                 onClick={
                                     () => this.props.paginationOnClick(this.props.currentPage + 1)
                                 }>
                    Вперед
                </Pagination.Item>
            </Pagination>
        );
    }
}

export {PaginationDiv};