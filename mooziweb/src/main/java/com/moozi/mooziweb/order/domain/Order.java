package com.moozi.mooziweb.order.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    int orderId;
    String userId;
    int productId;
    int quantity;
    int unitCost;
    LocalDateTime orderDate;
    LocalDateTime shipDate;

    public Order(int orderId, String userId, int productId, int quantity, int unitCost, LocalDateTime orderDate, LocalDateTime shipDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDateTime shipDate) {
        this.shipDate = shipDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && productId == order.productId && quantity == order.quantity && unitCost == order.unitCost && Objects.equals(userId, order.userId) && Objects.equals(orderDate, order.orderDate) && Objects.equals(shipDate, order.shipDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, productId, quantity, unitCost, orderDate, shipDate);
    }
}
