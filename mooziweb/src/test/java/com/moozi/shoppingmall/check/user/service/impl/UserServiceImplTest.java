package com.moozi.shoppingmall.check.user.service.impl;

import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.exception.UserAlreadyExistsException;
import com.moozi.mooziweb.user.exception.UserNotFoundException;
import com.moozi.mooziweb.user.repository.UserRepository;
import com.moozi.mooziweb.user.service.UserService;
import com.moozi.mooziweb.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

//todo#4-6 Test Code가 통과하도록 UserServiceImpl을 구현합니다.

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(userRepository);
    User testUser = new User("nhnacademy-test-user","nhn아카데미","nhnacademy-test-password","19900505", User.Auth.ROLE_USER, LocalDateTime.now(),LocalDateTime.now());

    @Test
    @DisplayName("getUser")
    void getUser() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(testUser));
        userService.getUser(testUser.getUserId());
        Mockito.verify(userRepository,Mockito.times(1)).findById(anyString());
    }

    @Test
    @DisplayName("getUser - user not found -> return null")
    void getUser_not_found(){
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        User user = userService.getUser(testUser.getUserId());
        Assertions.assertNull(user);
    }

    @Test
    @DisplayName("save user")
    void saveUser() {

        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(0);
        Mockito.when(userRepository.save(any())).thenReturn(1);
        userService.saveUser(testUser);
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());
        Mockito.verify(userRepository,Mockito.times(1)).save(any());

    }

    @Test
    @DisplayName("save user -already exist user")
    void saveUser_exist(){
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        Throwable throwable = Assertions.assertThrows(UserAlreadyExistsException.class,()->userService.saveUser(testUser));
        log.debug("error:{}",throwable.getMessage());
    }

    @Test
    @DisplayName("update user")
    void updateUser() {
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        Mockito.when(userRepository.update(testUser)).thenReturn(1);
        userService.updateUser(testUser);
        Mockito.verify(userRepository,Mockito.times(1)).update(any());
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());
    }

    @Test
    @DisplayName("delete user")
    void deleteUser() {
        Mockito.when(userRepository.deleteByUserId(anyString())).thenReturn(1);
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(0);

        userService.deleteUser(testUser.getUserId());

        Mockito.verify(userRepository,Mockito.times(1)).deleteByUserId(anyString());
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());

    }

    @Test
    @DisplayName("login - success")
    void doLogin() {
        Mockito.when(userRepository.findByUserIdAndUserPassword(anyString(),anyString())).thenReturn(Optional.ofNullable(testUser));
        Mockito.when(userRepository.updateLatestLoginAtByUserId(anyString(),any())).thenReturn(1);

        User user = userService.doLogin(testUser.getUserId(), testUser.getUserPassword());

        Assertions.assertEquals(testUser,user);
        Mockito.verify(userRepository,Mockito.times(1)).findByUserIdAndUserPassword(anyString(),anyString());
        Mockito.verify(userRepository,Mockito.times(1)).updateLatestLoginAtByUserId(anyString(),any());
    }

    @Test
    @DisplayName("login fail")
    void doLogin_fail(){
        Mockito.when(userRepository.findByUserIdAndUserPassword(anyString(),anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,()->userService.doLogin(testUser.getUserId(), testUser.getUserPassword()));
        Mockito.verify(userRepository,Mockito.times(1)).findByUserIdAndUserPassword(anyString(),anyString());
    }

}