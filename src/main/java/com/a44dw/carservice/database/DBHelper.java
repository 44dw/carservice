package com.a44dw.carservice.database;

import java.sql.*;

public class DBHelper {

    private static DBHelper instance;
    private DaoFactory daoFactory;
    private OrderDao orderDao;
    private ClientDao clientDao;

    private DBHelper(){
        daoFactory = new MyDaoFactory();
        try {
            checkTables(daoFactory.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    public void initOrderDao() {
        orderDao = daoFactory.getOrderDao();
    }

    public void initClientDao() {
        clientDao = daoFactory.getClientDao();
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    private void checkTables(Connection connection) throws Exception {
        boolean created = false;

        DatabaseMetaData dbm = connection.getMetaData();

        try(ResultSet rs = dbm.getTables(null, null, "CLIENTS", null)) {
            if (rs.next()) created = true;
        }

        if(!created) {
            createTables(connection);
        }
    }

    public Connection getConnection() {
        try {
            return daoFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            daoFactory.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTables(Connection connection) {
        try (Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE Clients " +
                    "(clientId INTEGER IDENTITY, " +
                    "name VARCHAR(20), " +
                    "surname VARCHAR(20), " +
                    "middleName VARCHAR(20), " +
                    "telNumber VARCHAR(11)," +
                    "PRIMARY KEY (clientId))");

            statement.execute("CREATE TABLE Orders " +
                    "(orderId INTEGER IDENTITY, " +
                    "clientId INTEGER, " +
                    "description LONGVARCHAR, " +
                    "createDate DATE, " +
                    "finishDate DATE, " +
                    "cost INTEGER, " +
                    "status VARCHAR(10), " +
                    "PRIMARY KEY (orderId), " +
                    "FOREIGN KEY (clientId) REFERENCES Clients(clientId))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
