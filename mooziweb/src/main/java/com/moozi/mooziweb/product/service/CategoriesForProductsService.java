package com.moozi.mooziweb.product.service;

import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.common.page.Page;

import java.util.List;

public interface CategoriesForProductsService {
    // 카테고리별 제품 가져오기, 제품의 카테고리 가져오기, 카테고리 제품 쌍 저장
    Page<Product> getProductByCategoryId(int categoryId, int page, int pageSize);
    List<Category> getCategoryByProductId(int productId);
    void saveCategoriesForProducts(int categoryId, int productId);
    void deleteCategoriesForProductsByProductId(int productId);
}
