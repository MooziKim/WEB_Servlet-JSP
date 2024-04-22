package com.moozi.mooziweb.user.service;

import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.common.page.Page;

public interface UserService {

    Page<User> getFullUser(int page, int pageSize);
    Page<User> getUserByUserAuth(String userAuth, int page, int pageSize);
    User getUser(String userId);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User doLogin(String userId, String userPassword);

}
