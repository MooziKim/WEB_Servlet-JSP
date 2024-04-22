package com.moozi.mooziweb.controller.admin.product;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.exception.ProductNotFoundException;
import com.moozi.mooziweb.product.repository.impl.ProductRepositoryImpl;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/index.do")
public class AdminIndexController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = getPageParameterOrDefault(req);

        long pageCount = DEFAULT_PAGE;
        List<Product> productList = new ArrayList<>();
        try {
            Page<Product> productPage = productService.adminGetFullProduct(page, DEFAULT_PAGE_SIZE);
            productList = productPage.getContent();
            pageCount = calculatePageCount(productPage.getTotalCount());
        } catch (ProductNotFoundException e) {
            log.error("Product not found error: {}", e.getMessage());
        }
        req.setAttribute("productList", productList);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("currentPage", page);

        return "admin/main/index";
    }

    private int getPageParameterOrDefault(HttpServletRequest req) {
        return Objects.nonNull(req.getParameter("page")) ? Integer.parseInt(req.getParameter("page")) : DEFAULT_PAGE;
    }

    private long calculatePageCount(long totalCount) {
        return (long) Math.ceil((double) totalCount / DEFAULT_PAGE_SIZE);
    }
}
