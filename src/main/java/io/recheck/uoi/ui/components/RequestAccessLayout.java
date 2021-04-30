package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.uoi.ui.components.model.RequestAccessModel;

public class RequestAccessLayout extends Dialog {

    private TextField userNameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button confirmButton = new Button("Confirm");
    private Button cancelButton = new Button("Cancel");

    private Label uoiLabel = new Label();

    public RequestAccessLayout() {
        initLayout();
    }

    public void confirmClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        confirmButton.addClickListener(listener);
    }

    public void cancelClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        cancelButton.addClickListener(listener);
    }

    public RequestAccessModel getData() {
        return new RequestAccessModel(uoiLabel.getText(), userNameField.getValue(), passwordField.getValue());
    }

    public void setData(String uoi) {
        uoiLabel.setText(uoi);
    }

    public void clearData() {
        userNameField.setValue("");
        passwordField.setValue("");
    }

    public void initLayout() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        add(new VerticalLayout(new Label("Please login to  access documents for :"), uoiLabel));

        add(new VerticalLayout(userNameField, passwordField),
                new HorizontalLayout(confirmButton, cancelButton));
    }
}
