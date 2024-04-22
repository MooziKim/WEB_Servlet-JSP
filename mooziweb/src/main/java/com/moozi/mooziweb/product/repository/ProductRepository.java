package com.moozi.mooziweb.product.repository;

import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.common.page.Page;

import java.util.Optional;

public interface ProductRepository {
    // 상품 전체 조회, 카테고리별 상품 조회, 상품 등록, 상품 수정, 상품 삭제
    Page<Product> findAll(int page, int pageSize);
    Page<Product> adminFindAll(int page, int pageSize);
    Page<Product> findProductByKeyword(String modelName, int page, int pageSize);
    Optional<Product> findProductById(int productId);
    int save(Product product);
    int update(Product product);
    int deleteByProductId(int productId);
    int countProductByProductId(int productId);
    int countProductByModelNumber(String modelNumber);
}
