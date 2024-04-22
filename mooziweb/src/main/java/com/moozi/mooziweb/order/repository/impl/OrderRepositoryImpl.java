package com.moozi.mooziweb.order.repository.impl;

import com.moozi.mooziweb.order.domain.Order;
import com.moozi.mooziweb.order.repository.OrderRepository;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public int saveOrder(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into Orders (UserID, OrderDate, ShipDate) values (?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            psmt.setString(index++, order.getUserId());
            psmt.setTimestamp(index++, Timestamp.valueOf(order.getOrderDate()));
            psmt.setTimestamp(index, Timestamp.valueOf(order.getShipDate()));

            psmt.executeUpdate();

            try (ResultSet generatedKeys = psmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int saveOrderDetail(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into OrderDetails (OrderID, ProductID, Quantity, UnitCost) values (?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setInt(index++, order.getOrderId());
            psmt.setInt(index++, order.getProductId());
            psmt.setInt(index++, order.getQuantity());
            psmt.setInt(index, order.getUnitCost());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Order> findByUserId(String userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select a.OrderID, a.UserID, b.ProductID, b.Quantity, b.UnitCost, a.OrderDate, a.ShipDate from Orders a inner join OrderDetails b on a.OrderID = b.OrderID where UserID = ? order by a.OrderID desc limit ?, ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            rs = psmt.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (rs.next()) {
                orderList.add(
                        new Order(
                                rs.getInt("OrderID"),
                                rs.getString("UserID"),
                                rs.getInt("ProductID"),
                                rs.getInt("Quantity"),
                                rs.getInt("UnitCost"),
                                Objects.nonNull(rs.getTimestamp("OrderDate")) ? rs.getTimestamp("OrderDate").toLocalDateTime() : null,
                                Objects.nonNull(rs.getTimestamp("ShipDate")) ? rs.getTimestamp("ShipDate").toLocalDateTime() : null
                        )
                );
            }

            long total = 0;

            if (!orderList.isEmpty()) {
                total = totalCount(connection, userId);
            }

            return new Page<>(orderList, total);
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
    public Optional<Order> findById(int orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select a.OrderID, a.UserID, b.ProductID, b.Quantity, b.UnitCost, a.OrderDate, a.ShipDate from Orders a inner join OrderDetails b on a.OrderID = b.OrderID where a.OrderID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, orderId);

            rs = psmt.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new Order(
                                rs.getInt("OrderID"),
                                rs.getString("UserID"),
                                rs.getInt("ProductID"),
                                rs.getInt("Quantity"),
                                rs.getInt("UnitCost"),
                                Objects.nonNull(rs.getTimestamp("OrderDate")) ? rs.getTimestamp("OrderDate").toLocalDateTime() : null,
                                Objects.nonNull(rs.getTimestamp("ShipDate")) ? rs.getTimestamp("ShipDate").toLocalDateTime() : null
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


    private long totalCount(Connection connection, String userId) {
        String sql = "select count(*) as count from Orders a inner join OrderDetails b on a.OrderID = b.OrderID where UserID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

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
    public int countByUserIdAndProductId(String userId, int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as count from Orders a inner join OrderDetails b on a.OrderID = b.OrderID where UserID = ? and ProductID = ?";

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
    public int countByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as count from Orders where UserID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

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
