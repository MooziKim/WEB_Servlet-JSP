package com.moozi.shoppingmall.check.product.repository.impl;

import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.ProductRepository;
import com.moozi.mooziweb.product.repository.impl.ProductRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProductRepositoryImplTest {
    ProductRepository productRepository = new ProductRepositoryImpl();

    Product testProduct;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testProduct = new Product(1, "123", "testModelName", "testProductImage", 20000, "testDescription", 0, 5);
        productRepository.save(testProduct);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("find product by productId")
    void findProductById() {
        int productId = productRepository.findAll(1, 10).getContent().get(0).getProductId();
        Optional<Product> optionalProduct = productRepository.findProductById(productId);

        assertAll(
                () -> assertNotNull(optionalProduct),
                () -> assertFalse(optionalProduct.isEmpty()),
                () -> assertEquals(testProduct.getModelNumber(), optionalProduct.get().getModelNumber())
        );
    }

    @Test
    @DisplayName("save product")
    void save() {
        Product product = new Product(2, "321", "modelName", "image", 10000, "product description", 0, 5);
        int result = productRepository.save(product);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(product.getModelNumber(), productRepository.findAll(1, 10).getContent().get(0).getModelNumber())
        );
    }

    @Test
    @DisplayName("update product")
    void update() {
        Product product = productRepository.findAll(1, 10).getContent().get(0);
        product.setDescription("new product description");
        int result = productRepository.update(product);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(1, productRepository.findAll(1, 10).getContent().size()),
                () -> assertEquals(product.getDescription(), productRepository.findAll(1, 10).getContent().get(0).getDescription())
        );
    }

    @Test
    @DisplayName("delete product")
    void deleteByProductId() {
        Product product = productRepository.findAll(1, 10).getContent().get(0);
        int productId = product.getProductId();
        int result = productRepository.deleteByProductId(productId);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(0, productRepository.findAll(1, 10).getContent().size())
        );
    }

    @Test
    @DisplayName("count product by product id")
    void countProductByProductId() {
        int productId = productRepository.findAll(1, 10).getContent().get(0).getProductId();
        int count = productRepository.countProductByProductId(productId);

        assertAll(
                () -> assertEquals(1, count)
        );
    }

    @Test
    @DisplayName("count product by model number")
    void countProductByModelNumber() {
        String modelNumber = productRepository.findAll(1, 10).getContent().get(0).getModelNumber();
        int count = productRepository.countProductByModelNumber(modelNumber);

        assertAll(
                () -> assertEquals(1, count)
        );
    }
}
