package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PropertiesLayout extends VerticalLayout {

    private PropertiesComponents propertiesComponents;

    public PropertiesLayout(PropertiesComponents propertiesComponents) {
        this.propertiesComponents = propertiesComponents;

        HorizontalLayout propertiesLayoutButtons = new HorizontalLayout();
        propertiesLayoutButtons.add(propertiesComponents.getUpdateButton(), propertiesComponents.getAddEntryButton());

        VerticalLayout propertiesLayoutHeader = new VerticalLayout();
        propertiesLayoutHeader.add(propertiesComponents.getTitle(), propertiesComponents.getSubTitle(), propertiesLayoutButtons);

        add(propertiesLayoutHeader, propertiesComponents.getTextFieldMap());

        toCreateState();
    }

    public void toCreateState() {
        setVisible(false);
    }

    public void toUpdateState() {
        setVisible(true);
    }

}
