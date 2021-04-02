package io.recheck.ui.components;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Data;


@Data
public class GridVerticalLayout extends VerticalLayout {

    private ExtendedGrid grid;
    private Label gridLabel;

    public GridVerticalLayout(String title, ExtendedGrid grid) {
        this.gridLabel = new Label(title);
        this.grid = grid;
        add(gridLabel, grid);
    }

}
