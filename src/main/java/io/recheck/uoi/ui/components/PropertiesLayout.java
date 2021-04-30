package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.uoi.ui.components.map.ComponentMap;
import io.recheck.uoi.ui.components.map.ComponentMapLayout;
import io.recheck.uoi.ui.components.model.PropertiesModel;

public class PropertiesLayout extends VerticalLayout {

    private H3 title = new H3("Properties");
    private Label subTitle = new Label();

    private Button updateButton = new Button("Update");
    private Button cancelButton = new Button("Cancel");
    private Button addEntryButton = new Button("Add");

    private final ComponentMapLayout componentMapLayout;
    private final ComponentMap componentMap;

    public PropertiesLayout(ComponentMap componentMap) {
        this.componentMap = componentMap;
        this.componentMapLayout = new ComponentMapLayout(componentMap);
        initLayout();
        initListeners();
    }

    private void initListeners() {
        addEntryButton.addClickListener(e -> componentMapLayout.addEmpty());
    }

    public void updateClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        updateButton.addClickListener(listener);
    }

    public void cancelClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        cancelButton.addClickListener(listener);
    }

    public PropertiesModel getData() {
        return (PropertiesModel) componentMap.getData();
    }

    public void setDataAndVisible(PropertiesModel data, boolean visible) {
        this.setData(data);
        this.setVisible(visible);
    }

    private void setData(PropertiesModel data) {
        clearData();

        subTitle.setText(data.getUoi());
        componentMap.setData(data);

        componentMapLayout.initLayout();
    }

    private void clearData() {
        componentMap.clearData();
        componentMapLayout.removeAll();
    }

    private void initLayout() {
        HorizontalLayout propertiesLayoutButtons = new HorizontalLayout();
        propertiesLayoutButtons.add(updateButton, cancelButton);

        VerticalLayout propertiesLayoutHeader = new VerticalLayout();
        propertiesLayoutHeader.add(title, subTitle, propertiesLayoutButtons, addEntryButton);

        add(propertiesLayoutHeader, componentMapLayout);

        setVisible(false);
    }
}
