package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.baseStructure.ComponentsData;
import io.recheck.ui.components.uoi.model.SearchByPropertiesModel;
import lombok.Data;

@Data
public class SearchByPropertiesComponents implements ComponentsData<SearchByPropertiesModel> {

    private TextField keyTextField = new TextField();
    private TextField valueTextField = new TextField();

    private Button searchButton = new Button("Search");

    public SearchByPropertiesComponents() {
        initComponents();
    }

    private void initComponents() {
        keyTextField.setPlaceholder("Key");
        valueTextField.setPlaceholder("Value");
    }

    public void searchClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        searchButton.addClickListener(listener);
    }

    @Override
    public SearchByPropertiesModel getData() {
        return new SearchByPropertiesModel(keyTextField.getValue(), valueTextField.getValue(), true);
    }

    @Override
    public void setData(SearchByPropertiesModel data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearData() {
        keyTextField.setValue("");
        valueTextField.setValue("");
    }
}
