package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import io.recheck.ui.components.baseStructure.ComponentsData;
import io.recheck.ui.components.map.ComponentMap;
import io.recheck.ui.components.map.ComponentMapLayout;
import io.recheck.ui.components.uoi.model.PropertiesModel;
import lombok.Data;

@Data
public class PropertiesComponents implements ComponentsData<PropertiesModel> {

    private static final String PROPERTIES_UOI_TEXT = "Edit for : ";

    private H3 title = new H3("Properties");
    private Text subTitle = new Text(PROPERTIES_UOI_TEXT);

    private Button updateButton = new Button("Update Properties");
    private Button addEntryButton = new Button("Add");

    private final ComponentMapLayout componentMapLayout;

    private PropertiesModel data;

    public PropertiesComponents(ComponentMap componentMap) {
        componentMapLayout = new ComponentMapLayout(componentMap);
        initListeners();
    }

    private void initListeners() {
        addEntryButton.addClickListener(e -> componentMapLayout.addEmpty());
    }

    public void updateClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        updateButton.addClickListener(listener);
    }

    public PropertiesModel getData() {
        data.setProperties(componentMapLayout.getComponents().getData().getMap());
        return data;
    }

    public void setData(PropertiesModel data) {
        this.data = data;
        subTitle.setText(PROPERTIES_UOI_TEXT + data.getUoi());
        componentMapLayout.getComponents().setData(data);
        componentMapLayout.initLayout(componentMapLayout.getComponents());
    }

    public void clearData() {
        data = null;
        componentMapLayout.removeAll();
    }

}
