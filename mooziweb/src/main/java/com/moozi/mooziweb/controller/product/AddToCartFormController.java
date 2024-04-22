package com.moozi.mooziweb.controller.product;

import com.moozi.mooziweb.cart.domain.ShoppingCart;
import com.moozi.mooziweb.cart.repository.impl.ShoppingCartRepositoryImpl;
import com.moozi.mooziweb.cart.service.ShoppingCartService;
import com.moozi.mooziweb.cart.service.impl.ShoppingCartServiceImpl;
import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.impl.ProductRepositoryImpl;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.product.service.impl.ProductServiceImpl;
import com.moozi.mooziweb.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/product/addToCart.do")
public class AddToCartFormController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션 확인 후 유저 아이디 가져오기
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("user"))) {
            return "redirect:/login.do";
        }

        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        // req에서 수량과 제품 아이디 가져오기
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        int productId = Integer.parseInt(req.getParameter("productId"));

        // 물품 수량과 비교
        Product product = productService.getProductById(productId);
        if (product.getQuantity() < quantity) {
            return "redirect:/product/detail.do?productId=" + productId;
        }

        // 장바구니 등록
        ShoppingCart record = ShoppingCart.builder()
                .userId(userId)
                .quantity(quantity)
                .productId(productId)
                .dateCreated(LocalDateTime.now())
                .build();
        shoppingCartService.saveShoppingCart(record);

        return "redirect:/product/detail.do?productId=" + productId;
    }
}
