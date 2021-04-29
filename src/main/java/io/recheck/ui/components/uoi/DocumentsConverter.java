package io.recheck.ui.components.uoi;

import io.recheck.ui.components.ExtendedButton;
import io.recheck.ui.components.list.elementConverter.Converter;
import io.recheck.ui.components.uoi.model.DocumentsModel;
import io.recheck.ui.entity.DocumentsSource;


public class DocumentsConverter implements Converter<ExtendedButton, DocumentsModel, DocumentsSource> {

    public ExtendedButton toElementComponent(DocumentsModel data, DocumentsSource elementData) {
        ExtendedButton button = new ExtendedButton("Access " + elementData.name());
        button.putProperty("DocumentsSource", elementData);
        return button;
    }

    public ExtendedButton createEmptyComponent() {
        return new ExtendedButton("");
    }

    public DocumentsSource toElementData(ExtendedButton elementComponent) {
        return DocumentsSource.valueOf((String) elementComponent.getProperty("DocumentsSource"));
    }

    public boolean elementDataIsNotEmpty(DocumentsSource elementData) {
        return elementData != null;
    }
}
