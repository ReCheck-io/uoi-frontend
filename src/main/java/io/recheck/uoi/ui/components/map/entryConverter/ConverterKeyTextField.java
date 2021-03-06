package io.recheck.uoi.ui.components.map.entryConverter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.uoi.ui.components.map.MapModel;
import org.springframework.util.StringUtils;

public abstract class ConverterKeyTextField<D extends MapModel, VC extends Component, VD> implements Converter<TextField, VC, D, String, VD> {

    public TextField toKeyComponent(D data, String key) {
        TextField keyField = createEmptyKeyComponent();
        keyField.setValue(key);
        keyField.setEnabled(false);
        return keyField;
    }

    public TextField createEmptyKeyComponent() {
        TextField keyField = new TextField();
        keyField.setPlaceholder("key");
        return keyField;
    }

    public String toKeyData(TextField key) {
        return key.getValue();
    }

    public boolean keyDataIsNotEmpty(String keyData) {
        return StringUtils.hasText(keyData);
    }

}
