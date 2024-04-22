package com.moozi.mooziweb.user.repository;

import com.moozi.mooziweb.user.domain.UserAddress;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository {
    //조회(select), 등록(insert), 수정(update), 삭제(delete)
    List<UserAddress> findByUserId(String userId);
    Optional<UserAddress> findById(int addressId);
    int save(UserAddress address);
    int update(UserAddress address);
    int deleteByAddressId(int addressId);

    int countUserAddressByUserId(String userId);
}
