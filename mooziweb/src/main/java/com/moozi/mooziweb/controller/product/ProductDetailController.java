package com.moozi.mooziweb.controller.product;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.common.util.CookieUtils;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.impl.ProductRepositoryImpl;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.product.service.impl.ProductServiceImpl;
import com.moozi.mooziweb.review.domain.Review;
import com.moozi.mooziweb.review.repository.impl.ReviewRepositoryImpl;
import com.moozi.mooziweb.review.service.ReviewService;
import com.moozi.mooziweb.review.service.impl.ReviewServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/product/detail.do")
public class ProductDetailController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ReviewService reviewService = new ReviewServiceImpl(new ReviewRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        if (Objects.nonNull(req.getParameter("page"))) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.getProductById(productId);
        product.setViewCount(product.getViewCount() + 1);
        productService.updateProduct(product);

        int pageSize = 10;
        Page<Review> reviewPage = new Page<>(new ArrayList<>(), 0);
        try {
            reviewPage = reviewService.getReviewByProductId(productId, page, pageSize);
        } catch (Exception e) {
            log.debug("review not found");
        }

        List<Review> reviewList = reviewPage.getContent();
        long pageCount = (long) Math.ceil((double) reviewPage.getTotalCount() / pageSize);

        req.setAttribute("currentPage", page);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("product", product);
        req.setAttribute("reviewList", reviewList);

        Queue<Product> productQueue = CookieUtils.getQueueFromCookie(req);
        if (productQueue.size() > 4) {
            productQueue.poll();
        }

        if (productQueue.removeIf(p -> p.getModelName().equals(product.getModelName()))) {
            productQueue.offer(product);
        } else {
            productQueue.offer(product);
        }


        CookieUtils.saveQueueToCookie(resp, productQueue);

        DbConnectionThreadLocal.reset();
        return "shop/product/product_detail";
    }
}
