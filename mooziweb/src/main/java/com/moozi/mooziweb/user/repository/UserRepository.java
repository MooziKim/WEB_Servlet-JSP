package com.moozi.mooziweb.user.repository;

import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.common.page.Page;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository {
    Page<User> findAll(int page, int pageSize);
    Page<User> findByUserAuth(String userAuth, int page, int pageSize);
    Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
    Optional<User> findById(String userId);
    int save(User user);
    int deleteByUserId(String userId);
    int update(User user);
    int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt);
    int countByUserId(String userId);
}
