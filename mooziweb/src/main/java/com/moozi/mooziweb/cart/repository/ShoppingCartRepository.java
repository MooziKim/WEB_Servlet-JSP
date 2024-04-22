package com.moozi.mooziweb.cart.repository;

import com.moozi.mooziweb.cart.domain.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository {
    // 장바구니 생성, 회원별 장바구니 조회, 장바구니 수정, 장바구니 삭제, 회원과 물품별 장바구니 카운트
    int save(ShoppingCart cart);
    List<ShoppingCart> findShoppingCartByUserId(String userId);
    Optional<ShoppingCart> findShoppingCartById(int recordId);
    int update(ShoppingCart cart);
    int deleteById(int recordId);
    int countByUserIdAndProductId(String userId, int productId);
    int countByRecordId(int recordId);
}
