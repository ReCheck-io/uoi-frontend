package io.recheck.ui.components;

import com.vaadin.flow.component.tabs.Tab;
import io.recheck.ui.components.baseStructure.Layout;
import lombok.Data;

@Data
public class LayoutTab extends Tab {

    private Layout layout;

    public LayoutTab(Layout layout, String title) {
        super(title);
        this.layout = layout;
    }

}
