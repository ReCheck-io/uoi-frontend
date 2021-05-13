package io.recheck.uoi.ui.components.map.entryConverter;

import com.vaadin.flow.component.textfield.TextField;
import io.recheck.uoi.ui.components.map.MapModel;
import org.springframework.util.StringUtils;

public class ConverterKeyValueTextField<D extends MapModel> extends ConverterKeyTextField<D, TextField, String> {

    public TextField toValueComponent(D data, String value) {
        TextField valueField = createEmptyValueComponent();
        valueField.setValue(value);
        return valueField;
    }

    public TextField createEmptyValueComponent() {
        TextField valueField = new TextField();
        valueField.setPlaceholder("value");
        return valueField;
    }

    public String toValueData(TextField value) {
        return value.getValue();
    }

    public boolean valueDataIsNotEmpty(String valueData) {
        return StringUtils.hasText(valueData);
    }
}
