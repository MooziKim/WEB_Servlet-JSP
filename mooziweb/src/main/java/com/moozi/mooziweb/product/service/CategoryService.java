package com.moozi.mooziweb.product.service;

import com.moozi.mooziweb.product.domain.Category;

import java.util.List;

public interface CategoryService {
    // 카테고리 전체 가져오기, 카테고리 저장, 카테고리 수정, 카테고리 삭제
    List<Category> getFullCategory();
    Category getCategoryById(int categoryId);
    int saveCategory(Category category);
    int updateCategory(Category category);
    int deleteCategoryById(int categoryId);
}
