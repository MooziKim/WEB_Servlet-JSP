package com.moozi.shoppingmall.check.cart.service.impl;

import com.moozi.mooziweb.cart.domain.ShoppingCart;
import com.moozi.mooziweb.cart.exception.ShoppingCartAlreadyExistException;
import com.moozi.mooziweb.cart.exception.ShoppingCartNotFoundException;
import com.moozi.mooziweb.cart.repository.ShoppingCartRepository;
import com.moozi.mooziweb.cart.service.ShoppingCartService;
import com.moozi.mooziweb.cart.service.impl.ShoppingCartServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceImplTest {
    ShoppingCartRepository cartRepository = Mockito.mock(ShoppingCartRepository.class);
    ShoppingCartService cartService = new ShoppingCartServiceImpl(cartRepository);

    static ShoppingCart testCart = ShoppingCart.builder()
            .recordId(1)
            .userId("user")
            .productId(1)
            .quantity(3)
            .dateCreated(LocalDateTime.now())
            .build();
    static List<ShoppingCart> cartList = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        cartList.add(testCart);
    }

    @Test
    @DisplayName("save shopping cart")
    void saveShoppingCart() {
        Mockito.when(cartRepository.countByUserIdAndProductId(anyString(), anyInt())).thenReturn(0);
        Mockito.when(cartRepository.save(any())).thenReturn(1);
        cartService.saveShoppingCart(testCart);
        Mockito.verify(cartRepository, Mockito.times(1)).countByUserIdAndProductId(anyString(), anyInt());
        Mockito.verify(cartRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("save shopping cart : cart already exist")
    void saveShoppingCart_cart_already_exist() {
        Mockito.when(cartRepository.countByUserIdAndProductId(anyString(), anyInt())).thenReturn(1);
        Throwable throwable = assertThrows(ShoppingCartAlreadyExistException.class, () -> cartService.saveShoppingCart(testCart));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("get shopping cart")
    void getShoppingCart() {
        Mockito.when(cartRepository.findShoppingCartByUserId(anyString())).thenReturn(cartList);
        cartService.getShoppingCart("user");
        Mockito.verify(cartRepository, Mockito.times(1)).findShoppingCartByUserId(anyString());
    }

    @Test
    @DisplayName("get shopping cart : cart not found")
    void getShoppingCart_cart_not_found() {
        Mockito.when(cartRepository.findShoppingCartByUserId(anyString())).thenReturn(new ArrayList<>());
        Throwable throwable = assertThrows(ShoppingCartNotFoundException.class, () -> cartService.getShoppingCart("admin"));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("update shopping cart")
    void updateShoppingCart() {
        Mockito.when(cartRepository.countByRecordId(anyInt())).thenReturn(1);
        Mockito.when(cartRepository.update(any())).thenReturn(1);
        cartService.updateShoppingCart(testCart);
        Mockito.verify(cartRepository, Mockito.times(1)).countByRecordId(anyInt());
        Mockito.verify(cartRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("update shopping cart : cart not found")
    void updateShoppingCart_cart_not_found() {
        Mockito.when(cartRepository.countByRecordId(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(ShoppingCartNotFoundException.class, () -> cartService.updateShoppingCart(testCart));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("delete shopping cart")
    void deleteShoppingCart() {
        Mockito.when(cartRepository.countByRecordId(anyInt())).thenReturn(1);
        Mockito.when(cartRepository.deleteById(anyInt())).thenReturn(1);
        cartService.deleteShoppingCart(1);
        Mockito.verify(cartRepository, Mockito.times(1)).countByRecordId(anyInt());
        Mockito.verify(cartRepository, Mockito.times(1)).deleteById(anyInt());
    }

    @Test
    @DisplayName("delete shopping cart : cart not found")
    void deleteShoppingCart_cart_not_found() {
        Mockito.when(cartRepository.countByRecordId(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(ShoppingCartNotFoundException.class, () -> cartService.deleteShoppingCart(1));
        log.debug("error: {}", throwable.getMessage());
    }
}
