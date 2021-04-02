package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.ComponentsData;
import io.recheck.ui.components.uoi.model.SearchByPropertiesModel;
import lombok.Data;

@Data
public class SearchByPropertiesComponents implements ComponentsData<SearchByPropertiesModel> {

    private TextField keyTextField = new TextField();
    private TextField valueTextField = new TextField();
    private Checkbox checkbox = new Checkbox("Show properties");

    private Button searchButton = new Button("Search");

    public SearchByPropertiesComponents() {
        initComponents();
    }

    private void initComponents() {
        keyTextField.setPlaceholder("Key");
        valueTextField.setPlaceholder("Value");
        checkbox.setValue(false);
    }

    public void searchClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        searchButton.addClickListener(listener);
    }

    @Override
    public SearchByPropertiesModel getData() {
        return new SearchByPropertiesModel(keyTextField.getValue(), valueTextField.getValue(), checkbox.getValue());
    }

    @Override
    public void setData(SearchByPropertiesModel data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearData() {
        keyTextField.setValue("");
        valueTextField.setValue("");
        checkbox.setValue(false);
    }
}
