package com.moozi.shoppingmall.check.product.service.impl;

import com.moozi.mooziweb.common.page.Page;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.exception.ProductAlreadyExistException;
import com.moozi.mooziweb.product.exception.ProductNotFoundException;
import com.moozi.mooziweb.product.repository.ProductRepository;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductService productService = new ProductServiceImpl(productRepository);

    static Product testProduct = new Product(1, "123", "testProduct", "testProductImage", 10000, "test product description", 0, 5);
    static List<Product> productList = new ArrayList<>();
    static Page<Product> productPage;

    @BeforeAll
    static void setUp() {
        productList.add(testProduct);
        productPage = new Page<>(productList, 1);
    }

    @Test
    @DisplayName("getProductById")
    void getProductById() {
        Mockito.when(productRepository.countProductByProductId(anyInt())).thenReturn(1);
        Mockito.when(productRepository.findProductById(anyInt())).thenReturn(Optional.of(testProduct));
        productService.getProductById(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).countProductByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).findProductById(anyInt());
    }

    @Test
    @DisplayName("getProductById : not found")
    void getProductById_not_found() {
        Mockito.when(productRepository.countProductByProductId(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(ProductNotFoundException.class, () -> productService.getProductById(anyInt()));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("saveProduct")
    void saveProduct() {
        Mockito.when(productRepository.countProductByModelNumber(anyString())).thenReturn(0);
        Mockito.when(productRepository.save(any())).thenReturn(1);
        productService.saveProduct(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).countProductByModelNumber(anyString());
        Mockito.verify(productRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("saveProduct : already exist")
    void saveProduct_already_exist() {
        Mockito.when(productRepository.countProductByModelNumber(anyString())).thenReturn(1);
        Throwable throwable = assertThrows(ProductAlreadyExistException.class, () -> productService.saveProduct(testProduct));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("updateProduct")
    void updateProduct() {
        Mockito.when(productRepository.countProductByProductId(anyInt())).thenReturn(1);
        Mockito.when(productRepository.update(any())).thenReturn(1);
        productService.updateProduct(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).countProductByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("updateProduct : not found")
    void updateProduct_not_found() {
        Mockito.when(productRepository.countProductByProductId(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(testProduct));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("deleteProduct")
    void deleteProduct() {
        Mockito.when(productRepository.countProductByProductId(anyInt())).thenReturn(1);
        Mockito.when(productRepository.deleteByProductId(anyInt())).thenReturn(1);
        productService.deleteProductById(testProduct.getProductId());
        Mockito.verify(productRepository, Mockito.times(1)).countProductByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).deleteByProductId(anyInt());
    }

    @Test
    @DisplayName("deleteProduct : not found")
    void deleteProduct_not_found() {
        Mockito.when(productRepository.countProductByProductId(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(testProduct.getProductId()));
        log.debug("error: {}", throwable.getMessage());
    }
}
