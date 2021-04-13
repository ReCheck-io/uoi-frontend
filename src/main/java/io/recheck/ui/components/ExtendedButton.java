package io.recheck.ui.components;

import com.vaadin.flow.component.button.Button;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExtendedButton extends Button {

    private Map<String, String> btnCustomProperties = new HashMap<>();

    public ExtendedButton(String text) {
        super(text);
    }
}
