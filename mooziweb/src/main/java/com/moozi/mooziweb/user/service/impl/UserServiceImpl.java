package com.moozi.mooziweb.user.service.impl;

import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.exception.UserAlreadyExistsException;
import com.moozi.mooziweb.user.exception.UserNotFoundException;
import com.moozi.mooziweb.user.repository.UserRepository;
import com.moozi.mooziweb.user.service.UserService;
import com.moozi.mooziweb.common.page.Page;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getFullUser(int page, int pageSize) {
        Page<User> userPage = userRepository.findAll(page, pageSize);

        if (Objects.isNull(userPage) || userPage.getContent().isEmpty()) {
            throw new UserNotFoundException("empty users");
        }

        return userPage;
    }

    @Override
    public Page<User> getUserByUserAuth(String userAuth, int page, int pageSize) {
        Page<User> userPage = userRepository.findByUserAuth(userAuth, page, pageSize);

        if (Objects.isNull(userPage) || userPage.getContent().isEmpty()) {
            throw new UserNotFoundException("empty users");
        }

        return userPage;
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> OptionalUser = userRepository.findById(userId);
        if (OptionalUser.isEmpty()) {
            return null;
        }
        User user = OptionalUser.get();
        return user;
    }

    @Override
    public void saveUser(User user) throws UserAlreadyExistsException {
        //todo#4-2 회원등록
        if (userRepository.countByUserId(user.getUserId()) > 0) {
            throw new UserAlreadyExistsException(user.getUserId());
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) throws UserNotFoundException {
        //todo#4-3 회원수정
        if (userRepository.countByUserId(user.getUserId()) < 1) {
            throw new UserNotFoundException(user.getUserId());
        }
        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) throws UserAlreadyExistsException {
        //todo#4-4 회원삭제
        userRepository.deleteByUserId(userId);

        if (userRepository.countByUserId(userId) > 0) {
            throw new UserAlreadyExistsException(userId);
        }
    }

    @Override
    public User doLogin(String userId, String userPassword) throws UserNotFoundException {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> OptionalUser = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if (OptionalUser.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        User user = OptionalUser.get();
        userRepository.updateLatestLoginAtByUserId(user.getUserId(), LocalDateTime.now());
        return user;
    }

}
