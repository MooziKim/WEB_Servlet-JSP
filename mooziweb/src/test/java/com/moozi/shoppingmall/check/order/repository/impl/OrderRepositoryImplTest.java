package com.moozi.shoppingmall.check.order.repository.impl;

import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.order.repository.OrderRepository;
import com.moozi.mooziweb.order.repository.impl.OrderRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class OrderRepositoryImplTest {
    OrderRepository orderRepository = new OrderRepositoryImpl();
    Order testOrder;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testOrder = new Order(
                1,
                "user",
                2,
                3,
                10000,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        int orderId = orderRepository.saveOrder(testOrder);
        testOrder.setOrderId(orderId);
        orderRepository.saveOrderDetail(testOrder);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("saveOrderAndOrderDetail")
    void saveOrder() {
        Order order = new Order(
                2,
                "user",
                3,
                3,
                1000,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        int orderId = orderRepository.saveOrder(testOrder);
        testOrder.setOrderId(orderId);
        int result = orderRepository.saveOrderDetail(testOrder);

        assertAll(
                () -> assertTrue(result > 0),
                () -> assertEquals(2, orderRepository.findByUserId("user", 1, 9).getContent().size())
        );
    }

    @Test
    @DisplayName("findByUserId")
    void findByUserId() {
        Page<Order> orderPage = orderRepository.findByUserId("user", 1, 10);

        assertAll(
                () -> assertNotNull(orderPage),
                () -> assertNotNull(orderPage.getContent()),
                () -> assertFalse(orderPage.getContent().isEmpty()),
                () -> assertEquals(1, orderPage.getContent().size())
        );
    }

    @Test
    @DisplayName("countByUserIdAndProductId")
    void countByUserIdAndProductId() {
        int count = orderRepository.countByUserIdAndProductId("user", 2);

        assertAll(
                () -> assertEquals(1, count)
        );
    }

    @Test
    @DisplayName("countByUserId")
    void countByUserId() {
        int count = orderRepository.countByUserId("user");

        assertAll(
                () -> assertEquals(1, count)
        );
    }
}
