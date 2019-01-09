package com.a44dw.carservice.ui;

import com.a44dw.carservice.entities.*;
import com.a44dw.carservice.services.*;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

public class OrderTab extends VerticalLayout implements Fillable {

    private Grid orderGrid;
    private OrderService service;
    private ActionButtons buttons;
    private OrderFilterLayout filterLayout;

    public OrderTab() {
        this.setCaption("Заказы");

        service = OrderService.getInstance();
        service.setResponser(this);

        filterLayout = new OrderFilterLayout(this);

        orderGrid = new Grid();
        fillGrid();
        orderGrid.setEditorEnabled(false);
        orderGrid.removeColumn("orderId");
        orderGrid.removeColumn("clientId");
        orderGrid.removeColumn("status");
        orderGrid.getColumn("clientName").setHeaderCaption("Клиент");
        orderGrid.getColumn("description").setHeaderCaption("Описание");
        orderGrid.getColumn("createDate").setHeaderCaption("Дата создания");
        orderGrid.getColumn("finishDate").setHeaderCaption("Дата окончания работ");
        orderGrid.getColumn("cost").setHeaderCaption("Стоимость");
        orderGrid.getColumn("statusPresentation").setHeaderCaption("Статус");
        orderGrid.setSizeFull();

        buttons = new ActionButtons();

        this.addComponents(filterLayout, orderGrid, buttons);
    }

    @Override
    public void fillGrid() {
        BeanItemContainer<Order> orders = new BeanItemContainer<>(Order.class, service.getOrders());
        orderGrid.setContainerDataSource(orders);
    }

    public void sortGrid(String what, int where) {
        BeanItemContainer<Order> orders = new BeanItemContainer<>(Order.class, service.getOrders(what, where));
        orderGrid.setContainerDataSource(orders);
    }

    public void openEditWindow(Order order) {
        EditOrderWindow editWindow = new EditOrderWindow(order);
        UI.getCurrent().addWindow(editWindow);
    }

    public void openWarningWindow(DatabaseEntity order) {
        WarningWindow warningWindow = new WarningWindow(order, service);
        UI.getCurrent().addWindow(warningWindow);
    }

    public class ActionButtons extends HorizontalLayout {

        public ActionButtons() {

            Button add = new Button("Добавить", event -> openEditWindow(new Order()));
            Button change = new Button("Изменить", event -> {
                Object s = ((Grid.SingleSelectionModel) orderGrid.getSelectionModel()).getSelectedRow();
                if(s == null) return;
                BeanItem selected = (BeanItem) orderGrid.getContainerDataSource().getItem(s);

                openEditWindow((Order) selected.getBean());
            });
            Button remove = new Button("Удалить", event -> {
                Object s = ((Grid.SingleSelectionModel) orderGrid.getSelectionModel()).getSelectedRow();
                if(s == null) return;
                BeanItem selected = (BeanItem) orderGrid.getContainerDataSource().getItem(s);

                openWarningWindow((DatabaseEntity) selected.getBean());
            });

            this.addComponents(add, change, remove);
        }
    }
}
