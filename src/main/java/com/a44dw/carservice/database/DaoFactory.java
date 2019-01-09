package com.a44dw.carservice.database;

import java.sql.Connection;

public interface DaoFactory {
    Connection getConnection() throws Exception;
    ClientDao getClientDao();
    OrderDao getOrderDao();
    void closeConnection() throws Exception;
}
