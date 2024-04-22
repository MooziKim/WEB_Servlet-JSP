package com.moozi.mooziweb.controller.cart;

import com.moozi.mooziweb.cart.domain.ShoppingCart;
import com.moozi.mooziweb.cart.repository.impl.ShoppingCartRepositoryImpl;
import com.moozi.mooziweb.cart.service.ShoppingCartService;
import com.moozi.mooziweb.cart.service.impl.ShoppingCartServiceImpl;
import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.order.repository.impl.OrderRepositoryImpl;
import com.moozi.mooziweb.order.service.OrderService;
import com.moozi.mooziweb.order.service.impl.OrderServiceImpl;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.impl.ProductRepositoryImpl;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.product.service.impl.ProductServiceImpl;
import com.moozi.mooziweb.thread.channel.RequestChannel;
import com.moozi.mooziweb.thread.request.impl.PointChannelRequest;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.repository.impl.UserAddressRepositoryImpl;
import com.moozi.mooziweb.user.repository.impl.UserPointInfoRepositoryImpl;
import com.moozi.mooziweb.user.service.UserAddressService;
import com.moozi.mooziweb.user.service.UserPointInfoService;
import com.moozi.mooziweb.user.service.impl.UserAddressServiceImpl;
import com.moozi.mooziweb.user.service.impl.UserPointInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/payment.do")
public class PaymentController implements BaseController {
    private static final String REDIRECT_SHOPPING_CART = "redirect:/shoppingCart.do";
    private static final String REDIRECT_ADD_ADDRESS = "redirect:/mypage/addAddress.do";
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    private final UserAddressService userAddressService = new UserAddressServiceImpl(new UserAddressRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final UserPointInfoService pointInfoService = new UserPointInfoServiceImpl(new UserPointInfoRepositoryImpl());
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        List<ShoppingCart> shoppingCartList = shoppingCartService.getShoppingCart(userId);

        for (ShoppingCart record : shoppingCartList) {
            if (!userAddressService.isUserAddress(user.getUserId())) {
                return REDIRECT_ADD_ADDRESS;
            }

            int productId = record.getProductId();
            int quantity = record.getQuantity();

            Product product = productService.getProductById(productId);

            if (product.getQuantity() < quantity) {
                return REDIRECT_SHOPPING_CART;
            }

            int cost = product.getUnitCost() * quantity;

            if (!canUserAfford(userId, cost)) {
                return REDIRECT_SHOPPING_CART;
            }

            processPayment(req, userId, cost);
            saveOrder(user.getUserId(), productId, quantity, product.getUnitCost());

            shoppingCartService.deleteShoppingCart(record.getRecordId());
        }

        return REDIRECT_SHOPPING_CART;
    }

    private boolean canUserAfford(String userId, int cost) {
        int userPoint = calculateUserPoints(userId);
        return userPoint >= cost;
    }

    private int calculateUserPoints(String userId) {
        List<UserPointInfo> pointInfoList = pointInfoService.getUserPointInfo(userId);
        int userPoint = 0;

        for (UserPointInfo pointInfo : pointInfoList) {
            if (pointInfo.getPointInfo().equals(UserPointInfo.PointInfo.SAVE)) {
                userPoint += pointInfo.getPoint();
            } else {
                userPoint -= pointInfo.getPoint();
            }
        }

        return userPoint;
    }

    private void processPayment(HttpServletRequest req,String userId, int cost) {
        UserPointInfo userPointInfo = new UserPointInfo(userId, UserPointInfo.PointInfo.USE, cost, LocalDateTime.now());
        pointInfoService.saveUserPointInfo(userPointInfo);

        PointChannelRequest pointChannelRequest = new PointChannelRequest(userId, cost);
        RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");

        try {
            requestChannel.addRequest(pointChannelRequest);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveOrder(String userId, int productId, int quantity, int unitCost) {
        Order order = new Order(0, userId, productId, quantity, unitCost, LocalDateTime.now(), LocalDateTime.now());
        orderService.saveOrder(order);
    }
}
