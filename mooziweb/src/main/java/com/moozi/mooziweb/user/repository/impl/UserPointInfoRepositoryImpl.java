package com.moozi.mooziweb.user.repository.impl;

import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.exception.PointInfoSaveException;
import com.moozi.mooziweb.user.repository.UserPointInfoRepository;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserPointInfoRepositoryImpl implements UserPointInfoRepository {
    @Override
    public List<UserPointInfo> findById(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from UserPointInfo where UserID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            List<UserPointInfo> pointInfos = new ArrayList<>();
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                pointInfos.add(
                        new UserPointInfo(
                                rs.getString("UserID"),
                                UserPointInfo.PointInfo.valueOf(rs.getString("PointInfo")),
                                rs.getInt("Point"),
                                Objects.nonNull(rs.getTimestamp("PointDate")) ? rs.getTimestamp("PointDate").toLocalDateTime() : null
                        )
                );
            }
            return pointInfos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<UserPointInfo> findById(String userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from UserPointInfo where UserID = ? order by PointDate desc limit ?, ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            List<UserPointInfo> pointInfoList = new ArrayList<>();
            rs = psmt.executeQuery();
            while (rs.next()) {
                pointInfoList.add(
                        new UserPointInfo(
                                rs.getString("UserID"),
                                UserPointInfo.PointInfo.valueOf(rs.getString("PointInfo")),
                                rs.getInt("Point"),
                                Objects.nonNull(rs.getTimestamp("PointDate")) ? rs.getTimestamp("PointDate").toLocalDateTime() : null
                        )
                );
            }

            long total = 0;

            if (!pointInfoList.isEmpty()) {
                total = totalCount(connection, userId);
            }

            return new Page<>(pointInfoList, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (Objects.nonNull(rs) && rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int save(UserPointInfo info) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into UserPointInfo (UserID, PointInfo, Point, PointDate) values (?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setString(index++, info.getUserId());
            psmt.setString(index++, info.getPointInfo().name());
            psmt.setInt(index++, info.getPoint());
            psmt.setTimestamp(index, Timestamp.valueOf(info.getPointDate()));

            int result = psmt.executeUpdate();
            if (result < 1) {
                throw new PointInfoSaveException();
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long totalCount(Connection connection, String userId) {
        String sql = "select count(*) as count from UserPointInfo where UserID = ?";

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
                if (Objects.nonNull(rs) || !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
}
