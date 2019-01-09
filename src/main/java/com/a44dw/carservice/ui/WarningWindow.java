package com.a44dw.carservice.ui;

import com.a44dw.carservice.entities.*;
import com.a44dw.carservice.services.*;
import com.vaadin.ui.*;

class WarningWindow extends com.vaadin.ui.Window {

    DatabaseEntity databaseEntity;
    private MyService service;

    WarningWindow(DatabaseEntity databaseEntity, MyService service) {
        super("Внимание");

        this.databaseEntity = databaseEntity;
        this.service = service;

        init();
    }

    private void init() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label label = new Label("Запись будет удалена. Вы уверены?");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button okButton = new Button("OK", event -> {
            service.deleteDatabaseEntity(databaseEntity);
            close();
        });
        Button cancelButton = new Button("Отмена", event -> close());
        horizontalLayout.addComponents(okButton, cancelButton);
        verticalLayout.addComponents(label, horizontalLayout);
        verticalLayout.addStyleName("warning-layout");
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.BOTTOM_RIGHT);

        this.setContent(verticalLayout);
        this.center();
        this.setModal(true);
    }
}

