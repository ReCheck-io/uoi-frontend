package io.recheck.ui.components;

import com.vaadin.flow.component.grid.Grid;
import io.recheck.ui.components.baseStructure.ClickListener;

import java.util.List;

public class ExtendedGrid<T> extends Grid<T> {

    private List<T> dataProvider;
    protected ClickListener<T> clickListener;

    public ExtendedGrid(List<T> dataProvider) {
        this.dataProvider = dataProvider;
        setSelectionMode(SelectionMode.SINGLE);
        setItems(dataProvider);
    }

    public void addItemClickListener(ClickListener<T> clickListener) {
        this.clickListener = clickListener;
        super.addItemClickListener(event -> {
            T item = event.getItem();
            clickListener.onClick(item);
        });
    }

    public void addItem(T item) {
        dataProvider.add(item);
        refreshUI();
    }


    public void refreshUI() {
        getDataProvider().refreshAll();
    }

    public T getSelectedItem() {
        return getSelectedItems().iterator().next();
    }

}
