package com.moozi.shoppingmall.check.user.repository.impl;

import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.repository.UserPointInfoRepository;
import com.moozi.mooziweb.user.repository.impl.UserPointInfoRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserPointInfoRepositoryImplTest {

    UserPointInfoRepository pointInfoRepository = new UserPointInfoRepositoryImpl();
    UserPointInfo testPointInfo1;
    UserPointInfo testPointInfo2;
    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testPointInfo1 = new UserPointInfo(
                "user",
                UserPointInfo.PointInfo.SAVE,
                1000,
                LocalDateTime.now()
        );
        testPointInfo2 = new UserPointInfo(
                "user",
                UserPointInfo.PointInfo.USE,
                1000,
                LocalDateTime.now()
        );
        pointInfoRepository.save(testPointInfo1);
        pointInfoRepository.save(testPointInfo2);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("포인트 정보 확인")
    void findById() {
        List<UserPointInfo> pointInfos = pointInfoRepository.findById("user");

        assertAll(
                () -> assertEquals(2, pointInfos.size()),
                () -> assertEquals(testPointInfo1, pointInfos.get(0)),
                () -> assertEquals(testPointInfo2, pointInfos.get(1))
        );
    }

    @Test
    @DisplayName("포인트 정보 저장")
    void save() {
        UserPointInfo pointInfo = new UserPointInfo("admin", UserPointInfo.PointInfo.USE, 500, LocalDateTime.now());
        pointInfoRepository.save(pointInfo);
        List<UserPointInfo> pointInfos = pointInfoRepository.findById("admin");
        assertEquals(pointInfo, pointInfos.get(0));
    }
}