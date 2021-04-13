package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.ui.components.StatedLayout;

public class PropertiesLayout extends VerticalLayout implements StatedLayout<PropertiesComponents> {

    private PropertiesComponents propertiesComponents;

    public PropertiesLayout(PropertiesComponents propertiesComponents) {
        this.propertiesComponents = propertiesComponents;
        initLayout(propertiesComponents);
    }

    public void toCreateState() {
        setVisible(false);
    }

    public void toUpdateState() {
        setVisible(true);
    }


    public PropertiesComponents getComponents() {
        return propertiesComponents;
    }

    public void initLayout(PropertiesComponents components) {
        HorizontalLayout propertiesLayoutButtons = new HorizontalLayout();
        propertiesLayoutButtons.add(propertiesComponents.getUpdateButton(), propertiesComponents.getAddEntryButton());

        VerticalLayout propertiesLayoutHeader = new VerticalLayout();
        propertiesLayoutHeader.add(propertiesComponents.getTitle(), propertiesComponents.getSubTitle(), propertiesLayoutButtons);

        add(propertiesLayoutHeader, propertiesComponents.getComponentMapLayout());

        toCreateState();
    }
}
