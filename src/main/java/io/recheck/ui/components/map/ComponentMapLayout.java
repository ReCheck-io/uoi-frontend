package io.recheck.ui.components.map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import io.recheck.ui.components.baseStructure.Layout;


public class ComponentMapLayout<KC extends Component, VC extends Component> extends VerticalLayout implements Layout<ComponentMap<KC, VC, ?, ?, ?>> {

    private final ComponentMap<KC, VC, ?, ?, ?> componentMap;

    public ComponentMapLayout(ComponentMap<KC, VC, ?, ?, ?> componentMap) {
        this.componentMap = componentMap;
        initLayout(componentMap);
    }

    public void removeAll() {
        componentMap.clearData();
        super.removeAll();
    }

    public ComponentMap getComponents() {
        return componentMap;
    }

    public void initLayout(ComponentMap<KC, VC, ?, ?, ?> componentMap) {
        componentMap.getCurrentMap().forEach((key, value) -> {
            add(key, value);
        });
    }

    public void addEmpty() {
        KC k = componentMap.putEmpty();
        add(k, componentMap.getCurrentMap().get(k));
    }

    private void add(KC key, VC value) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(key, value);
        add(layout);
    }

}
