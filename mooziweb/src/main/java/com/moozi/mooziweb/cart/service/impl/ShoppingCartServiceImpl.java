package com.moozi.mooziweb.cart.service.impl;

import com.moozi.mooziweb.cart.domain.ShoppingCart;
import com.moozi.mooziweb.cart.exception.*;
import com.moozi.mooziweb.cart.repository.ShoppingCartRepository;
import com.moozi.mooziweb.cart.service.ShoppingCartService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
    @Override
    public void saveShoppingCart(ShoppingCart cart) {
        if (shoppingCartRepository.countByUserIdAndProductId(cart.getUserId(), cart.getProductId()) > 0) {
            throw new ShoppingCartAlreadyExistException(cart.getUserId() + " " + cart.getProductId());
        }

        int result = shoppingCartRepository.save(cart);

        if (result < 1) {
            throw new ShoppingCartSaveException(cart.getUserId());
        }
    }

    @Override
    public List<ShoppingCart> getShoppingCart(String userId) {
        List<ShoppingCart> cartList = shoppingCartRepository.findShoppingCartByUserId(userId);

        if (Objects.isNull(cartList) || cartList.isEmpty()) {
            throw new ShoppingCartNotFoundException(userId);
        }

        return cartList;
    }

    @Override
    public ShoppingCart getShoppingCartById(int recordId) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findShoppingCartById(recordId);

        if (Objects.isNull(optionalShoppingCart) || optionalShoppingCart.isEmpty()) {
            throw new ShoppingCartNotFoundException(String.valueOf(recordId));
        }

        return optionalShoppingCart.get();
    }

    @Override
    public void updateShoppingCart(ShoppingCart cart) {
        if (shoppingCartRepository.countByRecordId(cart.getRecordId()) < 1) {
            throw new ShoppingCartNotFoundException(String.valueOf(cart.getRecordId()));
        }

        int result = shoppingCartRepository.update(cart);

        if (result < 1) {
            throw new ShoppingCartUpdateException(cart.getUserId());
        }
    }

    @Override
    public void deleteShoppingCart(int recordId) {
        if (shoppingCartRepository.countByRecordId(recordId) < 1) {
            throw new ShoppingCartNotFoundException(String.valueOf(recordId));
        }

        int result = shoppingCartRepository.deleteById(recordId);

        if (result < 1) {
            throw new ShoppingCartDeleteException(String.valueOf(recordId));
        }
    }
}
