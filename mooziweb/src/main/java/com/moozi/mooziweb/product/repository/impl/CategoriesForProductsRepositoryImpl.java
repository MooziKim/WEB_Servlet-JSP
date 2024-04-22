package com.moozi.mooziweb.product.repository.impl;

import com.moozi.mooziweb.product.domain.Category;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.CategoriesForProductsRepository;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoriesForProductsRepositoryImpl implements CategoriesForProductsRepository {
    @Override
    public Page<Product> findProductByCategoryId(int categoryId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select a.ProductID, a.ModelNumber, a.ModelName, a.ProductImage, a.UnitCost, a.Description, a.ViewCount, a.Quantity from Products a inner join CategoriesForProducts b on a.ProductID = b.ProductID where categoryID = ? order by ProductID desc limit ?, ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            rs = psmt.executeQuery();
            List<Product> productList = new ArrayList<>();
            while(rs.next()) {
                productList.add(
                        new Product(
                                rs.getInt("ProductID"),
                                rs.getString("ModelNumber"),
                                rs.getString("ModelName"),
                                rs.getString("ProductImage"),
                                rs.getInt("UnitCost"),
                                rs.getString("Description"),
                                rs.getInt("ViewCount"),
                                rs.getInt("Quantity")
                        )
                );
            }

            long total = 0;
            if (!productList.isEmpty()) {
                total = categoriesForProductsTotalCount(connection, categoryId);
            }

            return new Page<>(productList, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (Objects.nonNull(rs) && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Category> findCategoryByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select a.CategoryID, a.CategoryName from Categories a inner join CategoriesForProducts b on a.CategoryID = b.CategoryID where ProductID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            rs = psmt.executeQuery();
            List<Category> categoryList = new ArrayList<>();
            while(rs.next()) {
                categoryList.add(
                        new Category(
                                rs.getInt("CategoryID"),
                                rs.getString("CategoryName")
                        )
                );
            }
            return categoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (Objects.nonNull(rs) && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private long categoriesForProductsTotalCount(Connection connection, int categoryId) {
        String sql = "select count(*) as count from Products a inner join CategoriesForProducts b on a.ProductID = b.ProductID where CategoryID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);

            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (Objects.nonNull(rs) && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    @Override
    public int save(int categoryId, int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into CategoriesForProducts (CategoryID, ProductID) values (?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);
            psmt.setInt(2, productId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from CategoriesForProducts where ProductId = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
