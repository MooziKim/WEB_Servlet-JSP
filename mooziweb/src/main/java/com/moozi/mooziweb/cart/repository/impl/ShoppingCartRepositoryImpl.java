package com.moozi.mooziweb.cart.repository.impl;

import com.moozi.mooziweb.cart.domain.ShoppingCart;
import com.moozi.mooziweb.cart.repository.ShoppingCartRepository;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    @Override
    public int save(ShoppingCart cart) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into ShoppingCart (UserID, ProductID, Quantity, DateCreated) values (?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setString(index++, cart.getUserId());
            psmt.setInt(index++, cart.getProductId());
            psmt.setInt(index++, cart.getQuantity());
            psmt.setTimestamp(index, Timestamp.valueOf(cart.getDateCreated()));

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShoppingCart> findShoppingCartByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select a.RecordID, a.UserID, a.ProductID, a.Quantity as Quantity, a.DateCreated, b.ModelName, b.ProductImage, b.UnitCost, b.Quantity as MaxQuantity from ShoppingCart a inner join Products b on a.ProductID = b.ProductID where UserID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            rs = psmt.executeQuery();
            List<ShoppingCart> cartList = new ArrayList<>();
            while(rs.next()) {
                cartList.add(
                        ShoppingCart.builder()
                                .recordId(rs.getInt("RecordID"))
                                .userId(rs.getString("UserID"))
                                .productId(rs.getInt("ProductID"))
                                .quantity(rs.getInt("Quantity"))
                                .dateCreated(Objects.nonNull(rs.getTimestamp("DateCreated")) ? rs.getTimestamp("DateCreated").toLocalDateTime() : null)
                                .modelName(rs.getString("ModelName"))
                                .productImage(rs.getString("ProductImage"))
                                .unitCost(rs.getInt("UnitCost"))
                                .maxQuantity(rs.getInt("MaxQuantity"))
                                .build()
                );
            }
            return cartList;
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
    public Optional<ShoppingCart> findShoppingCartById(int recordId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from ShoppingCart where RecordID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, recordId);

            rs = psmt.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        ShoppingCart.builder()
                                .recordId(rs.getInt("RecordID"))
                                .userId(rs.getString("UserID"))
                                .productId(rs.getInt("ProductID"))
                                .quantity(rs.getInt("Quantity"))
                                .dateCreated(Objects.nonNull(rs.getTimestamp("DateCreated")) ? rs.getTimestamp("DateCreated").toLocalDateTime() : null)
                                .build()
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
    public int update(ShoppingCart cart) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update ShoppingCart set UserID = ?, ProductID = ?, Quantity = ?, DateCreated = ? where RecordID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setString(index++, cart.getUserId());
            psmt.setInt(index++, cart.getProductId());
            psmt.setInt(index++, cart.getQuantity());
            psmt.setTimestamp(index++, Timestamp.valueOf(cart.getDateCreated()));
            psmt.setInt(index, cart.getRecordId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(int recordId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from ShoppingCart where RecordID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, recordId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserIdAndProductId(String userId, int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as count from ShoppingCart where UserID = ? and ProductID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setInt(2, productId);

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
    public int countByRecordId(int recordId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as count from ShoppingCart where RecordID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, recordId);

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
