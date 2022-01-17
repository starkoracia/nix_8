package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jdbc.JdbcConnector;
import ua.com.alevel.persistence.dao.DaoProduct;
import ua.com.alevel.persistence.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ua.com.alevel.persistence.dao.SQLQueryUtil.*;

@Service
public class ProductDao implements DaoProduct {

    JdbcConnector connector;

    public ProductDao(JdbcConnector connector) {
        this.connector = connector;
    }

    @Override
    public void create(Product product) {
        String createQuery = String.format(PRODUCT_CREATE_SQL_QUERY,
                product.getProductName(),
                product.getPrice().toString());
        try (Statement statement = connector.getConnection().createStatement()) {
            statement.executeUpdate(createQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        String updateQuery = String.format(PRODUCT_UPDATE_SQL_QUERY,
                product.getProductName(),
                product.getPrice().toString(),
                product.getId());
        try (Statement statement = connector.getConnection().createStatement()) {
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Product product) {
        String deleteQuery = String.format(PRODUCT_DELETE_SQL_QUERY,
                product.getId());
        try (Statement statement = connector.getConnection().createStatement()) {
            deleteProductFromOrderProductTable(product);
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(Long id) {
        String findByIdQuery = String.format(PRODUCT_FIND_BY_ID_SQL_QUERY,
                id);
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(findByIdQuery);
            if (resultSet.next()) {
                Product product = createProductFromResultSet(resultSet);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteProductFromOrderProductTable(Product product) {
        String deleteProductOrderQuery = String.format(PRODUCT_ORDER_DELETE_SQL_QUERY,
                product.getId());
        try (Statement statement = connector.getConnection().createStatement()) {
            statement.executeUpdate(deleteProductOrderQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setPrice(resultSet.getBigDecimal("price"));
        return product;
    }

    @Override
    public boolean existById(Long id) {
        return findById(id) != null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productsList = new ArrayList<>();
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(PRODUCT_FIND_ALL_SQL_QUERY);
            while (resultSet.next()) {
                Product product = createProductFromResultSet(resultSet);
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    @Override
    public long count() {
        try (Statement statement = connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(PRODUCT_COUNT_SQL_QUERY);
            if (resultSet.next()) {
                return resultSet.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
