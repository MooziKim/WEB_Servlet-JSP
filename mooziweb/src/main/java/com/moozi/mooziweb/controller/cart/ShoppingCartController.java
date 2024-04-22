package com.moozi.mooziweb.controller.cart;

import com.moozi.mooziweb.cart.domain.ShoppingCart;
import com.moozi.mooziweb.cart.exception.ShoppingCartNotFoundException;
import com.moozi.mooziweb.cart.repository.impl.ShoppingCartRepositoryImpl;
import com.moozi.mooziweb.cart.service.ShoppingCartService;
import com.moozi.mooziweb.cart.service.impl.ShoppingCartServiceImpl;
import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.domain.UserAddress;
import com.moozi.mooziweb.user.exception.UserAddressNotFoundException;
import com.moozi.mooziweb.user.repository.impl.UserAddressRepositoryImpl;
import com.moozi.mooziweb.user.service.UserAddressService;
import com.moozi.mooziweb.user.service.impl.UserAddressServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/shoppingCart.do")
public class ShoppingCartController implements BaseController {
    private static final String REDIRECT_LOGIN = "redirect:/login.do";
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    private final UserAddressService userAddressService = new UserAddressServiceImpl(new UserAddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("user"))) {
            return REDIRECT_LOGIN;
        }

        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        try {
            shoppingCartList = shoppingCartService.getShoppingCart(userId);
        } catch (ShoppingCartNotFoundException e) {
            log.error("Error retrieving shopping cart: {}", e.getMessage());
        }

        List<UserAddress> userAddressList = new ArrayList<>();
        try {
            userAddressList = userAddressService.getUserAddress(userId);
        } catch (UserAddressNotFoundException e) {
            log.error("User address not found error: {}", e.getMessage());
        }

        req.setAttribute("shoppingCartList", shoppingCartList);
        req.setAttribute("userAddressList", userAddressList);

        return "shop/cart/shopping_cart";
    }
}

