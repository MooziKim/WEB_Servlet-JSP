package com.moozi.mooziweb.controller.admin.category;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.exception.CategoryNotFoundException;
import com.moozi.mooziweb.product.repository.impl.CategoryRepositoryImpl;
import com.moozi.mooziweb.product.service.CategoryService;
import com.moozi.mooziweb.product.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/category_processing.do")
public class AdminCategoryController implements BaseController {
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Category> categoryList = categoryService.getFullCategory();

            req.setAttribute("categoryList", categoryList);
        } catch (CategoryNotFoundException e) {
            log.error("Category not found error: {}", e.getMessage());

            req.setAttribute("categoryList", new ArrayList<Category>());
        }

        return "admin/category/category_processing";
    }
}
