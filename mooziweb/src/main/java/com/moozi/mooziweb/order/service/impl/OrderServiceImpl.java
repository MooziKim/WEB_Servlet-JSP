package com.moozi.mooziweb.order.service.impl;

import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.order.exception.OrderNotFoundException;
import com.moozi.mooziweb.order.exception.OrderSaveException;
import com.moozi.mooziweb.order.repository.OrderRepository;
import com.moozi.mooziweb.order.service.OrderService;
import com.moozi.mooziweb.common.page.Page;

import java.util.Objects;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public void saveOrder(Order order) {
        int orderId = orderRepository.saveOrder(order);
        order.setOrderId(orderId);
        int result = orderRepository.saveOrderDetail(order);

        if (orderId < 1 || result < 1) {
            throw new OrderSaveException(String.valueOf(orderId));
        }
    }

    @Override
    public Page<Order> getOrderByUserId(String userId, int page, int pageSize) {
        if (orderRepository.countByUserId(userId) < 1) {
            throw new OrderNotFoundException(userId);
        }

        Page<Order> orderPage = orderRepository.findByUserId(userId, page, pageSize);

        if (Objects.isNull(orderPage) || Objects.isNull(orderPage.getContent()) || orderPage.getContent().isEmpty()) {
            throw new OrderNotFoundException(userId);
        }

        return orderPage;
    }

    @Override
    public Order getOrderById(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (Objects.isNull(optionalOrder) || optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(String.valueOf(orderId));
        }

        return optionalOrder.get();
    }
}
