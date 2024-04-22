package com.moozi.shoppingmall.check.product.service.impl;

import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.exception.CategoryAlreadyExistException;
import com.moozi.mooziweb.product.exception.CategoryNotFoundException;
import com.moozi.mooziweb.product.repository.CategoryRepository;
import com.moozi.mooziweb.product.service.CategoryService;
import com.moozi.mooziweb.product.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
    static Category testCategory = new Category(1, "testCategory");
    static List<Category> categoryList = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        categoryList.add(testCategory);
    }

    @Test
    @DisplayName("saveCategory")
    void saveCategory() {
        Mockito.when(categoryRepository.countCategoryByName(anyString())).thenReturn(0);
        Mockito.when(categoryRepository.save(any())).thenReturn(1);
        categoryService.saveCategory(new Category(1, "testCategory"));
        Mockito.verify(categoryRepository, Mockito.times(1)).countCategoryByName(anyString());
        Mockito.verify(categoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("saveCategory : already exist")
    void saveCategory_already_exist() {
        Mockito.when(categoryRepository.countCategoryByName(anyString())).thenReturn(1);
        Throwable throwable = assertThrows(CategoryAlreadyExistException.class, () -> categoryService.saveCategory(new Category(1, "testCategory")));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("update category")
    void updateCategory() {
        Mockito.when(categoryRepository.countCategoryByName(anyString())).thenReturn(1);
        Mockito.when(categoryRepository.update(any())).thenReturn(1);
        categoryService.updateCategory(new Category(1, "testCategory"));
        Mockito.verify(categoryRepository, Mockito.times(1)).countCategoryByName(anyString());
        Mockito.verify(categoryRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("update category : not found")
    void updateCategory_not_found() {
        Mockito.when(categoryRepository.countCategoryByName(anyString())).thenReturn(0);
        Throwable throwable = assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(new Category(1, "TestCategory")));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("delete category")
    void deleteCategoryById() {
        Mockito.when(categoryRepository.countCategoryById(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.deleteByCategoryId(anyInt())).thenReturn(1);
        categoryService.deleteCategoryById(anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).countCategoryById(anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteByCategoryId(anyInt());
    }

    @Test
    @DisplayName("delete category : not found")
    void deleteCategoryById_not_found() {
        Mockito.when(categoryRepository.countCategoryById(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategoryById(anyInt()));
        log.debug("error: {}", throwable.getMessage());
    }
}
