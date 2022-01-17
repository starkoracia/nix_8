package ua.com.alevel.persistence.dao.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.com.alevel.config.jdbc.JdbcConnector;
import ua.com.alevel.persistence.dao.DaoCustomer;
import ua.com.alevel.persistence.entity.Customer;
import ua.com.alevel.persistence.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ua.com.alevel.persistence.dao.SQLQueryUtil.*;

@Service
public class CustomerDao implements DaoCustomer {

    JdbcConnector connector;
    OrderDao orderDao;

    public CustomerDao(JdbcConnector connector, @Lazy OrderDao orderDao) {
        this.connector = connector;
        this.orderDao = orderDao;
    }


    @Override
    public void create(Customer customer) {
        String createQuery = String.format(CUSTOMER_CREATE_SQL_QUERY,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber());
        try (Statement statement = connector.getConnection().createStatement()) {
            statement.executeUpdate(createQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        String updateQuery = String.format(CUSTOMER_UPDATE_SQL_QUERY,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getId());
        try (Statement statement = connector.getConnection().createStatement();) {
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Customer customer) {
        String deleteQuery = String.format(CUSTOMER_DELETE_SQL_QUERY,
                customer.getId());
        try (Statement statement = connector.getConnection().createStatement();) {
            deleteCustomerOrders(customer);
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomerOrders(Customer customer) {
        List<Order> ordersFromCustomer = orderDao.getOrdersFromCustomer(customer);
        for (Order order : ordersFromCustomer) {
            orderDao.delete(order);
        }
    }

    @Override
    public Customer findById(Long id) {
        String findByIdQuery = String.format(CUSTOMER_FIND_BY_ID_SQL_QUERY,
                id);
        try (Statement statement = connector.getConnection().createStatement();) {
            ResultSet resultSet = statement.executeQuery(findByIdQuery);
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existById(Long id) {
        return findById(id) != null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(CUSTOMER_FIND_ALL_SQL_QUERY);
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public long count() {
        try (Statement statement = connector.getConnection().createStatement();) {
            ResultSet resultSet = statement.executeQuery(CUSTOMER_COUNT_SQL_QUERY);
            if (resultSet.next()) {
                return resultSet.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
