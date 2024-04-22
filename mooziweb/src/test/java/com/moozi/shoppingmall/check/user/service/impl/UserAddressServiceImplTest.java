package com.moozi.shoppingmall.check.user.service.impl;

import com.moozi.mooziweb.user.domain.UserAddress;
import com.moozi.mooziweb.user.exception.UserAddressDeleteException;
import com.moozi.mooziweb.user.exception.UserAddressNotFoundException;
import com.moozi.mooziweb.user.exception.UserAddressUpdateException;
import com.moozi.mooziweb.user.repository.UserAddressRepository;
import com.moozi.mooziweb.user.service.UserAddressService;
import com.moozi.mooziweb.user.service.impl.UserAddressServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserAddressServiceImplTest {

    UserAddressRepository addressRepository = Mockito.mock(UserAddressRepository.class);
    UserAddressService addressService = new UserAddressServiceImpl(addressRepository);
    static UserAddress testAddress = new UserAddress(1, "test-user", "sangwon", "12345", "서울", "101호", "010-1234-1234");
    static List<UserAddress> addressList = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        addressList.add(testAddress);
    }

    @Test
    @DisplayName("getUserAddress")
    void getUserAddress() {
        Mockito.when(addressRepository.findByUserId(anyString())).thenReturn(addressList);
        addressService.getUserAddress(testAddress.getUserId());
        Mockito.verify(addressRepository, Mockito.times(1)).findByUserId(anyString());
    }

    @Test
    @DisplayName("getUserAddress : not exist")
    void getUserAddress_not_exist() {
        Mockito.when(addressRepository.findByUserId(anyString())).thenReturn(new ArrayList<>());
        Throwable throwable = assertThrows(UserAddressNotFoundException.class, () -> addressService.getUserAddress(anyString()));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("saveUserAddress")
    void saveUserAddress() {
        Mockito.when(addressRepository.save(any())).thenReturn(1);
        addressService.saveUserAddress(any());
        Mockito.verify(addressRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("updateUserAddress")
    void updateUserAddress() {
        Mockito.when(addressRepository.update(any())).thenReturn(1);
        addressService.updateUserAddress(any());
        Mockito.verify(addressRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("updateUserAddress : addressnot found")
    void updateUserAddress_address_not_found() {
        Mockito.when(addressRepository.update(any())).thenReturn(0);
        Throwable throwable = assertThrows(UserAddressUpdateException.class, () -> addressService.updateUserAddress(any()));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("deleteUserAddress")
    void deleteUserAddress() {
        Mockito.when(addressRepository.deleteByAddressId(anyInt())).thenReturn(1);
        addressService.deleteUserAddress(anyInt());
        Mockito.verify(addressRepository, Mockito.times(1)).deleteByAddressId(anyInt());
    }

    @Test
    @DisplayName("deleteUserAddress : address not found")
    void deleteUserAddress_address_not_found() {
        Mockito.when(addressRepository.deleteByAddressId(anyInt())).thenReturn(0);
        Throwable throwable = assertThrows(UserAddressDeleteException.class, () -> addressService.deleteUserAddress(anyInt()));
        log.debug("error: {}", throwable.getMessage());
    }
}
