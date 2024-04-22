package com.moozi.shoppingmall.check.cart.repository.impl;

import com.moozi.mooziweb.cart.domain.ShoppingCart;
import com.moozi.mooziweb.cart.repository.ShoppingCartRepository;
import com.moozi.mooziweb.cart.repository.impl.ShoppingCartRepositoryImpl;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ShoppingCartRepositoryImplTest {
    ShoppingCartRepository cartRepository = new ShoppingCartRepositoryImpl();
    ShoppingCart testShoppingCart;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testShoppingCart = ShoppingCart.builder()
                .recordId(1)
                .userId("user")
                .productId(1)
                .quantity(1)
                .dateCreated(LocalDateTime.now())
                .build();
        cartRepository.save(testShoppingCart);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("save")
    void save() {
        ShoppingCart cart = ShoppingCart.builder()
                .recordId(1)
                .userId("admin")
                .productId(1)
                .quantity(1)
                .dateCreated(LocalDateTime.now())
                .build();
        int result = cartRepository.save(cart);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(1, cartRepository.findShoppingCartByUserId("admin").size())
        );
    }

    @Test
    @DisplayName("findShoppingCartByUserId")
    void findShoppingCartByUserId() {
        List<ShoppingCart> cartList = cartRepository.findShoppingCartByUserId("user");

        assertAll(
                () -> assertNotNull(cartList),
                () -> assertFalse(cartList.isEmpty()),
                () -> assertEquals(1, cartList.size()),
                () -> assertEquals("user", cartList.get(0).getUserId())
        );
    }

    @Test
    @DisplayName("update")
    void update() {
        ShoppingCart cart = cartRepository.findShoppingCartByUserId("user").get(0);
        cart.setQuantity(3);
        int result = cartRepository.update(cart);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(3, cartRepository.findShoppingCartByUserId("user").get(0).getQuantity())
        );
    }

    @Test
    @DisplayName("delete")
    void delete() {
        int recordId = cartRepository.findShoppingCartByUserId("user").get(0).getRecordId();
        int result = cartRepository.deleteById(recordId);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertTrue(cartRepository.findShoppingCartByUserId("user").isEmpty())
        );
    }

    @Test
    @DisplayName("countByUserIdAndProductId")
    void countByUserIdAndProductId() {
        int count = cartRepository.countByUserIdAndProductId("user", 1);

        assertAll(
                () -> assertEquals(1, count)
        );
    }

    @Test
    @DisplayName("countByRecordId")
    void countByRecordId() {
        int recordId = cartRepository.findShoppingCartByUserId("user").get(0).getRecordId();
        int count = cartRepository.countByRecordId(recordId);

        assertAll(
                () -> assertEquals(1, count)
        );
    }
}
