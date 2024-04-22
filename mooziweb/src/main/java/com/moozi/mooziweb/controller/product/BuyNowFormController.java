package com.moozi.mooziweb.controller.product;

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
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/product/buyNow.do")
public class BuyNowFormController implements BaseController {
    private final UserAddressService userAddressService = new UserAddressServiceImpl(new UserAddressRepositoryImpl());
    private final UserPointInfoService pointInfoService = new UserPointInfoServiceImpl(new UserPointInfoRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // req에서 제품아이디, 수량 뽑기
        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        // 세션에서 유저 확인
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("user"))) {
            return "redirect:/login.do";
        }

        // 유저 주소가 있는지 확인
        User user = (User) session.getAttribute("user");
        if (!userAddressService.isUserAddress(user.getUserId())) {
            // 주소를 입력하는 페이지로 갈 수 있게 변경
            return "redirect:/mypage/addAddress.do";
        }

        // 제품 가져와서 수량 체크
        Product product = productService.getProductById(productId);
        if (product.getQuantity() < quantity) {
            return "redirect:/product/detail.do?productId=" + productId;
        }

        // 유저 포인트 와 제품 가격 비교
        int userPoint = 0;
        List<UserPointInfo> pointInfoList = pointInfoService.getUserPointInfo(user.getUserId());
        for (UserPointInfo pointInfo : pointInfoList) {
            if (pointInfo.getPointInfo().equals(UserPointInfo.PointInfo.SAVE)) {
                userPoint += pointInfo.getPoint();
            } else {
                userPoint -= pointInfo.getPoint();
            }
        }

        int cost = product.getUnitCost() * quantity;
        if (userPoint < cost) {
            // 나중에 포인트 충전페이지 같은 것을 만들면 좋을 것 같다
            return "redirect:/product/detail.do?productId=" + productId;
        }

        // 제품 수량 감소 업데이트
        product.setQuantity(product.getQuantity() - quantity);
        productService.updateProduct(product);

        // 포인트 정보 기록
        UserPointInfo userPointInfo = new UserPointInfo(user.getUserId(), UserPointInfo.PointInfo.USE, cost, LocalDateTime.now());
        pointInfoService.saveUserPointInfo(userPointInfo);

        // 포인트 적립 실행
        PointChannelRequest pointChannelRequest = new PointChannelRequest(user.getUserId(), cost);
        RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");
        try {
            requestChannel.addRequest(pointChannelRequest);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 주문 기록
        Order order = new Order(0, user.getUserId(), productId, quantity, product.getUnitCost(), LocalDateTime.now(), LocalDateTime.now());
        orderService.saveOrder(order);

        return "redirect:/product/detail.do?productId=" + productId;
    }
}
