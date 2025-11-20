package com.spring.ecom_app.model.dto;

import com.spring.ecom_app.model.entity.OrderItem;
import com.spring.ecom_app.model.entity.OrderStatus;
import com.spring.ecom_app.model.entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderResponse {

    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status = OrderStatus.PENDING;
    private List<OrderItemDTO> items = new ArrayList<>();
    private LocalDateTime createAt;

    public OrderResponse() {}

    public OrderResponse(Long id, BigDecimal totalAmount, OrderStatus status, List<OrderItemDTO> items, LocalDateTime createAt) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = items;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
