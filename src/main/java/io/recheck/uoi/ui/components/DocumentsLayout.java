package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.uoi.entity.DocumentsSource;
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
        VerticalLayout propertiesLayoutHeader = new VerticalLayout();
        propertiesLayoutHeader.add(title, subTitle);

        add(propertiesLayoutHeader, sourceListLayout, closeButton);

        setVisible(false);
    }

    public void closeClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        closeButton.addClickListener(listener);
    }

    public void setDataAndVisible(DocumentsModel documentsModel, boolean visible) {
        this.setData(documentsModel);
        this.setVisible(visible);
    }

    private void setData(DocumentsModel documentsModel) {
        this.documentsModel = documentsModel;
        clearData();
        subTitle.setText(documentsModel.getUoi());
        documentsModel.getDocuments().forEach(d -> {
            sourceListLayout.add(toComponent(d));
        });
    }

    private void clearData() {
        sourceListLayout.removeAll();
    }

    private ExtendedButton toComponent(DocumentsSource source) {
        ExtendedButton extendedButton = new ExtendedButton("Access " + source.name());
        extendedButton.putProperty("DocumentsSource", source);

        extendedButton.addClickListener(e -> {
            RequestAccessLayout requestAccessLayout = new RequestAccessLayout();
            requestAccessLayout.setData(documentsModel.getUoi());
            requestAccessLayout.open();

            requestAccessLayout.cancelClickListener(listener -> {
                requestAccessLayout.clearData(); requestAccessLayout.close();
            });

            requestAccessLayout.confirmClickListener(listener -> {
                requestAccessLayout.clearData(); requestAccessLayout.close();
            });
        });

        return extendedButton;
    }
}
