package io.recheck.ui.components;

import com.vaadin.flow.component.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ComponentMap<K extends Component, V extends Component> {

    private ComponentMapEntryStrategy<K,V> entryStrategy;
    private HashMap<K, V> currentMap = new HashMap<>();

    public ComponentMap(ComponentMapEntryStrategy entryStrategy) {
        this.entryStrategy = entryStrategy;
    }

    public Map<String, String> getMapEntries() {
        Map<String,String> textFieldsMap = new HashMap<>();

        forEachToStringImpl((key, value) -> {
            if (StringUtils.hasText(key) && StringUtils.hasText(value)) {
                textFieldsMap.put(key, value);
            }
        });

        return textFieldsMap;
    }

    public void clear() {
        currentMap.clear();
    }

    private void forEachToStringImpl(BiConsumer<String, String> action) {
        currentMap.forEach((key,value) -> {
            action.accept(entryStrategy.getKeyText(key), entryStrategy.getValueText(value));
        });
    }

    public K put(String key, String value) {
        K k = this.entryStrategy.newKeyComponent(key);
        V v = this.entryStrategy.newValueComponent(value);
        currentMap.put(k, v);
        return k;
    }

    public V get(K k) {
        return currentMap.get(k);
    }

}
