package io.recheck.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.Arrays;

public class LayoutTabs extends Tabs {

    public LayoutTabs(LayoutTab... layouts) {
        super(layouts);
        Arrays.asList(layouts).forEach(page -> ((Component) page.getLayout()).setVisible(false));

        Layout layout = Arrays.asList(layouts).stream().findFirst().get().getLayout();
        layout.getComponents().clearData();
        ((Component) layout).setVisible(true);

        addSelectedChangeListener(event -> {
            Arrays.asList(layouts).forEach(page -> ((Component) page.getLayout()).setVisible(false));

            LayoutTab selectedLayoutTab = (LayoutTab) this.getSelectedTab();
            Layout selectedLayout = selectedLayoutTab.getLayout();
            selectedLayout.getComponents().clearData();
            ((Component) selectedLayout).setVisible(true);
        });
    }

}
