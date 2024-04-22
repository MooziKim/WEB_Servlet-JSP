package com.moozi.shoppingmall.check.user.service.impl;

import com.moozi.mooziweb.user.domain.UserPointInfo;
import com.moozi.mooziweb.user.exception.PointInfoNotFoundException;
import com.moozi.mooziweb.user.exception.PointInfoSaveException;
import com.moozi.mooziweb.user.repository.UserPointInfoRepository;
import com.moozi.mooziweb.user.service.UserPointInfoService;
import com.moozi.mooziweb.user.service.impl.UserPointInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserPointInfoServiceImplTest {

    UserPointInfoRepository pointInfoRepository = Mockito.mock(UserPointInfoRepository.class);
    UserPointInfoService pointInfoService = new UserPointInfoServiceImpl(pointInfoRepository);
    static UserPointInfo testPointInfo = new UserPointInfo("nhnacademy-test-user", UserPointInfo.PointInfo.USE, 10000, LocalDateTime.now());
    static List<UserPointInfo> pointInfos = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        pointInfos.add(testPointInfo);
    }

    @Test
    @DisplayName("getUserPointInfo")
    void getUserPointInfo() {
        Mockito.when(pointInfoRepository.findById(anyString())).thenReturn(pointInfos);
        pointInfoService.getUserPointInfo(testPointInfo.getUserId());
        Mockito.verify(pointInfoRepository, Mockito.times(1)).findById(anyString());
    }

    @Test
    @DisplayName("getUserPointInfo - user not found")
    void getUserPointInfo_not_found() {
        Mockito.when(pointInfoRepository.findById(anyString())).thenReturn(null);
        Throwable throwable = assertThrows(PointInfoNotFoundException.class, () -> pointInfoService.getUserPointInfo(anyString()));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("saveUserPointInfo")
    void saveUserPointInfo() {
        Mockito.when(pointInfoRepository.save(any())).thenReturn(1);
        pointInfoService.saveUserPointInfo(any());
        Mockito.verify(pointInfoRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("saveUserPointInfo - already exist point info")
    void saveUserPointInfo_exist() {
        Mockito.when(pointInfoRepository.save(any())).thenReturn(0);
        Throwable throwable = assertThrows(PointInfoSaveException.class, () -> pointInfoService.saveUserPointInfo(any()));
        log.debug("error: {}", throwable.getMessage());

    }
}