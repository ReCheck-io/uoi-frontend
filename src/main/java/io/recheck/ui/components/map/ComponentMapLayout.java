package io.recheck.ui.components.map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ComponentMapLayout<KC extends Component, VC extends Component> extends VerticalLayout {

    private final ComponentMap<KC, VC, ?, ?, ?> componentMap;

    public ComponentMapLayout(ComponentMap<KC, VC, ?, ?, ?> componentMap) {
        this.componentMap = componentMap;
    }

    public void initLayout() {
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
