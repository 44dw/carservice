package com.a44dw.carservice.services;
import com.a44dw.carservice.database.*;
import com.a44dw.carservice.entities.*;
import com.a44dw.carservice.ui.*;

import java.util.LinkedList;

public class OrderService extends MyService {

    private static OrderService instance;
    private DBHelper helper;

    private OrderTab responser;

    private OrderService() {
        this.helper = DBHelper.getInstance();
        this.helper.initOrderDao();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public void setResponser(OrderTab responser) {
        this.responser = responser;
    }

    public LinkedList<Order> getOrders(String what, int where) {
        LinkedList<Order> obtainedOrders = (LinkedList) helper.getOrderDao().getAll();

        if(what.length() == 0) return obtainedOrders;

        LinkedList<Order> filteredOrders = new LinkedList<>();

        for(Order order : obtainedOrders) {
            boolean passedFilter = false;
            switch (where) {
                case 1: {
                    if(order.getClientName().toLowerCase().contains(what)) passedFilter = true;
                    break;
                }
                case 2: {
                    if(OrderStatus.getStatus(order.getStatus()).toLowerCase().contains(what)) passedFilter = true;
                    break;
                }
                case 3: {
                    if(order.getDescription().toLowerCase().contains(what)) passedFilter = true;
                    break;
                }
                default: {
                    if(order.getClientName().toLowerCase().contains(what)) passedFilter = true;
                    else if(OrderStatus.getStatus(order.getStatus()).toLowerCase().contains(what)) passedFilter = true;
                    else if(order.getDescription().toLowerCase().contains(what)) passedFilter = true;
                }
            }
            if(passedFilter) filteredOrders.add(order);
        }

        return filteredOrders;
    }

    public LinkedList<Order> getOrders() {
        return getOrders("", 0);
    }

    @Override
    public void doDatabaseEntityOperation(DatabaseEntity p) {
        Order order = (Order) p;
        if((order).getOrderId() >= 0) updateOrder(order);
        else addOrder(order);
        responser.fillGrid();
    }

    @Override
    public void deleteDatabaseEntity(DatabaseEntity e) {
        helper.getOrderDao().delete((Order) e);
        responser.fillGrid();
    }

    private void addOrder(Order order) {
        helper.getOrderDao().insert(order);
    }

    private void updateOrder(Order order) {
        helper.getOrderDao().update(order);
    }
}
