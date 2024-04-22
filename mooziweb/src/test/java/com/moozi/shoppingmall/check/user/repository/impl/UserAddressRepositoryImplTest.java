package com.moozi.shoppingmall.check.user.repository.impl;

import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.user.domain.UserAddress;
import com.moozi.mooziweb.user.repository.UserAddressRepository;
import com.moozi.mooziweb.user.repository.impl.UserAddressRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserAddressRepositoryImplTest {
    UserAddressRepository addressRepository = new UserAddressRepositoryImpl();

    UserAddress testAddress;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testAddress = new UserAddress(1, "user", "sangwon", "11430", "경상남도 김해시 내외동", "성화빌 101호", "010-7439-3903");
        addressRepository.save(testAddress);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("find user address")
    void findByUserId() {
        List<UserAddress> addressList = addressRepository.findByUserId("user");

        assertAll(
                () -> assertNotNull(addressList),
                () -> assertEquals(1, addressList.size()),
                () -> assertEquals(testAddress, addressList.get(0))
        );
    }

    @Test
    @DisplayName("save user address")
    void save() {
        UserAddress address = new UserAddress(2, "admin", "admin", "12345", "address", "addressDetail", "123-1234-1234");
        int result = addressRepository.save(address);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(address, addressRepository.findByUserId("admin").get(0))
        );
    }

    @Test
    @DisplayName("update user address")
    void update() {
        UserAddress address = addressRepository.findByUserId("user").get(0);
        address.setZipcode("12345");
        int result = addressRepository.update(address);

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(address, addressRepository.findByUserId("user").get(0))
        );
    }

    @Test
    @DisplayName("delete user address")
    void deleteByAddressId() {
        UserAddress address = addressRepository.findByUserId("user").get(0);
        int result = addressRepository.deleteByAddressId(address.getAddressId());

        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(0, addressRepository.findByUserId("user").size())
        );
    }
}