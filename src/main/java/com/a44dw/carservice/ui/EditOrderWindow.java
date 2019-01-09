package com.a44dw.carservice.ui;

import com.a44dw.carservice.entities.*;
import com.a44dw.carservice.services.*;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

import java.util.Collections;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;

class EditOrderWindow extends Window {

    private Validator validator;
    private DateField createDate;
    private DateField finishDate;
    private TextArea description;
    private Order order;
    private OrderService orderService;
    private String selectedStatus;
    private Client selectedClient;

    EditOrderWindow(Order order) {
        super("Заказ");
        this.order = order;

        init();
    }

    private void init() {
        orderService = OrderService.getInstance();
        FormLayout nameForm = new FormLayout();
        nameForm.addStyleName("window-content");

        BeanItemContainer<Client> beans = new BeanItemContainer<>(Client.class);
        List<Client> clients = ClientService.getInstance().getLoadedClients();
        Collections.sort(clients);
        beans.addAll(clients);

        ComboBox orderClientComboBox = new ComboBox("Клиент", beans);
        orderClientComboBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        orderClientComboBox.setItemCaptionPropertyId("complexName");
        orderClientComboBox.setNullSelectionAllowed(false);
        orderClientComboBox.addValueChangeListener(event -> selectedClient = (Client) orderClientComboBox.getValue());
        if(clients.size() > 0) orderClientComboBox.select(clients.get(0));
        orderClientComboBox.setWidth(70, Unit.PERCENTAGE);

        createDate = new DateField("Дата приёма", Date.from(
                order.getCreateDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        createDate.setEnabled(false);

        finishDate = new DateField("Дата сдачи", Date.from(
                order.getFinishDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        finishDate.setRangeStart(new Date());
        NumberField cost = new NumberField("Стоимость", String.valueOf(order.getCost()));

        ComboBox orderStatusComboBox = new ComboBox("Статус заказа",
                new IndexedContainer(OrderStatus.STATUS_MAP.keySet()));
        orderClientComboBox.setNullSelectionAllowed(false);
        orderStatusComboBox.addValueChangeListener(event -> {
            selectedStatus = (String) event.getProperty().getValue();
            if(selectedStatus == null)
                orderStatusComboBox.select(OrderStatus.getStatus(0));
        });
        description = new TextArea("Описание", order.getDescription());
        description.setWidth(90, Unit.PERCENTAGE);
        orderStatusComboBox.select(order.getStatusPresentation());

        if(order.getClientId() >= 0) {
            Client selected = null;
            for(Client client : clients) if(client.getClientId() == order.getClientId()) {
                selected = client;
                break;
            }
            orderClientComboBox.select(selected);

            orderClientComboBox.setEnabled(false);
        }

        nameForm.addComponent(orderClientComboBox);
        nameForm.addComponent(createDate);
        nameForm.addComponent(finishDate);
        nameForm.addComponent(cost);
        nameForm.addComponent(orderStatusComboBox);
        nameForm.addComponent(description);

        validator = new Validator();

        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(new Button("OK", event -> {
            if(validator.validateFields()) {
                order.setClientId(selectedClient.getClientId());
                order.setFinishDate(finishDate.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                order.setCost(Long.parseLong(cost.getValue().length() == 0 ? "0" : cost.getValue()));
                order.setDescription(description.getValue());
                order.setStatus(OrderStatus.STATUS_MAP.get(selectedStatus));

                orderService.doDatabaseEntityOperation(order);
                close();
            }
        }));
        layout.addComponent(new Button("Отмена", event -> close()));

        nameForm.addComponent(layout);

        this.setContent(nameForm);
        this.setWidth("600px");
        this.center();

        this.setModal(true);
    }

    private class Validator {

        String errorField;

        boolean result = true;

        boolean validateFields() {

            if(selectedClient == null) {
                result = false;
                errorField = "\"Клиент\"";
            } else if(finishDate.getValue() == null) {
                result = false;
                errorField = "\"Дата окончания\"";
            } else if(description.getValue().length() == 0) {
                result = false;
                errorField = "\"Описание\"";
            }

            if(!result) {
                Notification.show("Поле " + errorField + " заполнено неверно!",
                        "Проверьте правильность заполнения формы",
                        Notification.Type.WARNING_MESSAGE);
            }

            return result;
        }
    }
}
