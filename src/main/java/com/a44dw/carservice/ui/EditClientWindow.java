package com.a44dw.carservice.ui;

import com.a44dw.carservice.entities.*;
import com.a44dw.carservice.services.*;
import com.vaadin.ui.*;

class EditClientWindow extends Window {

    private TextField surname;
    private TextField name;
    private TextField middleName;
    private NumberField telNum;
    private FormLayout nameForm;
    private Validator validator;
    private Client client;
    private ClientService service;

    EditClientWindow(Client client) {
        super("Клиент");
        this.client = client;

        init();
    }

    private void init() {
        service = ClientService.getInstance();

        nameForm = new FormLayout();

        surname = new TextField("Фамилия", client.getSurname());
        name = new TextField("Имя", client.getName());
        middleName = new TextField("Отчество", client.getMiddleName());
        telNum = new NumberField("Телефон", client.getTelNumber());

        validator = new Validator();

        nameForm.addComponent(surname);
        nameForm.addComponent(name);
        nameForm.addComponent(middleName);
        nameForm.addComponent(telNum);
        nameForm.addComponent(new Label("телефонный номер должен содержать 7 или 11 цифр"));

        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(new Button("OK", event -> {
            if(validator.validateFields()) {
                client.setName(formatString(name.getValue().trim()));
                client.setSurname(formatString(surname.getValue().trim()));
                client.setMiddleName(middleName.getValue().length() < 1 ?
                        middleName.getValue() :
                        formatString(middleName.getValue().trim()));
                client.setTelNumber(telNum.getValue());
                if((client.getClientId() > -1)||(!service.checkIfExist(client))) {
                    service.doDatabaseEntityOperation(client);
                    close();
                } else {
                    Notification.show("Клиент с такими данными уже существует!",
                            Notification.Type.WARNING_MESSAGE);
                }
            }
        }));
        layout.addComponent(new Button("Отмена", event -> close()));

        nameForm.addComponent(layout);

        this.setContent(nameForm);
        this.center();
        this.setWidth("600px");
        this.setModal(true);
    }

    private String formatString(String s) {
        String result = s.toLowerCase();
        return result.substring(0,1).toUpperCase() + result.substring(1);
    }

    private class Validator {

        String nameRegex = "[а-яА-Яa-zA-Z\\-Ёё]*";
        String errorField;

        boolean validateFields() {

            String surnameValue = surname.getValue().trim();
            String nameValue = name.getValue().trim();
            int telNumValueLength = telNum.getValue().trim().length();

            boolean result = true;

            if((surnameValue.length() == 0)||(surnameValue.contains(" "))||(!surnameValue.matches(nameRegex))) {
                errorField = "\"Фамилия\"";
                result = false;
            }
            else if(((nameValue.length() == 0)||nameValue.contains(" "))||(!nameValue.matches(nameRegex))) {
                errorField = "\"Имя\"";
                result = false;
            }
            else if((telNumValueLength != 7)&&(telNumValueLength != 11)) {
                errorField = "\"Телефон\"";
                result = false;
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
