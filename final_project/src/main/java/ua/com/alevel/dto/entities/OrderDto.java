package ua.com.alevel.dto.entities;

import lombok.Data;
import ua.com.alevel.dto.entities.ClientDto;
import ua.com.alevel.dto.entities.EmployeeDto;
import ua.com.alevel.entities.Order;
import ua.com.alevel.entities.enums.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Data
public class OrderDto extends BaseDto<Order> {
    private Long id;
    private OrderStatus status;
    private Boolean paid;
    private ClientDto client = new ClientDto();
    private String productType;
    private String brandName;
    private String model;
    private String malfunction;
    private String appearance;
    private String equipment;
    private String acceptorNote;
    private String estimatedPrice;
    private Boolean quickly;
    private Calendar deadline;
    private BigDecimal prepayment;
    private EmployeeDto manager = new EmployeeDto();
    private EmployeeDto doer = new EmployeeDto();
    private String doerNote;
    private String recommendation;

    public Order toOrder() {
        Order order = new Order();
        order.setId(this.id);
        order.setStatus(this.status);
        order.setPaid(this.paid);
        order.setClient(this.client.toClient());
        order.setProductType(this.productType);
        order.setBrandName(this.brandName);
        order.setModel(this.model);
        order.setMalfunction(this.malfunction);
        order.setAppearance(this.appearance);
        order.setEquipment(this.equipment);
        order.setAcceptorNote(this.acceptorNote);
        order.setEstimatedPrice(this.estimatedPrice);
        order.setQuickly(this.quickly);
        order.setDeadline(this.deadline);
        order.setPrepayment(this.prepayment);
        order.setManager(this.manager.toEmployee());
        order.setDoer(this.doer.toEmployee());
        order.setDoerNote(this.doerNote);
        order.setRecommendation(this.recommendation);
        return order;
    }

    public static OrderDto toDto(Order order) {
        if(order == null) {
            return null;
        }
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setPaid(order.getPaid());
        dto.setClient(ClientDto.toDto(order.getClient()));
        dto.setProductType(order.getProductType());
        dto.setBrandName(order.getBrandName());
        dto.setModel(order.getModel());
        dto.setMalfunction(order.getMalfunction());
        dto.setAppearance(order.getAppearance());
        dto.setEquipment(order.getEquipment());
        dto.setAcceptorNote(order.getAcceptorNote());
        dto.setEstimatedPrice(order.getEstimatedPrice());
        dto.setQuickly(order.getQuickly());
        dto.setDeadline(order.getDeadline());
        dto.setPrepayment(order.getPrepayment());
        dto.setManager(EmployeeDto.toDto(order.getManager()));
        dto.setDoer(EmployeeDto.toDto(order.getDoer()));
        dto.setDoerNote(order.getDoerNote());
        dto.setRecommendation(order.getRecommendation());
        return dto;
    }

    public static List<OrderDto> toDtoList(List<Order> orders) {
        return orders.stream().map(OrderDto::toDto).toList();
    }

}
