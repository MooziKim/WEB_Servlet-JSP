package com.moozi.shoppingmall.check.common.util;

import com.moozi.mooziweb.common.util.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;

//todo#2 - connection-pool test

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class DbUtilsTest {

    @Test
    @Order(1)
    @DisplayName("instance of dbcp2")
    void connection_close() throws SQLException {
        Assertions.assertInstanceOf(BasicDataSource.class, DbUtils.getDataSource());
    }

    @Test
    @Order(2)
    @DisplayName("mysql driver load : success")
    void mysql_driver_load(){
        String driverClassName="com.mysql.cj.jdbc.Driver";
        try {
            Class<?> driver = Class.forName(driverClassName);
            log.info("driver:{}", driver.getName());
            Assertions.assertEquals(driver.getName(),driverClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    @DisplayName("mysql connect")
    void my_connect() throws SQLException {
        Connection connection = DbUtils.getDataSource().getConnection();
        Assertions.assertTrue(connection.isValid(2));
    }

    @Test
    @Order(4)
    @DisplayName("테스트를 위한 pool-size:5 설정, maxIdle, maxTotal, initialSize, minIdle")
    void connection_pool_size(){
        BasicDataSource basicDataSource = (BasicDataSource) DbUtils.getDataSource();
        Assertions.assertAll(
                ()->Assertions.assertEquals(5,basicDataSource.getMaxIdle()),
                ()->Assertions.assertEquals(5,basicDataSource.getMaxTotal()),
                ()->Assertions.assertEquals(5,basicDataSource.getInitialSize()),
                ()->Assertions.assertEquals(5,basicDataSource.getMinIdle())
        );
    }

}