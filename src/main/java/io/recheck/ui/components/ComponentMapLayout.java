package io.recheck.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Data;

import java.util.Map;

@Data
public class ComponentMapLayout<K extends Component, V extends Component> extends VerticalLayout {

    private final ComponentMap<K, V> currentMap;

    public ComponentMapLayout(ComponentMap<K, V> currentMap) {
        this.currentMap = currentMap;
    }

    public void removeAll() {
        currentMap.clear();
        super.removeAll();
    }

    public void addEmpty() {
        add(null, null);
    }

    public void addAll(Map<String, String> m) {
        if (m != null && !m.isEmpty()) {
            for (Map.Entry<String,String> entry : m.entrySet()) {
                add(entry.getKey(), entry.getValue());
            }
        }
    }

    private void add(String key, String value) {
        K keyComponent = currentMap.put(key, value);
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(keyComponent, currentMap.get(keyComponent));
        this.add(layout);
    }

}
