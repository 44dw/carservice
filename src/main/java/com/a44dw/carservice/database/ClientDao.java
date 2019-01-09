package com.a44dw.carservice.database;

import com.a44dw.carservice.entities.*;

import java.util.List;

public interface ClientDao {
    void insert(Client client);
    List<Client> getAll();
    void update(Client client);
    void delete(Client client);
}
