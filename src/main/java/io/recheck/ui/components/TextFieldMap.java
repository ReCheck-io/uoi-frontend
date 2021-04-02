package io.recheck.ui.components;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TextFieldMap extends VerticalLayout {

    private Map<TextField, TextField> currentTextFieldsMap = new HashMap<>();

    public void removeAll() {
        currentTextFieldsMap.clear();
        super.removeAll();
    }

    //get key/value as Map
    public Map<String, String> getMapEntries() {
        Map<String,String> textFieldsMap = new HashMap<>();

        for (Map.Entry<TextField,TextField> entry : currentTextFieldsMap.entrySet()) {
            String key = entry.getKey().getValue();
            String value = entry.getValue().getValue();
            if (StringUtils.hasText(key) && StringUtils.hasText(value)) {
                textFieldsMap.put(key, value);
            }
        }

        return textFieldsMap;
    }
    
    public void putMapEntries(Map<String, String> textFieldsMap) {
        if (textFieldsMap != null && !textFieldsMap.isEmpty()) {
            for (Map.Entry<String,String> entry : textFieldsMap.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void putEmpty() {
        put(null, null);
    }

    private void put(String key, String value) {
        TextField keyField = new TextField();
        keyField.setPlaceholder("key");
        TextField valueField = new TextField();
        valueField.setPlaceholder("value");

        if (StringUtils.hasText(key))
            keyField.setValue(key);
        if (StringUtils.hasText(value))
            valueField.setValue(value);
        
        currentTextFieldsMap.put(keyField, valueField);

        HorizontalLayout textFieldsLayout = new HorizontalLayout();
        textFieldsLayout.add(keyField, valueField);

        this.add(textFieldsLayout);
    }

}
