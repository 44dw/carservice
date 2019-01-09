package com.a44dw.carservice.entities;

import java.util.Objects;

public class Client implements DatabaseEntity, Comparable {
    private long clientId;
    private String name;
    private String surname;
    private String middleName;
    private String telNumber;

    private String complexName;

    public Client(long id, String name, String surname, String middleName, String telNumber) {
        this.clientId = id;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.telNumber = telNumber;

        this.complexName = surname + " " + name + " " + middleName;
    }

    public Client() {
        this(-1, "", "", "", "");
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }

    public long getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getComplexName() {
        return complexName;
    }

    @Override
    public int compareTo(Object o) {
        return this.getSurname().compareTo(((Client)o).getSurname());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(middleName, client.middleName) &&
                Objects.equals(telNumber, client.telNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, middleName, telNumber);
    }
}
