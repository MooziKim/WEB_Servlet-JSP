package com.moozi.mooziweb.user.repository.impl;

import com.moozi.mooziweb.user.domain.UserAddress;
import com.moozi.mooziweb.user.repository.UserAddressRepository;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserAddressRepositoryImpl implements UserAddressRepository {
    @Override
    public List<UserAddress> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from UserAddress where UserID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            rs = psmt.executeQuery();
            List<UserAddress> userAddressList = new ArrayList<>();
            while(rs.next()) {
                userAddressList.add(
                        new UserAddress(
                                rs.getInt("AddressID"),
                                rs.getString("UserID"),
                                rs.getString("Name"),
                                rs.getString("Zipcode"),
                                rs.getString("Address"),
                                rs.getString("AddressDetail"),
                                rs.getString("PhoneNumber")
                        )
                );
            }
            return userAddressList;
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
    public Optional<UserAddress> findById(int addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from UserAddress where AddressID = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, addressId);

            rs = psmt.executeQuery();
            if(rs.next()) {
                return Optional.of(
                        new UserAddress(
                                rs.getInt("AddressID"),
                                rs.getString("UserID"),
                                rs.getString("Name"),
                                rs.getString("Zipcode"),
                                rs.getString("Address"),
                                rs.getString("AddressDetail"),
                                rs.getString("PhoneNumber")
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
    public int save(UserAddress address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into UserAddress (UserID, Name, Zipcode, Address, AddressDetail, PhoneNumber) values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setString(index++, address.getUserId());
            psmt.setString(index++, address.getName());
            psmt.setString(index++, address.getZipcode());
            psmt.setString(index++, address.getAddress());
            psmt.setString(index++, address.getAddressDetail());
            psmt.setString(index, address.getPhoneNumber());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(UserAddress address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update UserAddress set UserID = ?, Name = ?, Zipcode = ?, Address = ?, AddressDetail = ?, PhoneNumber = ? where AddressID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            int index = 1;
            psmt.setString(index++, address.getUserId());
            psmt.setString(index++, address.getName());
            psmt.setString(index++, address.getZipcode());
            psmt.setString(index++, address.getAddress());
            psmt.setString(index++, address.getAddressDetail());
            psmt.setString(index++, address.getPhoneNumber());
            psmt.setInt(index, address.getAddressId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByAddressId(int addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from UserAddress where AddressID = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, addressId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countUserAddressByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) as count from UserAddress where UserID = ?";

        int count = -1;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
