package com.moozi.mooziweb.product.service.impl;

import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.exception.CategoryAlreadyExistException;
import com.moozi.mooziweb.product.exception.CategoryNotFoundException;
import com.moozi.mooziweb.product.exception.CategorySaveException;
import com.moozi.mooziweb.product.exception.CategoryUpdateException;
import com.moozi.mooziweb.product.repository.CategoryRepository;
import com.moozi.mooziweb.product.service.CategoryService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Category> getFullCategory() {
        List<Category> categoryList = categoryRepository.findAll();

        if (Objects.isNull(categoryList) || categoryList.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        return categoryList;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        if (categoryRepository.countCategoryById(categoryId) < 1) {
            throw new CategoryNotFoundException(String.valueOf(categoryId));
        }

        Optional<Category> optionalCategory = categoryRepository.findCategoryById(categoryId);

        if (Objects.isNull(optionalCategory) || optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(String.valueOf(categoryId));
        }

        return optionalCategory.get();
    }

    @Override
    public int saveCategory(Category category) {
        if (categoryRepository.countCategoryByName(category.getCategoryName()) > 0) {
            throw new CategoryAlreadyExistException(category.getCategoryName());
        }

        int result = categoryRepository.save(category);

        if (result < 1) {
            throw new CategorySaveException();
        }

        return result;
    }

    @Override
    public int updateCategory(Category category) {
        if (categoryRepository.countCategoryById(category.getCategoryId()) < 1) {
            throw new CategoryNotFoundException();
        }

        int result = categoryRepository.update(category);

        if (result < 1) {
            throw new CategoryUpdateException();
        }

        return result;
    }

    @Override
    public int deleteCategoryById(int categoryId) {
        if (categoryRepository.countCategoryById(categoryId) < 1) {
            throw new CategoryNotFoundException();
        }

        int result = categoryRepository.deleteByCategoryId(categoryId);

        if (result < 1) {
            throw new CategoryNotFoundException();
        }

        return result;
    }
}
