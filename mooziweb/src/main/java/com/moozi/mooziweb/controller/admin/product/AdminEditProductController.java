package com.moozi.mooziweb.controller.admin.product;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.impl.CategoriesForProductsRepositoryImpl;
import com.moozi.mooziweb.product.repository.impl.CategoryRepositoryImpl;
import com.moozi.mooziweb.product.repository.impl.ProductRepositoryImpl;
import com.moozi.mooziweb.product.service.CategoriesForProductsService;
import com.moozi.mooziweb.product.service.CategoryService;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.product.service.impl.CategoriesForProductsServiceImpl;
import com.moozi.mooziweb.product.service.impl.CategoryServiceImpl;
import com.moozi.mooziweb.product.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/edit_product.do")
public class AdminEditProductController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final CategoriesForProductsService cfpService = new CategoriesForProductsServiceImpl(new CategoriesForProductsRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("id"));

        Product product = productService.getProductById(productId);
        List<Category> productCategoryList = cfpService.getCategoryByProductId(productId);
        List<Category> categoryList = categoryService.getFullCategory();

        req.setAttribute("product", product);
        req.setAttribute("categoryList", categoryList);
        req.setAttribute("productCategoryList", productCategoryList);

        return "admin/product/edit_product_form";
    }
}
