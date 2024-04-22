package com.moozi.mooziweb.product.repository;

import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.common.page.Page;

import java.util.List;

public interface CategoriesForProductsRepository {
    Page<Product> findProductByCategoryId(int categoryId, int page, int pageSize);
    List<Category> findCategoryByProductId(int productId);
    int save(int categoryId, int productId);
    int deleteByProductId(int productId);
}
