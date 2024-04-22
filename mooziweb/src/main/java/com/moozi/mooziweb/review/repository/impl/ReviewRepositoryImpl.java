package com.moozi.mooziweb.review.repository.impl;

import com.moozi.mooziweb.review.domain.Review;
import com.moozi.mooziweb.review.repository.ReviewRepository;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewRepositoryImpl implements ReviewRepository {
    @Override
    public Page<Review> findReviewByProductId(int productId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Reviews where ProductID = ? limit ?, ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            rs = psmt.executeQuery();
            List<Review> reviewList = new ArrayList<>();
            while(rs.next()) {
                reviewList.add(
                        Review.builder()
                                .reviewId(rs.getInt("ReviewID"))
                                .productId(rs.getInt("ProductID"))
                                .userId(rs.getString("UserID"))
                                .rating(rs.getInt("Rating"))
                                .comments(rs.getString("Comments"))
                                .build()
                );
            }

            long total = 0;

            if (!reviewList.isEmpty()) {
                total = totalCount(connection, productId);
            }

            return new Page<>(reviewList, total);
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
    public int save(Review review) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into Reviews (ProductID, UserID, Rating, Comments) values (?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setInt(index++, review.getProductId());
            psmt.setString(index++, review.getUserId());
            psmt.setInt(index++, review.getRating());
            psmt.setString(index, review.getComments());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Review review, String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update Reviews set ProductID = ?, UserID = ?, Rating = ?, Comments = ? where ReviewID = ? and UserID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setInt(index++, review.getProductId());
            psmt.setString(index++, review.getUserId());
            psmt.setInt(index++, review.getRating());
            psmt.setString(index++, review.getComments());
            psmt.setInt(index++, review.getReviewId());
            psmt.setString(index, userId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(int reviewId, String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from Reviews where ReviewID = ? and UserID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, reviewId);
            psmt.setString(2, userId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByReviewId(int reviewId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as count from Reviews where ReviewID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, reviewId);

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

    private long totalCount(Connection connection, int productId) {
        String sql = "select count(*) as count from Reviews where ProductId = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1,productId);

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
