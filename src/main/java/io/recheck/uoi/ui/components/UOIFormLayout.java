package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.entity.UOINode;
import io.recheck.uoi.ui.components.model.UOIFormModel;

public class UOIFormLayout extends Div {

    private Label subTitle = new Label();

    private H3 title = new H3();

    private TextField uoiField = new TextField();
    private TextField countryCodeField = new TextField();
    private ComboBox<LEVEL> levelField = new ComboBox<>();
    private TextField parentUOIField = new TextField();

    private Button createButton = new Button("Create");
    private Button updateButton = new Button("Update Parent UOI");
    private Button cancelButton = new Button("Cancel");

    public UOIFormLayout() {
        initLayout();
        initComponents();
    }

    private void initComponents() {
        levelField.setItems(LEVEL.values());
        initPlaceHolders();
    }

    private void initPlaceHolders() {
        countryCodeField.setPlaceholder("Country code");
        levelField.setPlaceholder("Choose level");
        parentUOIField.setPlaceholder("Parent UOI");
    }

    public void setData(UOIFormModel uoiFormModel) {
        uoiField.setValue(uoiFormModel.getUoi());
        countryCodeField.setValue(uoiFormModel.getCountryCode());
        levelField.setValue(uoiFormModel.getLevel());
        parentUOIField.setValue(uoiFormModel.getParentUOI());
    }

    public void clearData() {
        uoiField.setValue("");
        countryCodeField.setValue("");
        levelField.setValue(null);
        parentUOIField.setValue("");
    }

    public UOIFormModel getData() {
        return new UOIFormModel(uoiField.getValue(), countryCodeField.getValue(), levelField.getValue(), parentUOIField.getValue());
    }

    public void createClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        createButton.addClickListener(listener);
    }

    public void cancelClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        cancelButton.addClickListener(listener);
    }

    public void updateClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        updateButton.addClickListener(listener);
    }

    public void initLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(countryCodeField, levelField, parentUOIField);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(createButton, updateButton, cancelButton);

        add(title, subTitle, formLayout, buttonsLayout);

        setVisible(false);
    }

    public void toCreateState() {
        clearData();

        title.setText("Create new");
        subTitle.setVisible(false);

        countryCodeField.setEnabled(true);
        levelField.setEnabled(true);

        createButton.setVisible(true);
        updateButton.setVisible(false);

        setVisible(true);
    }

    public void toUpdateState(UOINode uoiNode) {
        clearData();
        setData(new UOIFormModel(uoiNode));

        title.setText("Update");
        subTitle.setText(uoiNode.getUoi());
        subTitle.setVisible(true);

        countryCodeField.setEnabled(false);
        levelField.setEnabled(false);

        updateButton.setVisible(true);
        createButton.setVisible(false);

        setVisible(true);
    }

}
