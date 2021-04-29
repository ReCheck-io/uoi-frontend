package io.recheck.ui.components.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ComponentListVerticalLayout<C extends Component> extends VerticalLayout {

    private ComponentList<C, ?, ?> componentsList;

    public ComponentListVerticalLayout(ComponentList<C, ?, ?> componentsList) {
        this.componentsList = componentsList;
    }

    public void initLayout() {
        componentsList.getCurrentList().forEach(e -> {
            add(e);
        });
    }

}
