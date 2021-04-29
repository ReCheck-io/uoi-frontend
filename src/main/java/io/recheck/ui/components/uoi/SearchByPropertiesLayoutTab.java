package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.LayoutTabModel;
import io.recheck.ui.components.uoi.model.SearchByPropertiesModel;

public class SearchByPropertiesLayoutTab extends VerticalLayout implements LayoutTabModel<SearchByPropertiesModel> {

    private TextField keyTextField = new TextField();
    private TextField valueTextField = new TextField();

    private Button searchButton = new Button("Search");

    public SearchByPropertiesLayoutTab() {
        initComponents();
        initLayout();
    }

    private void initComponents() {
        keyTextField.setPlaceholder("Key");
        valueTextField.setPlaceholder("Value");
    }

    public void searchClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        searchButton.addClickListener(listener);
    }

    public SearchByPropertiesModel getData() {
        return new SearchByPropertiesModel(keyTextField.getValue(), valueTextField.getValue(), true);
    }

    public void setData(SearchByPropertiesModel data) {
        throw new UnsupportedOperationException();
    }

    public void clearData() {
        keyTextField.setValue("");
        valueTextField.setValue("");
    }

    public void initLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(keyTextField, valueTextField);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(horizontalLayout, searchButton);
    }
}
