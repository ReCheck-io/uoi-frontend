package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.uoi.ui.components.model.DocumentsModel;


public class DocumentsLayout extends VerticalLayout {

    private H3 title = new H3("Documents");
    private Label subTitle = new Label();

    private Button closeButton = new Button("Close");
    private VerticalLayout sourceListLayout = new VerticalLayout();

    private DocumentsModel documentsModel;

    public DocumentsLayout() {
        initLayout();
    }

    public void initLayout() {
        add(new VerticalLayout(title, subTitle), sourceListLayout, closeButton);
        setVisible(false);
    }

    public void closeClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        closeButton.addClickListener(listener);
    }

    public void setDataAndVisible(DocumentsModel documentsModel, DocumentsListeners documentsListeners, boolean visible) {
        this.setData(documentsModel, documentsListeners);
        this.setVisible(visible);
    }

    private void setData(DocumentsModel documentsModel, DocumentsListeners documentsListeners) {
        this.documentsModel = documentsModel;
        clearData();
        subTitle.setText(documentsModel.getUoi());
        documentsModel.getDocuments().forEach(source -> {
            Button accessButton = new Button("Access " + source.name());
            accessButton.addClickListener(e -> {
                documentsListeners.getDocumentsAccess(source);
            });
            sourceListLayout.add(accessButton);
        });
    }

    private void clearData() {
        sourceListLayout.removeAll();
    }

}
