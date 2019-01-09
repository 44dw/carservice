package com.a44dw.carservice.ui;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.Map;

public class OrderFilterLayout extends HorizontalLayout {

    TextField orderFilterText;
    ComboBox orderFilterComboBox;
    Map<String, Integer> filterItems;
    OrderTab listener;

    public OrderFilterLayout(OrderTab listener) {

        this.listener = listener;

        filterItems = new HashMap<>();
        filterItems.put("Везде", 0);
        filterItems.put("Клиенты", 1);
        filterItems.put("Статус", 2);
        filterItems.put("Описание", 3);

        Label orderFilterLabel = new Label("Фильтр");
        orderFilterLabel.addStyleName("order-filter-title");
        orderFilterText = new TextField();
        orderFilterText.addShortcutListener(new ShortcutListener("name", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object o, Object o1) {
                find();
            }
        });
        orderFilterComboBox = new ComboBox();
        orderFilterComboBox.setContainerDataSource(new IndexedContainer(filterItems.keySet()));
        orderFilterComboBox.select("Везде");
        orderFilterComboBox.setNullSelectionAllowed(false);

        this.addComponents(orderFilterLabel,
                new Label("что искать:"),
                orderFilterText,
                new Label("где искать:"),
                orderFilterComboBox,
                new Button("Применить", event -> this.find()),
                new Button("Сбросить", event -> this.clear()));

        this.addStyleName("order-filter");
    }

    private void find() {
        String what = orderFilterText.getValue().toLowerCase();
        int where = filterItems.get(orderFilterComboBox.getValue());

        listener.sortGrid(what, where);
    }

    private void clear() {
        orderFilterText.clear();
        listener.fillGrid();
    }
}