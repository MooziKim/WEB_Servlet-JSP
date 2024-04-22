package com.moozi.mooziweb.product.service;

import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.common.page.Page;

public interface ProductService {
    // 상품 전체 조회, 상품 카테고리별 조회, 상품 저장, 상품 수정, 상품 삭제
    Page<Product> getFullProduct(int page, int pageSize);
    Page<Product> adminGetFullProduct(int page, int pageSize);
    Page<Product> getProductByKeyword(String keyword, int page, int pageSize);
    Product getProductById(int productId);
    int saveProduct(Product product);
    int updateProduct(Product product);
    int deleteProductById(int productId);
}
