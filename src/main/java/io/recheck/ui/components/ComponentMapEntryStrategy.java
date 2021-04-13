package io.recheck.ui.components;

import com.vaadin.flow.component.Component;


public interface ComponentMapEntryStrategy<K extends Component, V extends Component> {

    K newKeyComponent(String key);
    String getKeyText(K key);

    V newValueComponent(String value);
    String getValueText(V value);

}
