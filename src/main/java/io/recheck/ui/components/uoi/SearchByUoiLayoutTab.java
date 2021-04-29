package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.LayoutTabModel;
import io.recheck.ui.components.uoi.model.SearchByUoiModel;

public class SearchByUoiLayoutTab extends VerticalLayout implements LayoutTabModel<SearchByUoiModel> {

    private TextField uoiField = new TextField();

    private Button searchButton = new Button("Search");

    public SearchByUoiLayoutTab() {
        initComponents();
        initLayout();
    }

    private void initComponents() {
        uoiField.setPlaceholder("UOI");
    }

    public void searchClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        searchButton.addClickListener(listener);
    }

    public SearchByUoiModel getData() {
        return new SearchByUoiModel(uoiField.getValue());
    }

    public void setData(SearchByUoiModel data) {
        throw new UnsupportedOperationException();
    }

    public void clearData() {
        uoiField.setValue("");
    }

    public void initLayout() {
        add(uoiField, searchButton);
    }
}
