package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.uoi.ui.components.model.RequestAccessModel;

public class DocumentsRequestAccessDialog extends Dialog {

    private TextField userNameField = new TextField();
    private Button confirmButton = new Button("Confirm");
    private Button cancelButton = new Button("Cancel");

    private Label uoiLabel = new Label();

    public DocumentsRequestAccessDialog() {
        initLayout();
        initListeners();
    }

    public void confirmClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        confirmButton.addClickListener(listener);
    }

    public RequestAccessModel getData() {
        return new RequestAccessModel(userNameField.getValue(), uoiLabel.getText());
    }

    public void setData(String uoi) {
        uoiLabel.setText(uoi);
    }

    public void clearData() {
        userNameField.setValue("");
        userNameField.setPlaceholder("User name");
    }

    public void initLayout() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        add(new VerticalLayout(new Label("Please enter a user name to request access documents for :"), uoiLabel));

        add(new VerticalLayout(userNameField), new HorizontalLayout(confirmButton, cancelButton));
    }

    public void initListeners() {
        cancelButton.addClickListener(listener -> {
            close();
        });
    }

    public void open(String uoi) {
        setData(uoi);
        clearData();
        super.open();
    }
}
