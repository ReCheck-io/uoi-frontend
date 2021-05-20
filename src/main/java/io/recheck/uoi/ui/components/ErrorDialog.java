package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.exceptionhandler.ApiError;

public class ErrorDialog extends Dialog {

    private Button closeButton = new Button("Close");

    private VerticalLayout content = new VerticalLayout();

    public ErrorDialog() {
        initLayout();
        initListeners();
    }

    public void initListeners() {
        closeButton.addClickListener(listener -> close());
    }

    public void initLayout() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        add(content);
        add(closeButton);
    }

    public void open(ApiError data) {
        content.removeAll();

        String title = data.getMessage();
        if (data.getSubErrors() != null && !data.getSubErrors().isEmpty()) {
            title += ": ";
        }
        content.add(new Label(title));

        if (data.getSubErrors() != null && !data.getSubErrors().isEmpty()) {
            data.getSubErrors().forEach(validationError -> {
                content.add(new Label(validationError.getField() + ": " + validationError.getMessage()));
            });
        }

        open();
    }

    public void open(String internalError) {
        content.removeAll();
        content.add(new Label(internalError));
        open();
    }

}
