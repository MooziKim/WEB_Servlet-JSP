package com.moozi.mooziweb.product.repository.impl;

import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.ProductRepository;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Page<Product> findAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products where Quantity > 0 order by ProductID desc limit ?, ?";

        ResultSet rs = null;
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);

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
                total = totalCount(connection);
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
    public Page<Product> adminFindAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products order by ProductID desc limit ?, ?";

        ResultSet rs = null;
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);

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
                total = adminTotalCount(connection);
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
    public Page<Product> findProductByKeyword(String keyword, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        keyword = "%" + keyword + "%";
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products where ModelName like ? limit ?, ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, keyword);
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
                total = totalCountByKeyword(keyword, connection);
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
    public Optional<Product> findProductById(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products where ProductID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            rs = psmt.executeQuery();
            if (rs.next()) {
                return Optional.of(
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
        return Optional.empty();
    }

    @Override
    public int save(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into Products (ModelNumber, ModelName, ProductImage, UnitCost, Description, ViewCount, Quantity) values (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            psmt.setString(index++, product.getModelNumber());
            psmt.setString(index++, product.getModelName());
            psmt.setString(index++, product.getProductImage());
            psmt.setInt(index++, product.getUnitCost());
            psmt.setString(index++, product.getDescription());
            psmt.setInt(index++, product.getViewCount());
            psmt.setInt(index, product.getQuantity());

            psmt.executeUpdate();

            try (ResultSet generatedKeys = psmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int update(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update Products set ModelNumber = ?, ModelName = ?, ProductImage = ?, UnitCost = ?, Description = ?, ViewCount = ?, Quantity = ? where ProductID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setString(index++, product.getModelNumber());
            psmt.setString(index++, product.getModelName());
            psmt.setString(index++, product.getProductImage());
            psmt.setInt(index++, product.getUnitCost());
            psmt.setString(index++, product.getDescription());
            psmt.setInt(index++, product.getViewCount());
            psmt.setInt(index++, product.getQuantity());
            psmt.setInt(index, product.getProductId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from Products where ProductID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countProductByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as count from Products where ProductID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

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
    public int countProductByModelNumber(String modelNumber) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as count from Products where ModelNumber = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, modelNumber);

            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if (Objects.nonNull(rs) && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    private long totalCount(Connection connection) {
        String sql = "select count(*) as count from Products where Quantity > 0";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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

    private long totalCountByKeyword(String keyword, Connection connection) {
        keyword = "%" + keyword + "%";
        String sql = "select count(*) as count from Products where Quantity > 0 and ModelName like ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, keyword);
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

    private long adminTotalCount(Connection connection) {
        String sql = "select count(*) as count from Products";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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
}
