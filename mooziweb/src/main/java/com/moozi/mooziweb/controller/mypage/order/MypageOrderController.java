package com.moozi.mooziweb.controller.mypage.order;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.order.exception.OrderNotFoundException;
import com.moozi.mooziweb.order.repository.impl.OrderRepositoryImpl;
import com.moozi.mooziweb.order.service.OrderService;
import com.moozi.mooziweb.order.service.impl.OrderServiceImpl;
import com.moozi.mooziweb.user.domain.User;
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
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/order.do")
public class MypageOrderController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        int page = getPageParameterOrDefault(req);

        long pageCount = DEFAULT_PAGE;
        List<Order> orderList = new ArrayList<>();
        try {
            Page<Order> orderPage = orderService.getOrderByUserId(userId, page, DEFAULT_PAGE_SIZE);
            orderList = orderPage.getContent();
            pageCount = calculatePageCount(orderPage.getTotalCount());
        } catch (OrderNotFoundException e) {
            log.error("error: {}", e.getMessage());
        }

        req.setAttribute("orderList", orderList);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("currentPage", page);

        return "mypage/orders";
    }

    private int getPageParameterOrDefault(HttpServletRequest req) {
        return Objects.nonNull(req.getParameter("page")) ? Integer.parseInt(req.getParameter("page")) : DEFAULT_PAGE;
    }

    private long calculatePageCount(long totalCount) {
        return (long) Math.ceil((double) totalCount / DEFAULT_PAGE_SIZE);
    }
}
