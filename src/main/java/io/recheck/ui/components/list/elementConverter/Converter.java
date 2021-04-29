package io.recheck.ui.components.list.elementConverter;

import com.vaadin.flow.component.Component;
import io.recheck.ui.components.list.ListModel;

public interface Converter<C extends Component, D extends ListModel, E> {

    C toElementComponent(D data, E elementData);
    C createEmptyComponent();
    E toElementData(C elementComponent);
    boolean elementDataIsNotEmpty(E elementData);

}
