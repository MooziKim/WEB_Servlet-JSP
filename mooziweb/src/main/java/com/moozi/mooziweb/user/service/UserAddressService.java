package com.moozi.mooziweb.user.service;

import com.moozi.mooziweb.user.domain.UserAddress;

import java.util.List;

public interface UserAddressService {
    // 조회(getUserAddress), 등록(saveUserAddress), 수정(updateUserAddress), 삭제(deleteUserAddress)
    List<UserAddress> getUserAddress(String userId);
    UserAddress getUserAddressById(int addressId);
    void saveUserAddress(UserAddress address);
    void updateUserAddress(UserAddress address);
    void deleteUserAddress(int addressId);

    boolean isUserAddress(String user);
}
