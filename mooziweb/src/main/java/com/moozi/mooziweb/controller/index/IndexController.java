package com.moozi.mooziweb.controller.index;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.common.util.CookieUtils;
import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.exception.CategoryNotFoundException;
import com.moozi.mooziweb.product.repository.impl.CategoriesForProductsRepositoryImpl;
import com.moozi.mooziweb.product.repository.impl.CategoryRepositoryImpl;
import com.moozi.mooziweb.product.repository.impl.ProductRepositoryImpl;
import com.moozi.mooziweb.product.service.CategoriesForProductsService;
import com.moozi.mooziweb.product.service.CategoryService;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.product.service.impl.CategoriesForProductsServiceImpl;
import com.moozi.mooziweb.product.service.impl.CategoryServiceImpl;
import com.moozi.mooziweb.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = {"/index.do"})
public class IndexController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 8;
    private static final String STRING_CATEGORY_ID = "categoryId";
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final CategoriesForProductsService cfpService = new CategoriesForProductsServiceImpl(new CategoriesForProductsRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = getPageParameterOrDefault(req);

        Queue<Product> productQueue = CookieUtils.getQueueFromCookie(req);
        if (!productQueue.isEmpty()) {
            req.setAttribute("productQueue", productQueue);
        }

        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList = categoryService.getFullCategory();
        } catch (CategoryNotFoundException e) {
            log.error("error: {}", e.getMessage());
        }

        long pageCount = DEFAULT_PAGE;
        List<Product> productList = new ArrayList<>();
        Page<Product> productPage = productService.getFullProduct(page, DEFAULT_PAGE_SIZE);
        try {
            String categoryId = req.getParameter(STRING_CATEGORY_ID);
            if (Objects.nonNull(categoryId) && !categoryId.isEmpty()) {
                req.setAttribute(STRING_CATEGORY_ID, categoryId);
                productPage = cfpService.getProductByCategoryId(Integer.parseInt(categoryId), page, DEFAULT_PAGE_SIZE);
            } else if (!categoryList.isEmpty()) {
                req.setAttribute(STRING_CATEGORY_ID, categoryList.get(0).getCategoryId());
            }

            productList = productPage.getContent();
            pageCount = calculatePageCount(productPage.getTotalCount());
        } catch (Exception e) {
            log.error("Index Page error: {}", e.getMessage());

            if (Objects.nonNull(productPage) && Objects.nonNull(productPage.getContent()) && !productPage.getContent().isEmpty()) {
                productList = productPage.getContent();
                pageCount = calculatePageCount(productPage.getTotalCount());
            }
        }

        req.setAttribute("currentPage", page);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("categoryList", categoryList);
        req.setAttribute("productList", productList);

        return "shop/main/index";
    }

    private int getPageParameterOrDefault(HttpServletRequest req) {
        return Objects.nonNull(req.getParameter("page")) ? Integer.parseInt(req.getParameter("page")) : DEFAULT_PAGE;
    }

    private long calculatePageCount(long totalCount) {
        return (long) Math.ceil((double) totalCount / DEFAULT_PAGE_SIZE);
    }
}