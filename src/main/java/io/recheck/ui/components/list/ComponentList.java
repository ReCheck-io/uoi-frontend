package io.recheck.ui.components.list;

import com.vaadin.flow.component.Component;
import io.recheck.ui.components.list.elementConverter.Converter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ComponentList<C extends Component, D extends ListModel, E> {

    private List<C> currentList = new ArrayList<>();
    private D currentData;
    private Converter<C, D, E> converter;

    public ComponentList(Converter<C, D, E> converter) {
        this.converter = converter;
    }

    public D getData() {
        List<E> result = new ArrayList<>();

        currentList.forEach(e -> {
            E elementData = converter.toElementData(e);
            if (converter.elementDataIsNotEmpty(elementData)) {
                result.add(elementData);
            }
        });
        currentData.setList(result);

        return currentData;
    }

    public void setData(D data) {
        this.currentData = data;

        List<E> list = currentData.getList();
        list.forEach(e -> {
            add(data, e);
        });
    }

    public void clearData() {
        currentList.clear();
    }

    private void add(D data, E elementData) {
        C c = converter.elementDataIsNotEmpty(elementData) ? converter.toElementComponent(data, elementData) : converter.createEmptyComponent();
        currentList.add(c);
    }




}
