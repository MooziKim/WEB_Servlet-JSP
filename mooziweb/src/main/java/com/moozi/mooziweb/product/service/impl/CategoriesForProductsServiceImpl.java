package com.moozi.mooziweb.product.service.impl;

import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.exception.CategoriesForProductsAlreadyExistException;
import com.moozi.mooziweb.product.exception.CategoriesForProductsNotFoundException;
import com.moozi.mooziweb.product.exception.CategoryNotFoundException;
import com.moozi.mooziweb.product.exception.ProductNotFoundException;
import com.moozi.mooziweb.product.repository.CategoriesForProductsRepository;
import com.moozi.mooziweb.product.service.CategoriesForProductsService;
import com.moozi.mooziweb.common.page.Page;

import java.util.List;
import java.util.Objects;

public class CategoriesForProductsServiceImpl implements CategoriesForProductsService {
    private CategoriesForProductsRepository categoriesForProductsRepository;

    public CategoriesForProductsServiceImpl(CategoriesForProductsRepository categoriesForProductsRepository) {
        this.categoriesForProductsRepository = categoriesForProductsRepository;
    }
    @Override
    public Page<Product> getProductByCategoryId(int categoryId, int page, int pageSize) {
        Page<Product> productPage = categoriesForProductsRepository.findProductByCategoryId(categoryId, page, pageSize);

        if (Objects.isNull(productPage) || productPage.getContent().isEmpty()) {
            throw new ProductNotFoundException(String.valueOf(categoryId));
        }

        return productPage;
    }

    @Override
    public List<Category> getCategoryByProductId(int productId) {
        List<Category> categoryList = categoriesForProductsRepository.findCategoryByProductId(productId);

        if (Objects.isNull(categoryList) || categoryList.isEmpty()) {
            throw new CategoryNotFoundException(String.valueOf(productId));
        }

        return categoryList;
    }

    @Override
    public void saveCategoriesForProducts(int categoryId, int productId) {
        int result = categoriesForProductsRepository.save(categoryId, productId);

        if (result < 1) {
            throw new CategoriesForProductsAlreadyExistException(String.valueOf(categoryId + " " + productId));
        }
    }

    @Override
    public void deleteCategoriesForProductsByProductId(int productId) {
        int result = categoriesForProductsRepository.deleteByProductId(productId);

        if (result < 1) {
            throw new CategoriesForProductsNotFoundException(String.valueOf(productId));
        }
    }
}
