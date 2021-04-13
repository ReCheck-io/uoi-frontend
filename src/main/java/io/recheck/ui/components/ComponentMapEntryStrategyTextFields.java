package io.recheck.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.util.StringUtils;

public class ComponentMapEntryStrategyTextFields implements ComponentMapEntryStrategy {

    public TextField newKeyComponent(String key) {
        TextField keyField = new TextField();
        keyField.setPlaceholder("key");

        if (StringUtils.hasText(key)) {
            keyField.setValue(key);
            keyField.setEnabled(false);
        }

        return keyField;
    }

    @Override
    public String getKeyText(Component key) {
        return ((TextField) key).getValue();
    }

    public Component newValueComponent(String value) {
        TextField valueField = new TextField();
        valueField.setPlaceholder("value");

        if (StringUtils.hasText(value))
            valueField.setValue(value);

        return valueField;
    }

    @Override
    public String getValueText(Component value) {
        return ((TextField) value).getValue();
    }

}
