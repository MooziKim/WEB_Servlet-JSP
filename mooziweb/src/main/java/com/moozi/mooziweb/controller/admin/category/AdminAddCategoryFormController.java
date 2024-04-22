package com.moozi.mooziweb.controller.admin.category;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.repository.impl.CategoryRepositoryImpl;
import com.moozi.mooziweb.product.service.CategoryService;
import com.moozi.mooziweb.product.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/add_category.do")
public class AdminAddCategoryFormController implements BaseController {
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryName = req.getParameter("categoryName");

        Category category = new Category(0, categoryName);

        categoryService.saveCategory(category);

        DbConnectionThreadLocal.reset();
        return "redirect:/admin/category_processing.do";
    }
}
