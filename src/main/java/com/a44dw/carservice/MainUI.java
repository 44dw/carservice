package com.a44dw.carservice;

import com.a44dw.carservice.database.*;
import com.a44dw.carservice.ui.*;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@Theme("mytheme")
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        TabSheet tabSheet = new TabSheet();

        OrderTab orderTab = new OrderTab();
        ClientTab clientTab = new ClientTab();

        clientTab.setResponsers(new Fillable[]{orderTab, clientTab});

        tabSheet.addTab(orderTab);
        tabSheet.addTab(clientTab);

        tabSheet.setSizeFull();

        setContent(tabSheet);

        addDetachListener((DetachListener) detachEvent ->
                DBHelper.getInstance().closeConnection());
    }
}