package com.moozi.shoppingmall.check.product.repository.impl;

import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.CategoriesForProductsRepository;
import com.moozi.mooziweb.product.repository.impl.CategoriesForProductsRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CategoriesForProductsRepositoryImplTest {
    CategoriesForProductsRepository categoriesForProductsRepository = new CategoriesForProductsRepositoryImpl();

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        categoriesForProductsRepository.save(1, 1);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("find product by category id")
    void findProductByCategoryId() {
        Page<Product> productPage = categoriesForProductsRepository.findProductByCategoryId(1, 1, 10);
        List<Product> productList = productPage.getContent();

        assertAll(
                () -> assertNotNull(productPage),
                () -> assertNotNull(productList),
                () -> assertFalse(productList.isEmpty()),
                () -> assertEquals(1,productList.get(0).getProductId())

        );
    }

    @Test
    @DisplayName("find product by category id : not found category id")
    void findProductByCategoryId_not_found_category_id() {
        Page<Product> productPage = categoriesForProductsRepository.findProductByCategoryId(0, 1, 10);

        assertAll(
                () -> assertTrue(productPage.getContent().isEmpty()),
                () -> assertEquals(0, productPage.getTotalCount())
        );
    }

    @Test
    @DisplayName("find category by product id")
    void findCategoryByProductId() {
        List<Category> categoryList = categoriesForProductsRepository.findCategoryByProductId(1);

        assertAll(
                () -> assertNotNull(categoryList),
                () -> assertEquals(1, categoryList.size()),
                () -> assertEquals(1, categoryList.get(0).getCategoryId())
        );
    }

    @Test
    @DisplayName("find category by product id : not found product id")
    void findCategoryByProductId_not_found_product_id() {
        List<Category> categoryList = categoriesForProductsRepository.findCategoryByProductId(0);

        assertAll(
                () -> assertTrue(categoryList.isEmpty())
        );
    }

    @Test
    @DisplayName("save")
    void save() {
        int result = categoriesForProductsRepository.save(2, 1);
        List<Category> categoryList = categoriesForProductsRepository.findCategoryByProductId(1);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertNotNull(categoryList),
                () -> assertFalse(categoryList.isEmpty()),
                () -> assertEquals(2, categoryList.size())
        );
    }
}
