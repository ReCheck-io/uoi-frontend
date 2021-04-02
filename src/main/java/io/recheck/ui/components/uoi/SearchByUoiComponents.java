package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.ui.components.ComponentsData;
import io.recheck.ui.components.uoi.model.SearchByUoiModel;
import lombok.Data;

@Data
public class SearchByUoiComponents implements ComponentsData<SearchByUoiModel> {

    private TextField uoiField = new TextField();

    private Button searchButton = new Button("Search");

    public SearchByUoiComponents() {
        initComponents();
    }

    private void initComponents() {
        uoiField.setPlaceholder("UOI");
    }

    public void searchClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        searchButton.addClickListener(listener);
    }

    @Override
    public SearchByUoiModel getData() {
        return new SearchByUoiModel(uoiField.getValue());
    }

    @Override
    public void setData(SearchByUoiModel data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearData() {
        uoiField.setValue("");
    }
}
