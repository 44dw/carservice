package com.a44dw.carservice.entities;

import java.time.LocalDate;

public class Order implements DatabaseEntity {
    private long orderId;
    private long clientId;
    private String clientName;
    private String description;
    private LocalDate createDate;
    private LocalDate finishDate;
    private long cost;
    private int status;
    private String statusPresentation;

    public Order(long orderId, long clientId, String description, LocalDate createDate, LocalDate finishDate, long cost, int status) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.description = description;
        this.finishDate = finishDate;
        this.cost = cost;
        this.status = status;
        this.statusPresentation = OrderStatus.getStatus(status);
        this.createDate = createDate;
    }

    public Order(String clientName, long orderId, long clientId, String description, LocalDate createDate, LocalDate finishDate, long cost, int status) {
        this(orderId, clientId, description, createDate, finishDate, cost, status);
        this.clientName = clientName;
    }

    public Order(){this(-1, -1, "", LocalDate.now(), LocalDate.now(), 0, 0);}

    public LocalDate getCreateDate() {
        return createDate;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public void setStatus(int status) {
        this.status = status;
        this.statusPresentation = OrderStatus.getStatus(status);
    }

    public String getStatusPresentation() {
        return statusPresentation;
    }

    public int getStatus() {
        return status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
