package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;
import lombok.Data;

@Data
public class LayoutTab extends Tab {

    private Component layout;

    public LayoutTab(Component layout, String title) {
        super(title);
        this.layout = layout;
    }

}
