package com.moozi.mooziweb.cart.service;

import com.moozi.mooziweb.cart.domain.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    // 장바구니 생성, 유저별 장바구니 조회, 장바구니 수정, 장바구니 삭제
    void saveShoppingCart(ShoppingCart cart);
    List<ShoppingCart> getShoppingCart(String userId);
    ShoppingCart getShoppingCartById(int recordId);
    void updateShoppingCart(ShoppingCart cart);
    void deleteShoppingCart(int recordId);
}
