package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.persistence.dao.impl.CustomerDao;
import ua.com.alevel.persistence.dao.impl.OrderDao;
import ua.com.alevel.persistence.dao.impl.ProductDao;
import ua.com.alevel.persistence.entity.Customer;
import ua.com.alevel.persistence.entity.Order;
import ua.com.alevel.persistence.entity.Product;
import ua.com.alevel.util.page.OrderPage;
import ua.com.alevel.util.page.Page;
import ua.com.alevel.util.page.ProductPage;

import java.util.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    OrderDao orderDao;
    CustomerDao customerDao;
    ProductDao productDao;
    String errorMessage = "Укажите клиента и хотя бы один товар";

    public OrderController(OrderDao orderDao, CustomerDao customerDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @GetMapping()
    public String getOrders(Model model, @RequestParam Map<String, String> allRequestParams) {
        prepareOrdersModel(model, allRequestParams);
        return "orders";
    }

    @PostMapping()
    public String getOrdersUpdateButton(Model model, @RequestParam Map<String, String> allRequestParams,
                                        @ModelAttribute("number_of_rows") int numberOfRows) {
        prepareOrdersModel(model, allRequestParams);
        return "orders";
    }

    @PostMapping("/create_order")
    public String createOrder(Model model,
                              @RequestParam Map<String, String> allRequestParams,
                              @RequestParam(value = "products", required = false) Long[] products) {
        String customerId = allRequestParams.get("customer");
        if (customerId != null && products != null) {
            Order order = createOrderFromQueryData(products, customerId);
            orderDao.create(order);
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }

        prepareOrdersModel(model, allRequestParams);
        return "orders";
    }

    @PostMapping("/delete_order/{id}")
    public String deleteOrder(@PathVariable Long id, Model model,
                              @RequestParam Map<String, String> allRequestParams) {
        Order order = new Order();
        order.setId(id);
        orderDao.delete(order);

        prepareOrdersModel(model, allRequestParams);
        return "orders";
    }

    @GetMapping("/update_order/{id}")
    public String redirectToUpdateOrderPage(@PathVariable Long id, Model model) {
        prepareUpdateCustomerModel(model, id);
        return "update_order";
    }

    @PostMapping("/update_order/{id}")
    public String updateOrder(@PathVariable Long id, Model model,
                              @RequestParam(value = "products", required = false) Long[] products,
                              @RequestParam Map<String, String> allRequestParams) {
        String customerId = allRequestParams.get("customer");
        if (customerId != null && products != null) {
            Order order = createOrderFromQueryData(products, customerId);
            order.setId(id);
            orderDao.update(order);
            return "redirect:/orders";
        }

        model.addAttribute("errorMessage", errorMessage);
        prepareUpdateCustomerModel(model, id);
        return "update_order";
    }

    @GetMapping("/{id}/products")
    public String orderGetProducts(@PathVariable Long id, Model model,
                                   @RequestParam Map<String, String> allRequestParams) {
        prepareOrderProductsModel(id, model, allRequestParams);
        return "order_products";
    }

    @PostMapping("/{id}/products")
    public String orderGetProductsUpdateButton(@PathVariable Long id, Model model,
                                               @RequestParam Map<String, String> allRequestParams) {
        prepareOrderProductsModel(id, model, allRequestParams);
        return "order_products";
    }

    private void prepareOrderProductsModel(Long id, Model model, Map<String, String> allRequestParams) {
        Order order = orderDao.findById(id);
        Set<Product> products = order.getProducts();
        ProductPage productPage = new ProductPage(products.stream().toList());

        String numberOfRows = allRequestParams.get("number_of_rows");
        if(numberOfRows != null) {
            try {
                productPage.setNumberOfRows(Integer.parseInt(numberOfRows));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String pageNumber = allRequestParams.get("page_number");
        if(pageNumber != null) {
            try {
                productPage.setPageNumber(Integer.parseInt(pageNumber));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String sorting = allRequestParams.get("sorting");
        String asc = allRequestParams.get("asc");
        boolean ascBool;
        if(sorting != null) {
            if(asc != null) {
                ascBool = Boolean.parseBoolean(asc);
            } else {
                ascBool = true;
            }
            productPage.sort(sorting, ascBool);
            model.addAttribute("asc", ascBool);
            model.addAttribute("sorting", sorting);
        }

        model.addAttribute("product", new Product());
        model.addAttribute("totalRows", productPage.getTotalRows());
        model.addAttribute("productPage", productPage);
        model.addAttribute("number_of_rows", productPage.getNumberOfRows());
        model.addAttribute("orderId", id);
    }


    private void prepareUpdateCustomerModel(Model model, Long id) {
        Order order = orderDao.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("customers", customerDao.findAll());
    }

    private Order createOrderFromQueryData(Long[] products, String customerId) {
        Customer customer = customerDao.findById(Long.parseLong(customerId));
        List<Product> productList = new ArrayList<>();
        for (Long productId : products) {
            productList.add(productDao.findById(productId));
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(new HashSet<>(productList));
        return order;
    }

    private void prepareOrdersModel(Model model, Map<String, String> allRequestParams) {
        List<Order> orders = orderDao.findAll();
        Page orderPage = new OrderPage(orders);

        String numberOfRows = allRequestParams.get("number_of_rows");
        if (numberOfRows != null) {
            try {
                orderPage.setNumberOfRows(Integer.parseInt(numberOfRows));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String pageNumber = allRequestParams.get("page_number");
        if (pageNumber != null) {
            try {
                orderPage.setPageNumber(Integer.parseInt(pageNumber));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String sorting = allRequestParams.get("sorting");
        String asc = allRequestParams.get("asc");
        boolean ascBool;
        if (sorting != null) {
            if (asc != null) {
                ascBool = Boolean.parseBoolean(asc);
            } else {
                ascBool = true;
            }
            orderPage.sort(sorting, ascBool);
            model.addAttribute("asc", ascBool);
            model.addAttribute("sorting", sorting);
        }
        model.addAttribute("order", new Order());
        model.addAttribute("totalRows", orderPage.getTotalRows());
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("number_of_rows", orderPage.getNumberOfRows());
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("customers", customerDao.findAll());
    }

}
