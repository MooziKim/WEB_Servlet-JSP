package com.moozi.mooziweb.controller.cart;

import com.moozi.mooziweb.cart.domain.ShoppingCart;
import com.moozi.mooziweb.cart.repository.impl.ShoppingCartRepositoryImpl;
import com.moozi.mooziweb.cart.service.ShoppingCartService;
import com.moozi.mooziweb.cart.service.impl.ShoppingCartServiceImpl;
import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/update_quantity.do")
public class UpdateCartFormController implements BaseController {
    private static final String REDIRECT_LOGIN = "redirect:/login.do";
    private static final String REDIRECT_SHOPPINGCART = "redirect:/shoppingCart.do";
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recordId = Integer.parseInt(req.getParameter("recordId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        int maxQuantity = Integer.parseInt(req.getParameter("maxQuantity"));

        if (shouldRedirectToLogin(req)) {
            return REDIRECT_LOGIN;
        }

        if (isValidQuantity(quantity, maxQuantity)) {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartById(recordId);
            shoppingCart.setQuantity(quantity);
            shoppingCartService.updateShoppingCart(shoppingCart);
        }

        return REDIRECT_SHOPPINGCART;
    }

    private boolean shouldRedirectToLogin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return Objects.isNull(session) || Objects.isNull(session.getAttribute("user"));
    }

    private boolean isValidQuantity(int quantity, int maxQuantity) {
        return quantity >= 1 && quantity <= maxQuantity;
    }
}
