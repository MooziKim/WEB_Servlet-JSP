package com.moozi.mooziweb.product.service.impl;

import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.exception.*;
import com.moozi.mooziweb.product.repository.ProductRepository;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.common.page.Page;

import java.util.Objects;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Page<Product> getFullProduct(int page, int pageSize) {
        Page<Product> pageProduct = productRepository.findAll(page, pageSize);

        if (Objects.isNull(pageProduct) || pageProduct.getContent().isEmpty()) {
            throw new ProductNotFoundException();
        }

        return pageProduct;
    }

    @Override
    public Page<Product> adminGetFullProduct(int page, int pageSize) {
        Page<Product> pageProduct = productRepository.adminFindAll(page, pageSize);

        if (Objects.isNull(pageProduct) || pageProduct.getContent().isEmpty()) {
            throw new ProductNotFoundException();
        }

        return pageProduct;
    }

    @Override
    public Page<Product> getProductByKeyword(String keyword, int page, int pageSize) {
        Page<Product> pageProduct = productRepository.findProductByKeyword(keyword, page, pageSize);

        if (Objects.isNull(pageProduct) || pageProduct.getContent().isEmpty()) {
            throw new ProductNotFoundException();
        }

        return pageProduct;
    }

    @Override
    public Product getProductById(int productId) {
        if (productRepository.countProductByProductId(productId) < 1) {
            throw new ProductNotFoundException(String.valueOf(productId));
        }

        Optional<Product> optionalProduct = productRepository.findProductById(productId);

        if (Objects.isNull(optionalProduct) || optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(String.valueOf(productId));
        }

        return optionalProduct.get();
    }

    @Override
    public int saveProduct(Product product) {
        if (productRepository.countProductByModelNumber(product.getModelNumber()) > 0) {
            throw new ProductAlreadyExistException(String.valueOf(product.getProductId()));
        }

        int result = productRepository.save(product);

        if (result < 1) {
            throw new ProductSaveException(String.valueOf(product.getProductId()));
        }

        return result;
    }

    @Override
    public int updateProduct(Product product) {
        if (productRepository.countProductByProductId(product.getProductId()) < 1) {
            throw new ProductNotFoundException(String.valueOf(product.getProductId()));
        }

        int result = productRepository.update(product);

        if (result < 1) {
            throw new ProductUpdateException(String.valueOf(product.getProductId()));
        }

        return result;
    }

    @Override
    public int deleteProductById(int productId) {
        if (productRepository.countProductByProductId(productId) < 1) {
            throw new ProductNotFoundException(String.valueOf(productId));
        }

        int result = productRepository.deleteByProductId(productId);

        if (result < 1) {
            throw new ProductDeleteException(String.valueOf(productId));
        }

        return result;
    }
}
