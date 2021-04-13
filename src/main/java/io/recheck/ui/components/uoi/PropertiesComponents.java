package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.*;
import io.recheck.ui.components.uoi.model.RequestAccessModel;
import io.recheck.ui.components.uoi.model.PropertiesModel;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class PropertiesComponents implements ComponentsData<PropertiesModel> {

    private static final String PROPERTIES_UOI_TEXT = "Edit for : ";

    private H3 title = new H3("Properties");
    private Text subTitle = new Text(PROPERTIES_UOI_TEXT);

    private Button updateButton = new Button("Update Properties");
    private Button addEntryButton = new Button("Add");

    private final ComponentMapLayout componentMapLayout;

    private PropertiesModel data;

    public PropertiesComponents(ComponentMapEntryStrategy entryStrategy) {
        ComponentMap<TextField, Component> currentMap = new ComponentMap<>(entryStrategy);
        componentMapLayout = new ComponentMapLayout(currentMap);
        initListeners();
    }

    private void initListeners() {
        addEntryButton.addClickListener(e -> componentMapLayout.addEmpty());
    }

    public void updateClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        updateButton.addClickListener(listener);
    }

    public PropertiesModel getData() {
        data.setProperties(componentMapLayout.getCurrentMap().getMapEntries());
        return data;
    }

    public void setData(PropertiesModel data) {
        this.data = data;
        subTitle.setText(PROPERTIES_UOI_TEXT + data.getUoi());
        componentMapLayout.addAll(data.getProperties());
    }

    public void clearData() {
        data = null;
        componentMapLayout.removeAll();
    }


    class ComponentMapEntryStrategyValueButton extends ComponentMapEntryStrategyTextFields {
        @Override
        public Component newValueComponent(String value) {
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

        @Override
        public String getValueText(Component value) {
            String valueText = "";
            if (value instanceof TextField) {
                valueText = ((TextField) value).getValue();
            }
            else if (value instanceof ExtendedButton) {
                valueText = ((ExtendedButton) value).getBtnCustomProperties().get("href");
            }
            return valueText;
        }
    }

}
