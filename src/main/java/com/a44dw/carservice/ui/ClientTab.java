package com.a44dw.carservice.ui;

import com.a44dw.carservice.entities.*;
import com.a44dw.carservice.services.*;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

public class ClientTab extends VerticalLayout implements Fillable{

    private Grid clientGrid;
    private ClientService service;

    public ClientTab() {
        this.setCaption("Клиенты");

        service = ClientService.getInstance();

        clientGrid = new Grid();
        fillGrid();
        clientGrid.setEditorEnabled(false);
        clientGrid.removeColumn("clientId");
        clientGrid.removeColumn("complexName");
        clientGrid.setColumnOrder("surname", "name", "middleName", "telNumber");
        clientGrid.getColumn("name").setHeaderCaption("Имя");
        clientGrid.getColumn("surname").setHeaderCaption("Фамилия");
        clientGrid.getColumn("middleName").setHeaderCaption("Отчество");
        clientGrid.getColumn("telNumber").setHeaderCaption("Телефон");
        clientGrid.setSizeFull();

        ClientActionButtons buttons = new ClientActionButtons();

        this.addComponents(clientGrid, buttons);
    }

    public void fillGrid() {
        BeanItemContainer<Client> clients = new BeanItemContainer<>(Client.class, service.loadClients());
        clientGrid.setContainerDataSource(clients);
    }

    private void openEditWindow(Client client) {
        EditClientWindow editWindow = new EditClientWindow(client);
        UI.getCurrent().addWindow(editWindow);
    }

    private void openWarningWindow(DatabaseEntity client) {
        WarningWindow warningWindow = new WarningWindow(client, service);
        UI.getCurrent().addWindow(warningWindow);
    }

    public void setResponsers(Fillable[] responsers) {
        this.service.setResponsers(responsers);
    }

    public class ClientActionButtons extends HorizontalLayout {

        public ClientActionButtons() {

            Button add = new Button("Добавить", event -> openEditWindow(new Client()));
            Button change = new Button("Изменить", event -> {
                Object s = ((Grid.SingleSelectionModel) clientGrid.getSelectionModel()).getSelectedRow();
                if(s == null) return;
                BeanItem selected = (BeanItem) clientGrid.getContainerDataSource().getItem(s);

                clientGrid.deselectAll();

                openEditWindow((Client) selected.getBean());
            });
            Button remove = new Button("Удалить", event -> {
                Object s = ((Grid.SingleSelectionModel) clientGrid.getSelectionModel()).getSelectedRow();
                if(s == null) return;
                BeanItem selected = (BeanItem) clientGrid.getContainerDataSource().getItem(s);

                openWarningWindow((DatabaseEntity) selected.getBean());
            });

            this.addComponents(add, change, remove);
        }
    }
}
