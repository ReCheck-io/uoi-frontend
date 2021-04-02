package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.ComponentsData;
import io.recheck.ui.components.uoi.model.UOIFormModel;
import io.recheck.ui.entity.LEVEL;
import lombok.Data;


@Data
public class UOIFormComponents implements ComponentsData<UOIFormModel> {

    private TextField countryCodeField = new TextField();
    private ComboBox<LEVEL> levelField = new ComboBox<>();
    private TextField parentUOIField = new TextField();

    private Button createButton = new Button("Create");
    private Button updateButton = new Button("Update Parent UOI");
    private Button cancelButton = new Button("Cancel");

    public UOIFormComponents() {
        initComponents();
    }

    private void initComponents() {
        countryCodeField.setPlaceholder("Country code");
        levelField.setItems(LEVEL.values());
        levelField.setPlaceholder("Choose level");
        parentUOIField.setPlaceholder("Parent UOI");
    }

    public void setData(UOIFormModel uoiFormModel) {
        countryCodeField.setValue(uoiFormModel.getCountryCode());
        levelField.setValue(uoiFormModel.getLevel());
        parentUOIField.setValue(uoiFormModel.getParentUOI());
    }

    public void clearData() {
        countryCodeField.setValue("");
        levelField.setValue(null);
        parentUOIField.setValue("");
    }

    public UOIFormModel getData() {
        return new UOIFormModel(countryCodeField.getValue(), levelField.getValue(), parentUOIField.getValue());
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


}
