package com.moozi.shoppingmall.check.product.service.impl;

import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.exception.CategoriesForProductsAlreadyExistException;
import com.moozi.mooziweb.product.exception.CategoryNotFoundException;
import com.moozi.mooziweb.product.exception.ProductNotFoundException;
import com.moozi.mooziweb.product.repository.CategoriesForProductsRepository;
import com.moozi.mooziweb.product.service.CategoriesForProductsService;
import com.moozi.mooziweb.product.service.impl.CategoriesForProductsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CategoriesForProductsServiceImplTest {
    CategoriesForProductsRepository categoriesForProductsRepository = Mockito.mock(CategoriesForProductsRepository.class);
    CategoriesForProductsService categoriesForProductsService = new CategoriesForProductsServiceImpl(categoriesForProductsRepository);
    static Category category = new Category(1, "testCategory");
    static Product product = new Product(1, "123", "testModelName", "test.png", 10000, "test product", 0, 5);
    static List<Product> productList = new ArrayList<>();
    static List<Category> categoryList = new ArrayList<>();
    static Page<Product> productPage;

    @BeforeAll
    static void setUp() {
        productList.add(product);
        categoryList.add(category);
        productPage = new Page<>(productList, 1);
    }

    @Test
    @DisplayName("getProductByCategoryId")
    void getProductByCategoryId() {
        Mockito.when(categoriesForProductsRepository.findProductByCategoryId(anyInt(), anyInt(), anyInt())).thenReturn(productPage);
        categoriesForProductsService.getProductByCategoryId(1, 1, 10);
        Mockito.verify(categoriesForProductsRepository, Mockito.times(1)).findProductByCategoryId(anyInt(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("getProductByCategoryId : not found product")
    void getProductByCategoryId_not_found_product() {
        Mockito.when(categoriesForProductsRepository.findProductByCategoryId(anyInt(), anyInt(), anyInt())).thenReturn(new Page<>(new ArrayList<>(), 10));
        Throwable throwable = assertThrows(ProductNotFoundException.class, () -> categoriesForProductsService.getProductByCategoryId(0, 1, 10));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("getCategoryByProductId")
    void getCategoryByProductId() {
        Mockito.when(categoriesForProductsRepository.findCategoryByProductId(anyInt())).thenReturn(categoryList);
        categoriesForProductsService.getCategoryByProductId(1);
        Mockito.verify(categoriesForProductsRepository, Mockito.times(1)).findCategoryByProductId(anyInt());
    }

    @Test
    @DisplayName("getCategoryByProductId : not found category")
    void getCategoryByProductId_not_found_category() {
        Mockito.when(categoriesForProductsRepository.findCategoryByProductId(anyInt())).thenReturn(new ArrayList<>());
        Throwable throwable = assertThrows(CategoryNotFoundException.class, () -> categoriesForProductsService.getCategoryByProductId(0));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("saveCategoriesForProducts")
    void saveCategoriesForProducts() {
        Mockito.when(categoriesForProductsRepository.save(anyInt(), anyInt())).thenReturn(1);
        categoriesForProductsService.saveCategoriesForProducts(1, 1);
        Mockito.verify(categoriesForProductsRepository, Mockito.times(1)).save(anyInt(), anyInt());
    }

    @Test
    @DisplayName("saveCategoriesForProducts : already exist")
    void saveCategoriesForProducts_already_exist() {
        Mockito.when(categoriesForProductsRepository.save(anyInt(), anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(CategoriesForProductsAlreadyExistException.class, () -> categoriesForProductsService.saveCategoriesForProducts(1, 1));
        log.debug("error: {}", throwable.getMessage());
    }
}
