package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.baseStructure.ComponentsData;
import io.recheck.ui.components.uoi.model.RequestAccessModel;
import lombok.Data;

@Data
public class RequestAccessComponents implements ComponentsData<RequestAccessModel> {

    private TextField userNameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button confirmButton = new Button("Confirm");
    private Button cancelButton = new Button("Cancel");

    private RequestAccessModel data;


    public void confirmClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        confirmButton.addClickListener(listener);
    }

    public void cancelClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        cancelButton.addClickListener(listener);
    }

    @Override
    public RequestAccessModel getData() {
        return data;
    }

    @Override
    public void setData(RequestAccessModel data) {
        this.data = data;
    }

    @Override
    public void clearData() {
        userNameField.setValue("");
        passwordField.setValue("");
        data = null;
    }
}
