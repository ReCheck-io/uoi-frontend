package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.ui.components.Layout;

public class SearchByPropertiesLayout extends VerticalLayout implements Layout<SearchByPropertiesComponents> {

    private SearchByPropertiesComponents components;

    public SearchByPropertiesLayout(SearchByPropertiesComponents components) {
        this.components = components;
        initLayout(components);
    }

    @Override
    public SearchByPropertiesComponents getComponents() {
        return components;
    }

    @Override
    public void initLayout(SearchByPropertiesComponents components) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(components.getKeyTextField(), components.getValueTextField(), components.getCheckbox());
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(horizontalLayout, components.getSearchButton());
    }
}
