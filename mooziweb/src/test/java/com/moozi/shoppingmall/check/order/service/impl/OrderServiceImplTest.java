package com.moozi.shoppingmall.check.order.service.impl;

import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.order.exception.OrderAlreadyExistException;
import com.moozi.mooziweb.order.exception.OrderNotFoundException;
import com.moozi.mooziweb.order.repository.OrderRepository;
import com.moozi.mooziweb.order.service.OrderService;
import com.moozi.mooziweb.order.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    OrderService orderService = new OrderServiceImpl(orderRepository);

    static Order testOrder = new Order(
            1,
            "user",
            2,
            3,
            2000,
            LocalDateTime.now(),
            LocalDateTime.now()
    );

    static List<Order> orderList = new ArrayList<>();
    static Page<Order> orderPage;

    @BeforeAll
    static void setUp() {
        orderList.add(testOrder);
        orderPage = new Page<>(orderList, 1);
    }

    @Test
    @DisplayName("saveOrder")
    void saveOrder() {
        Mockito.when(orderRepository.countByUserIdAndProductId(anyString(), anyInt())).thenReturn(0);
        Mockito.when(orderRepository.saveOrder(any())).thenReturn(1);
        Mockito.when(orderRepository.saveOrderDetail(any())).thenReturn(1);
        orderService.saveOrder(testOrder);
        Mockito.verify(orderRepository, Mockito.times(1)).countByUserIdAndProductId(anyString(), anyInt());
        Mockito.verify(orderRepository, Mockito.times(1)).saveOrder(any());
        Mockito.verify(orderRepository, Mockito.times(1)).saveOrderDetail(any());
    }

    @Test
    @DisplayName("saveOrder : already exist order")
    void saveOrder_already_exist_order() {
        Mockito.when(orderRepository.countByUserIdAndProductId(anyString(), anyInt())).thenReturn(1);
        Throwable throwable = assertThrows(OrderAlreadyExistException.class, () -> orderService.saveOrder(testOrder));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("getOrderByUserId")
    void getOrderByUserId() {
        Mockito.when(orderRepository.countByUserId(anyString())).thenReturn(1);
        Mockito.when(orderRepository.findByUserId(anyString(), anyInt(), anyInt())).thenReturn(orderPage);
        orderService.getOrderByUserId("user", 1, 9);
        Mockito.verify(orderRepository, Mockito.times(1)).countByUserId(anyString());
        Mockito.verify(orderRepository, Mockito.times(1)).findByUserId("user", 1, 9);
    }

    @Test
    @DisplayName("getOrderByUserId : not found user")
    void getOrderByUserId_not_found_user() {
        Mockito.when(orderRepository.countByUserId(anyString())).thenReturn(0);
        Throwable throwable = assertThrows(OrderNotFoundException.class, () -> orderService.getOrderByUserId("user", 1, 9));
        log.debug("error: {}", throwable.getMessage());
    }
}
