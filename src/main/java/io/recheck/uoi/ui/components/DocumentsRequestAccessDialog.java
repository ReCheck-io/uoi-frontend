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

public class DocumentsRequestAccessDialog extends Dialog {

    private TextField userNameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button confirmButton = new Button("Confirm");
    private Button cancelButton = new Button("Cancel");

    private Label uoiLabel = new Label();
    private Label invalidAccount = new Label("Invalid account");

    public DocumentsRequestAccessDialog() {
        initLayout();
        initListeners();
    }

    public void confirmClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        confirmButton.addClickListener(listener);
    }

    public RequestAccessModel getData() {
        return new RequestAccessModel(userNameField.getValue(), passwordField.getValue(), uoiLabel.getText());
    }

    public void setData(String uoi) {
        uoiLabel.setText(uoi);
    }

    public void clearData() {
        invalidAccount.setVisible(false);
        userNameField.setValue("");
        passwordField.setValue("");
    }

    public void initLayout() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        invalidAccount.setClassName("redText");

        add(new VerticalLayout(new Label("Please login to  access documents for :"), uoiLabel, invalidAccount));

        add(new VerticalLayout(userNameField, passwordField),
                new HorizontalLayout(confirmButton, cancelButton));
    }

    public void initListeners() {
        cancelButton.addClickListener(listener -> {
            close();
        });
    }

    public void invalidAccount() {
        invalidAccount.setVisible(true);
    }

    public void open(String uoi) {
        setData(uoi);
        clearData();
        super.open();
    }
}
