package io.recheck.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.Arrays;

public class LayoutTabs extends Tabs {

    private LayoutTab[] layouts;

    public LayoutTabs(LayoutTab... layouts) {
        super(layouts);
        this.layouts = layouts;
        Arrays.asList(layouts).forEach(page -> page.getLayout().setVisible(false));

        Component layout = Arrays.asList(layouts).stream().findFirst().get().getLayout();
        ((LayoutTabModel) layout).clearData();
        layout.setVisible(true);

        addSelectedChangeListener(event -> selectedChangeEvent());
    }

    public void selectedChangeEvent() {
        Arrays.asList(layouts).forEach(page -> page.getLayout().setVisible(false));

        LayoutTab selectedLayoutTab = (LayoutTab) this.getSelectedTab();
        Component selectedLayout = selectedLayoutTab.getLayout();
        ((LayoutTabModel) selectedLayout).clearData();
        selectedLayout.setVisible(true);
    }

}
