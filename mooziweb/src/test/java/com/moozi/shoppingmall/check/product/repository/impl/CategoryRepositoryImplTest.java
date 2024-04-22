package com.moozi.shoppingmall.check.product.repository.impl;

import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.repository.CategoryRepository;
import com.moozi.mooziweb.product.repository.impl.CategoryRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CategoryRepositoryImplTest {
    CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    Category testCategory1;
    Category testCategory2;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testCategory1 = new Category(1, "category1");
        testCategory2 = new Category(2, "category2");
        categoryRepository.save(testCategory1);
        categoryRepository.save(testCategory2);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("save category")
    void save() {
        Category category = new Category(3, "category3");
        int result = categoryRepository.save(category);
        List<Category> categoryList = categoryRepository.findAll();

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(3, categoryList.size()),
                () -> assertEquals(category.getCategoryName(), categoryList.get(2).getCategoryName())
        );
    }

    @Test
    @DisplayName("update category")
    void update() {
        Category category = categoryRepository.findAll().get(0);
        category.setCategoryName("update category");
        int result = categoryRepository.update(category);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(category, categoryRepository.findAll().get(0))
        );
    }

    @Test
    @DisplayName("delete category")
    void deleteByCategoryId() {
        int categoryId = categoryRepository.findAll().get(0).getCategoryId();
        int result = categoryRepository.deleteByCategoryId(categoryId);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(1, categoryRepository.findAll().size())
        );
    }

    @Test
    @DisplayName("count category by name")
    void countCategoryByName() {
        int result = categoryRepository.countCategoryByName("category1");

        assertAll(
                () -> assertEquals(1, result)
        );
    }

    @Test
    @DisplayName("count category by id")
    void countCategoryById() {
        int id = categoryRepository.findAll().get(0).getCategoryId();
        int result = categoryRepository.countCategoryById(id);

        assertAll(
                () -> assertEquals(1, result)
        );
    }
}
