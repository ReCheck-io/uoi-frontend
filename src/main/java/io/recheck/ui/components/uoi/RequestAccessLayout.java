package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.ui.components.StatedLayout;

public class RequestAccessLayout extends Dialog implements StatedLayout<RequestAccessComponents> {

    private RequestAccessComponents requestAccessComponents;

    public RequestAccessLayout(RequestAccessComponents requestAccessComponents) {
        this.requestAccessComponents = requestAccessComponents;
        initLayout(requestAccessComponents);
    }


    @Override
    public void toCreateState() {

    }

    @Override
    public void toUpdateState() {

    }

    @Override
    public RequestAccessComponents getComponents() {
        return requestAccessComponents;
    }

    @Override
    public void initLayout(RequestAccessComponents components) {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        add(new VerticalLayout(
                new Label("Please login to  access :"),
                new Label(components.getData().getUoi() + " on"),
                new Label(components.getData().getUrl())));

        add(new VerticalLayout(components.getUserNameField(), components.getPasswordField()),
                new HorizontalLayout(components.getConfirmButton(), components.getCancelButton()));
    }
}
