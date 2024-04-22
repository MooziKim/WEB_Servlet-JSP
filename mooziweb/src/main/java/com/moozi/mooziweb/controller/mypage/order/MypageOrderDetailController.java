package com.moozi.mooziweb.controller.mypage.order;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.order.repository.impl.OrderRepositoryImpl;
import com.moozi.mooziweb.order.service.OrderService;
import com.moozi.mooziweb.order.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/orderDetail.do")
public class MypageOrderDetailController implements BaseController {
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        Order order = orderService.getOrderById(orderId);

        req.setAttribute("order", order);

        return "mypage/order_detail";
    }
}
