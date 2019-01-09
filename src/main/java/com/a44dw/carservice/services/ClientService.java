package com.a44dw.carservice.services;

import com.a44dw.carservice.database.*;
import com.a44dw.carservice.entities.*;
import com.a44dw.carservice.ui.*;

import java.util.LinkedList;
import java.util.List;

public class ClientService extends MyService {

    private static ClientService instance;
    private DBHelper helper;

    private Fillable[] responsers;
    private LinkedList<Client> clients;

    private ClientService() {
        this.helper = DBHelper.getInstance();
        this.helper.initClientDao();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public void setResponsers(Fillable[] responsers) {
        this.responsers = responsers;
    }

    public List<Client> loadClients() {
        clients = (LinkedList<Client>) helper.getClientDao().getAll();
        return getLoadedClients();
    }

    public List<Client> getLoadedClients() {
        return clients;
    }

    public boolean checkIfExist(Client newClient) {
        for(Client c : clients) {
            if(newClient.equals(c)) return true;
        }
        return false;
    }

    @Override
    public void doDatabaseEntityOperation(DatabaseEntity e) {
        Client client = (Client) e;
        if(client.getClientId() >= 0) updateClient(client);
        else addClient(client);
        for(Fillable f : responsers) f.fillGrid();
    }

    @Override
    public void deleteDatabaseEntity(DatabaseEntity e) {
        helper.getClientDao().delete((Client) e);
        for(Fillable f : responsers) f.fillGrid();
    }

    private void addClient(Client client) {
        helper.getClientDao().insert(client);
    }

    private void updateClient(Client client) {
        helper.getClientDao().update(client);
    }
}
