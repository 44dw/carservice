package com.a44dw.carservice.database;

import com.a44dw.carservice.entities.*;

import java.util.List;

public interface OrderDao {
    void insert(Order order);
    List<Order> getAll();
    void update(Order order);
    void delete(Order order);
}
