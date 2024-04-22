package com.moozi.mooziweb.user.repository.impl;

import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.exception.UserDeleteException;
import com.moozi.mooziweb.user.exception.UserSaveException;
import com.moozi.mooziweb.user.repository.UserRepository;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.common.page.Page;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Page<User> findAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Users order by UserID desc limit ?, ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);

            rs = psmt.executeQuery();

            List<User> userList = new ArrayList<>();
            while(rs.next()) {
                userList.add(
                        new User(
                                rs.getString("UserID"),
                                rs.getString("UserName"),
                                rs.getString("UserPassword"),
                                rs.getString("UserBirth"),
                                User.Auth.valueOf(rs.getString("UserAuth")),
                                Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                                Objects.nonNull(rs.getTimestamp("LatestLoginAt")) ? rs.getTimestamp("LatestLoginAt").toLocalDateTime() : null
                        )
                );
            }

            long total = 0;

            if (!userList.isEmpty()) {
                total = totalCount(connection);
            }

            return new Page<>(userList, total);
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
    public Page<User> findByUserAuth(String userAuth, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Users where userAuth = ? order by CreatedAt desc limit ?, ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userAuth);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            rs = psmt.executeQuery();

            List<User> userList = new ArrayList<>();
            while(rs.next()) {
                userList.add(
                        new User(
                                rs.getString("UserID"),
                                rs.getString("UserName"),
                                rs.getString("UserPassword"),
                                rs.getString("UserBirth"),
                                User.Auth.valueOf(rs.getString("UserAuth")),
                                Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                                Objects.nonNull(rs.getTimestamp("LatestLoginAt")) ? rs.getTimestamp("LatestLoginAt").toLocalDateTime() : null
                        )
                );
            }

            long total = 0;

            if (!userList.isEmpty()) {
                total = totalCount(connection);
            }

            return new Page<>(userList, total);
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
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select UserID, UserName, UserPassword, UserBirth, UserAuth, CreatedAt, LatestLoginAt from Users where UserID=? and UserPassword =?";

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);
            ResultSet rs = psmt.executeQuery();
            if(rs.next()){
                User user = new User(
                        rs.getString("UserID"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getString("UserBirth"),
                        User.Auth.valueOf(rs.getString("UserAuth")),
                        Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("LatestLoginAt")) ? rs.getTimestamp("LatestLoginAt").toLocalDateTime() : null
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select UserID, UserName, UserPassword, UserBirth, UserAuth, CreatedAt, LatestLoginAt from Users where UserID=?";

        try( PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, userId);

            ResultSet rs = psmt.executeQuery();
            if(rs.next()){
                return Optional.of(
                        new User(
                        rs.getString("UserID"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getString("UserBirth"),
                        User.Auth.valueOf(rs.getString("UserAuth")),
                        Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("LatestLoginAt")) ? rs.getTimestamp("LatestLoginAt").toLocalDateTime() : null
                    )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into Users (UserID, UserName, UserPassword, UserBirth, UserAuth, CreatedAt, LatestLoginAt) values (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setString(index++, user.getUserId());
            psmt.setString(index++, user.getUserName());
            psmt.setString(index++, user.getUserPassword());
            psmt.setString(index++, user.getUserBirth());
            psmt.setString(index++, user.getUserAuth().toString());
            psmt.setTimestamp(index++, Objects.nonNull(user.getCreatedAt()) ? Timestamp.valueOf(user.getCreatedAt()) : null);
            psmt.setTimestamp(index, Objects.nonNull(user.getLatestLoginAt()) ? Timestamp.valueOf(user.getLatestLoginAt()) : null);

            int result = psmt.executeUpdate();

            if (result < 1) {
                throw new UserSaveException(user.getUserId());
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from Users where UserID=?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            int result = psmt.executeUpdate();

            if (result < 1) {
                throw new UserDeleteException(userId);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update Users set UserName=?, UserPassword=?, UserBirth=?, UserAuth=?, CreatedAt=?, LatestLoginAt=? where UserID=?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setString(index++, user.getUserName());
            psmt.setString(index++, user.getUserPassword());
            psmt.setString(index++, user.getUserBirth());
            psmt.setString(index++, user.getUserAuth().toString());
            psmt.setTimestamp(index++, Objects.nonNull(user.getCreatedAt()) ? Timestamp.valueOf(user.getCreatedAt()) : null);
            psmt.setTimestamp(index++, Objects.nonNull(user.getLatestLoginAt()) ? Timestamp.valueOf(user.getLatestLoginAt()) : null);
            psmt.setString(index, user.getUserId());

            int result = psmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update Users set LatestLoginAt=? where UserID=?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setTimestamp(1, Objects.nonNull(latestLoginAt) ? Timestamp.valueOf(latestLoginAt) : null);
            psmt.setString(2, userId);

            int result = psmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as user_count from Users where UserID=?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            ResultSet rs = psmt.executeQuery();

            int result = 0;

            if (rs.next()) {
                result = rs.getInt("user_count");
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private long totalCount(Connection connection) {
        String sql = "select count(*) as count from Users";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = psmt.executeQuery()){
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
