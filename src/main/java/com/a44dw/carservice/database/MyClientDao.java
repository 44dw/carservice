package com.a44dw.carservice.database;

import com.a44dw.carservice.entities.*;
import com.vaadin.ui.Notification;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MyClientDao implements ClientDao {

    private DBHelper helper;

    public MyClientDao() {
        this.helper = DBHelper.getInstance();
    }

    @Override
    public void insert(Client client) {
        try(PreparedStatement statement = helper.getConnection().prepareStatement("INSERT INTO Clients " +
                "(name, surname, middleName, telNumber) " +
                "VALUES (?, ?, ?, ?)")) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setString(3, client.getMiddleName());
            statement.setString(4, client.getTelNumber());
            if(client.getClientId() >= 0) statement.setLong(5, client.getClientId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll() {
        LinkedList<Client> clientList = new LinkedList<>();
        try(Statement statement = helper.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Clients")) {

            while(resultSet.next()) {
                clientList.add(new Client(resultSet.getLong("clientId"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("middleName"),
                        resultSet.getString("telNumber")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    @Override
    public void update(Client client) {
        try(PreparedStatement statement = helper.getConnection().prepareStatement("UPDATE Clients " +
                "SET name = ?, surname = ?, middleName = ?, telNumber = ? " +
                "WHERE clientId = ?")) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setString(3, client.getMiddleName());
            statement.setString(4, client.getTelNumber());
            if(client.getClientId() >= 0) statement.setLong(5, client.getClientId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Client client) {
        try(PreparedStatement statement = helper.getConnection().prepareStatement("DELETE FROM Clients " +
                    " WHERE clientId = ?")) {

            statement.setLong(1, client.getClientId());
            statement.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            Notification.show("Ошибка!",
                    "Невозможно удалить клиента, на которого существуют заказы",
                    Notification.Type.WARNING_MESSAGE);

            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
