package com.a44dw.carservice.database;

import com.a44dw.carservice.entities.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MyOrderDao implements OrderDao {

    private DBHelper helper;

    MyOrderDao() {
        this.helper = DBHelper.getInstance();
    }

    @Override
    public void insert(Order order) {
        try(PreparedStatement statement = helper.getConnection().prepareStatement("INSERT INTO Orders " +
                "(clientId, description, createDate, finishDate, cost, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setLong(1, order.getClientId());
            statement.setString(2, order.getDescription());
            statement.setDate(3, Date.valueOf(order.getCreateDate()));
            statement.setDate(4, Date.valueOf(order.getFinishDate()));
            statement.setLong(5, order.getCost());
            statement.setInt(6, order.getStatus());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAll() {
        LinkedList<Order> orderList = new LinkedList<>();
        try(Statement statement = helper.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT c.surname, c.name, c.middlename, o.* " +
                    "FROM Clients AS c, Orders AS o " +
                    "WHERE o.clientid = c.clientid")) {

            while(resultSet.next()) {
                String fullName = resultSet.getString("surname") + " " +
                        resultSet.getString("name") + " " +
                        resultSet.getString("middlename");

                orderList.add(new Order(fullName,
                        resultSet.getLong("orderId"),
                        resultSet.getLong("clientId"),
                        resultSet.getString("description"),
                        resultSet.getDate("createDate").toLocalDate(),
                        resultSet.getDate("finishDate").toLocalDate(),
                        resultSet.getLong("cost"),
                        resultSet.getInt("status")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public void update(Order order) {
        try(PreparedStatement statement = helper.getConnection().prepareStatement("UPDATE Orders " +
                "SET description = ?, finishDate = ?, cost = ?, status = ? " +
                "WHERE orderId = ?")) {
            statement.setString(1, order.getDescription());
            statement.setDate(2, Date.valueOf(order.getFinishDate()));
            statement.setLong(3, order.getCost());
            statement.setInt(4, order.getStatus());
            statement.setInt(5, (int)order.getOrderId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Order order) {
        try(PreparedStatement statement = helper.getConnection().prepareStatement("DELETE FROM Orders " +
                " WHERE orderId = ?")) {

            statement.setLong(1, order.getOrderId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
