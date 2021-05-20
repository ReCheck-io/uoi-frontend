package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.uoi.entity.DocumentsSource;
import io.recheck.uoi.ui.components.model.DocumentsModel;

import java.util.ArrayList;
import java.util.List;


public class DocumentsLayout extends VerticalLayout {

    private final String SOURCE_KEY = "source";

    private H3 title = new H3("Documents");
    private Label subTitle = new Label();

    private Button closeButton = new Button("Close");
    private VerticalLayout sourceListLayout = new VerticalLayout();

    private List<ExtendedButton> accessButtons = new ArrayList<>();

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

    public void updateAccessButtons(boolean loggedAccountPresent) {
        accessButtons.forEach(btn -> {
            String buttonText = "Log in to access " + ((DocumentsSource) btn.getProperty(SOURCE_KEY)).name();
            if (loggedAccountPresent) {
                buttonText = "Access " + ((DocumentsSource) btn.getProperty(SOURCE_KEY)).name();
            }
            btn.setText(buttonText);
        });
    }

    private void setData(DocumentsModel documentsModel, DocumentsListeners documentsListeners) {
        clearData();
        subTitle.setText(documentsModel.getUoi());
        documentsModel.getDocuments().forEach(source -> {

            ExtendedButton accessButton = new ExtendedButton();
            accessButton.putProperty(SOURCE_KEY, source);

            accessButton.addClickListener(e -> {
                documentsListeners.getDocumentsAccess(source);
            });
            sourceListLayout.add(accessButton);

            accessButtons.add(accessButton);
        });
    }

    private void clearData() {
        sourceListLayout.removeAll();
    }

}
