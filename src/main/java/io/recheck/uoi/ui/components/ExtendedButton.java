package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.button.Button;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExtendedButton extends Button {

    private Map<String, Object> btnCustomProperties = new HashMap<>();

    public ExtendedButton() {
    }

    public ExtendedButton(String text) {
        super(text);
    }

    public void putProperty(String key, Object value) {
        btnCustomProperties.put(key, value);
    }

    public Object getProperty(String key) {
        return btnCustomProperties.get(key);
    }
}
