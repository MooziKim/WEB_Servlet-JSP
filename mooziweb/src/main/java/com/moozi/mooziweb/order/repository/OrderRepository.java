package com.moozi.mooziweb.order.repository;

import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.common.page.Page;

import java.util.Optional;

public interface OrderRepository {
    // 주문 생성, 주문 상세 생성, 유저별 주문 조회
    int saveOrder(Order order);
    int saveOrderDetail(Order order);
    Page<Order> findByUserId(String userId, int page, int pageSize);
    Optional<Order> findById(int orderId);
    int countByUserIdAndProductId(String userId, int productId);
    int countByUserId(String user);
}
