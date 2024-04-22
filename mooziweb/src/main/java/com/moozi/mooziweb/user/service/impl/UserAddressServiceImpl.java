package com.moozi.mooziweb.user.service.impl;

import com.moozi.mooziweb.user.domain.UserAddress;
import com.moozi.mooziweb.user.exception.UserAddressDeleteException;
import com.moozi.mooziweb.user.exception.UserAddressNotFoundException;
import com.moozi.mooziweb.user.exception.UserAddressSaveException;
import com.moozi.mooziweb.user.exception.UserAddressUpdateException;
import com.moozi.mooziweb.user.repository.UserAddressRepository;
import com.moozi.mooziweb.user.service.UserAddressService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserAddressServiceImpl implements UserAddressService {
    UserAddressRepository addressRepository;

    public UserAddressServiceImpl(UserAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    @Override
    public List<UserAddress> getUserAddress(String userId) {
        List<UserAddress> addressList = addressRepository.findByUserId(userId);

        if (Objects.isNull(addressList) || addressList.isEmpty()) {
            throw new UserAddressNotFoundException(userId);
        }

        return addressList;
    }

    @Override
    public UserAddress getUserAddressById(int addressId) {
        Optional<UserAddress> optionalUserAddress = addressRepository.findById(addressId);

        if (Objects.isNull(optionalUserAddress) || optionalUserAddress.isEmpty()) {
            throw new UserAddressNotFoundException(String.valueOf(addressId));
        }

        return optionalUserAddress.get();
    }

    @Override
    public void saveUserAddress(UserAddress address) {
        int result = addressRepository.save(address);

        if (result < 1) {
            throw new UserAddressSaveException();
        }
    }

    @Override
    public void updateUserAddress(UserAddress address) {
        int result = addressRepository.update(address);

        if (result < 1) {
            throw new UserAddressUpdateException();
        }
    }

    @Override
    public void deleteUserAddress(int addressId) {
        int result = addressRepository.deleteByAddressId(addressId);

        if (result < 1) {
            throw new UserAddressDeleteException(String.valueOf(addressId));
        }
    }

    @Override
    public boolean isUserAddress(String userId) {
        int count = addressRepository.countUserAddressByUserId(userId);
        return count > 0;
    }
}
