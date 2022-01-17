package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jdbc.JdbcConnector;
import ua.com.alevel.persistence.dao.DaoOrder;
import ua.com.alevel.persistence.entity.Customer;
import ua.com.alevel.persistence.entity.Order;
import ua.com.alevel.persistence.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static ua.com.alevel.persistence.dao.SQLQueryUtil.*;

@Service
public class OrderDao implements DaoOrder {

    JdbcConnector connector;
    CustomerDao customerDao;

    public OrderDao(JdbcConnector connector, CustomerDao customerDao) {
        this.connector = connector;
        this.customerDao = customerDao;
    }

    @Override
    public void create(Order order) {
        try (PreparedStatement statement = connector.getConnection().prepareStatement(ORDER_CREATE_SQL_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            statement.setLong(1, order.getCustomer().getId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                order.setId(id);
            }
            updateProducts(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order order) {
        String updateQuery = String.format(ORDER_UPDATE_SQL_QUERY,
                order.getCustomer().getId(),
                order.getId());
        try (Statement statement = connector.getConnection().createStatement();) {
            statement.executeUpdate(updateQuery);
            updateProducts(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Order order) {
        String deleteQuery = String.format(ORDER_DELETE_SQL_QUERY,
                order.getId());
        try (Statement statement = connector.getConnection().createStatement();) {
            deleteProducts(order);
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findById(Long id) {
        String findByIdQuery = String.format(ORDER_FIND_BY_ID,
                id);
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(findByIdQuery);
            if (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateProducts(Order order) {
        String deleteProductsQuery = String.format(ORDER_DELETE_PRODUCTS_SQL_QUERY,
                order.getId());
        try (Statement statement = connector.getConnection().createStatement()) {
            statement.executeUpdate(deleteProductsQuery);
            Set<Product> products = order.getProducts();
            for (Product product : products) {
                String setProductQuery = String.format(ORDER_SET_PRODUCTS_SQL_QUERY,
                        order.getId(), product.getId());
                statement.executeUpdate(setProductQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteProducts(Order order) {
        String deleteProductsQuery = String.format(ORDER_DELETE_PRODUCTS_SQL_QUERY,
                order.getId());
        try (Statement statement = connector.getConnection().createStatement()) {
            statement.executeUpdate(deleteProductsQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Order createOrderFromResultSet(ResultSet resultSet) throws SQLException {
        long customer_id = resultSet.getLong("customer_id");
        Customer customer = customerDao.findById(customer_id);
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setCustomer(customer);
        Set<Product> products = getProducts(order);
        order.setProducts(products);
        return order;
    }

    @Override
    public boolean existById(Long id) {
        return findById(id) != null;
    }

    @Override
    public List<Order> findAll() {
        List<Order> ordersList = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(ORDER_FIND_ALL_SQL_QUERY);
            while (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);
                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    @Override
    public long count() {
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(ORDER_COUNT_SQL_QUERY);
            if (resultSet.next()) {
                return resultSet.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Order> getOrdersFromCustomer(Customer customer) {
        String getOrdersFromCustomerQuery = String.format(ORDER_GET_ORDERS_FROM_CUSTOMER_SQL_QUERY,
                customer.getId());
        List<Order> ordersList = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getOrdersFromCustomerQuery);
            while (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);
                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    private Set<Product> getProducts(Order order) {
        Set<Product> productSet = new LinkedHashSet<>();
        String getProductsQuery = String.format(ORDER_GET_PRODUCTS_SQL_QUERY,
                order.getId());
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getProductsQuery);
            while (resultSet.next()) {
                Product product = createProductFromResultSet(resultSet);
                productSet.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productSet;
    }

    private Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setPrice(resultSet.getBigDecimal("price"));
        return product;
    }

}
