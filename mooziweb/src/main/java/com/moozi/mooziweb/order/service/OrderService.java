package com.moozi.mooziweb.order.service;

import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.common.page.Page;

public interface OrderService {
    // 주문 생성, 유저별 주문 조회
    void saveOrder(Order order);
    Page<Order> getOrderByUserId(String userId, int page, int pageSize);
    Order getOrderById(int orderId);
}
