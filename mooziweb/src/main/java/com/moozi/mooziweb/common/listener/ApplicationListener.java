package com.moozi.mooziweb.common.listener;

import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.user.domain.User;
import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.repository.impl.UserPointInfoRepositoryImpl;
import com.moozi.mooziweb.user.repository.impl.UserRepositoryImpl;
import com.moozi.mooziweb.user.service.UserPointInfoService;
import com.moozi.mooziweb.user.service.UserService;
import com.moozi.mooziweb.user.service.impl.UserPointInfoServiceImpl;
import com.moozi.mooziweb.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final UserPointInfoService pointInfoService = new UserPointInfoServiceImpl(new UserPointInfoRepositoryImpl());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
        DbConnectionThreadLocal.initialize();
        try {
            userService.saveUser(new User(
                    "admin",
                    "admin",
                    "12345",
                    "19990210",
                    User.Auth.ROLE_ADMIN,
                    LocalDateTime.now(),
                    null
            ));

            pointInfoService.saveUserPointInfo(new UserPointInfo(
                    "admin",
                    UserPointInfo.PointInfo.SAVE,
                    1000000,
                    LocalDateTime.now()
            ));

        } catch (RuntimeException e) {
            log.error("error: {}", e.getMessage());
        }

        try {
            userService.saveUser(new User(
                    "user",
                    "user",
                    "12345",
                    "19990210",
                    User.Auth.ROLE_USER,
                    LocalDateTime.now(),
                    null
            ));

            pointInfoService.saveUserPointInfo(new UserPointInfo(
                    "user",
                    UserPointInfo.PointInfo.SAVE,
                    1000000,
                    LocalDateTime.now()
            ));

        } catch (RuntimeException e) {
            log.error("error: {}", e.getMessage());
        }
        DbConnectionThreadLocal.reset();
    }
}
