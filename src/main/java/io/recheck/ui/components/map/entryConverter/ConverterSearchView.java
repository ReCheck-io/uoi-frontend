package io.recheck.ui.components.map.entryConverter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.ExtendedButton;
import io.recheck.ui.components.uoi.RequestAccessComponents;
import io.recheck.ui.components.uoi.RequestAccessLayout;
import io.recheck.ui.components.uoi.model.PropertiesModel;
import io.recheck.ui.components.uoi.model.RequestAccessModel;
import org.springframework.util.StringUtils;

public class ConverterSearchView extends ConverterKeyTextField<PropertiesModel, Component, String> {

    public Component toValueComponent(PropertiesModel data, String value) {
        Component valueField = new TextField();
        ((TextField) valueField).setPlaceholder("value");

        if (StringUtils.hasText(value)) {
            if (value.startsWith("http:") || value.startsWith("https:")) {
                valueField = new ExtendedButton("View Documents");
                ExtendedButton extendedButton = ((ExtendedButton) valueField);
                extendedButton.getBtnCustomProperties().put("href", value);
                extendedButton.addClickListener(e -> {
                    RequestAccessComponents requestAccessComponents = new RequestAccessComponents();
                    requestAccessComponents.setData(new RequestAccessModel("", "", data.getUoi(), value));
                    RequestAccessLayout requestAccessLayout = new RequestAccessLayout(requestAccessComponents);
                    requestAccessLayout.open();

                    requestAccessComponents.cancelClickListener(listener -> {
                        requestAccessComponents.clearData(); requestAccessLayout.close();
                    });

                    requestAccessComponents.confirmClickListener(listener -> {
                        requestAccessComponents.clearData(); requestAccessLayout.close();
                    });
                });
            } else {
                ((TextField) valueField).setValue(value);
            }
        }

        return valueField;
    }

    public Component createEmptyValueComponent() {
        TextField valueField = new TextField();
        valueField.setPlaceholder("value");
        return valueField;
    }

    public String toValueData(Component value) {
        String valueText = "";
        if (value instanceof TextField) {
            valueText = ((TextField) value).getValue();
        }
        else if (value instanceof ExtendedButton) {
            valueText = ((ExtendedButton) value).getBtnCustomProperties().get("href");
        }
        return valueText;
    }

    public boolean valueDataIsNotEmpty(String valueData) {
        return StringUtils.hasText(valueData);
    }

}
