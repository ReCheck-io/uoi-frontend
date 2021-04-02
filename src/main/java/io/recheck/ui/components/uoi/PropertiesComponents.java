package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import io.recheck.ui.components.TextFieldMap;
import io.recheck.ui.components.uoi.model.ComponentsData;
import io.recheck.ui.components.uoi.model.PropertiesModel;
import lombok.Data;

@Data
public class PropertiesComponents implements ComponentsData<PropertiesModel> {

    private static final String PROPERTIES_UOI_TEXT = "Edit for : ";

    private H3 title = new H3("Properties");
    private Text subTitle = new Text(PROPERTIES_UOI_TEXT);

    private Button updateButton = new Button("Update Properties");
    private Button addEntryButton = new Button("Add");

    private TextFieldMap textFieldMap = new TextFieldMap();

    private PropertiesModel data;

    public PropertiesComponents() {
        initListeners();
    }

    private void initListeners() {
        addEntryButton.addClickListener(e -> textFieldMap.putEmpty());
    }

    public void updateClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        updateButton.addClickListener(listener);
    }

    public void setData(PropertiesModel data) {
        this.data = data;
        subTitle.setText(PROPERTIES_UOI_TEXT + data.getUoi());
        textFieldMap.putMapEntries(data.getProperties());
    }

    public PropertiesModel getData() {
        data.setProperties(textFieldMap.getMapEntries());
        return data;
    }

    public void removeData() {
        data = null;
        textFieldMap.removeAll();
    }

}
