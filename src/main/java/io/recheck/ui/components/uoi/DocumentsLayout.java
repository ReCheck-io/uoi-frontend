package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.ui.components.list.ComponentList;
import io.recheck.ui.components.list.ComponentListVerticalLayout;
import io.recheck.ui.components.uoi.model.DocumentsModel;

public class DocumentsLayout extends VerticalLayout {

    private H3 title = new H3("Documents");
    private Label subTitle = new Label();

    private Button closeButton = new Button("Close");

    private ComponentList componentList;
    private ComponentListVerticalLayout componentListLayout;

    public DocumentsLayout(ComponentList componentList) {
        this.componentList = componentList;
        this.componentListLayout = new ComponentListVerticalLayout(componentList);
        initLayout();
    }

    public void initLayout() {
        VerticalLayout propertiesLayoutHeader = new VerticalLayout();
        propertiesLayoutHeader.add(title, subTitle);

        add(propertiesLayoutHeader, componentListLayout, closeButton);

        setVisible(false);
    }

    public void closeClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        closeButton.addClickListener(listener);
    }

    public void setDataAndVisible(DocumentsModel data, boolean visible) {
        this.setData(data);
        this.setVisible(visible);
    }

    private void setData(DocumentsModel data) {
        clearData();

        subTitle.setText(data.getUoi());
        componentList.setData(data);

        componentListLayout.initLayout();
    }

    private void clearData() {
        componentList.clearData();
        componentListLayout.removeAll();
    }
}
