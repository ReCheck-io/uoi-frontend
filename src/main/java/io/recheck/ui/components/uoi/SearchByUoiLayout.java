package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.ui.components.baseStructure.Layout;

public class SearchByUoiLayout extends VerticalLayout implements Layout<SearchByUoiComponents> {

    private SearchByUoiComponents components;

    public SearchByUoiLayout(SearchByUoiComponents components) {
        this.components = components;
        initLayout(components);
    }

    @Override
    public SearchByUoiComponents getComponents() {
        return components;
    }

    @Override
    public void initLayout(SearchByUoiComponents components) {
        add(components.getUoiField());
        add(components.getSearchButton());
    }
}
