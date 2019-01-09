package com.a44dw.carservice.ui;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.TextField;

public class NumberField extends TextField implements FieldEvents.TextChangeListener {
    String value;

    public NumberField(String caption, String v) {
        super(caption, v);
        this.value = v;

        setImmediate(true);
        setTextChangeEventMode(TextChangeEventMode.EAGER);
        addTextChangeListener(this);
    }

    @Override
    public void textChange(FieldEvents.TextChangeEvent event) {
        String text = event.getText();
        if(text.length() == 0) value = text;
        try {
            new Long(text);
            value = text;
        } catch (NumberFormatException e) {
            setValue(value);
        }
    }
}
