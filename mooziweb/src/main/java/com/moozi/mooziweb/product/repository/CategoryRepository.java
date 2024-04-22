package com.moozi.mooziweb.product.repository;

import com.moozi.mooziweb.product.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    // 전체 조회, 생성, 수정, 삭제
    List<Category> findAll();
    Optional<Category> findCategoryById(int categoryId);
    int save(Category category);
    int update(Category category);
    int deleteByCategoryId(int categoryId);
    int countCategoryByName(String categoryName);
    int countCategoryById(int categoryId);
}
