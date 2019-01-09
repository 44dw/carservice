package com.a44dw.carservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MyDaoFactory implements DaoFactory {

    private static final String DATABASE_URL = "jdbc:hsqldb:file:dbpath/dbpath";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private boolean connected;

    private Connection connection;

    @Override
    public Connection getConnection() throws Exception {
        if((this.connection == null)||(!connected)) {
            this.connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            connected = true;
        }
        return connection;
    }

    @Override
    public ClientDao getClientDao() {
        return new MyClientDao();
    }

    @Override
    public OrderDao getOrderDao() {
        return new MyOrderDao();
    }

    @Override
    public void closeConnection() throws Exception {
        try(Statement statement = connection.createStatement()) {
            statement.execute("SHUTDOWN");
        } finally {
            connection.close();
            connected = false;
        }
    }
}
