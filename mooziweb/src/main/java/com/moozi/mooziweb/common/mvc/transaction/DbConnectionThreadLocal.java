package com.moozi.mooziweb.common.mvc.transaction;

import com.moozi.mooziweb.common.mvc.exception.ConnectionNotFoundException;
import com.moozi.mooziweb.common.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(()->false);

    public static void initialize(){

        //todo#2-1 - connection pool에서 connectionThreadLocal에 connection을 할당합니다.
        Connection connection = null;
        try {
            connection = DbUtils.getDataSource().getConnection();
            connectionThreadLocal.set(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //todo#2-2 connectiond의 Isolation level을 READ_COMMITED를 설정 합니다.
        if (Objects.isNull(connection)) {
            throw new ConnectionNotFoundException("connection을 찾을 수 없습니다.");
        }
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //todo#2-3 auto commit 을 false로 설정합니다.
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        return connectionThreadLocal.get();
    }

    public static void setSqlError(boolean sqlError){
        sqlErrorThreadLocal.set(sqlError);
    }

    public static boolean getSqlError(){
        return sqlErrorThreadLocal.get();
    }

    public static void reset(){

        //todo#2-4 사용이 완료된 connection은 close를 호출하여 connection pool에 반환합니다.
        Connection connection = null;
        try {
            connection = connectionThreadLocal.get();

            if (connection != null && getSqlError()) {
                connection.rollback();
            }

            if (connection != null && !getSqlError()){
                connection.commit();
            }

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("error: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        //todo#2-5 getSqlError() 에러가 존재하면 rollback 합니다.

        //todo#2-6 getSqlError() 에러가 존재하지 않다면 commit 합니다.

        //todo#2-7 현제 사용하고 있는 connection을 재 사용할 수 없도록 connectionThreadLocal을 초기화 합니다.
        connectionThreadLocal.remove();
        sqlErrorThreadLocal.remove();
    }
}
