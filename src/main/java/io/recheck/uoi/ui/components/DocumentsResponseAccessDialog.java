package io.recheck.uoi.ui.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.rest.dto.CirdaxResponseWrapperDTO;
import org.springframework.util.StringUtils;

public class DocumentsResponseAccessDialog extends Dialog {

    private H3 title = new H3();

    private Button closeButton = new Button("Close");

    private VerticalLayout content = new VerticalLayout();

    public DocumentsResponseAccessDialog() {
        initLayout();
        initListeners();
    }

    public void initListeners() {
        closeButton.addClickListener(listener -> {
            close();
        });
    }

    public void initLayout() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        add(title);
        add(content);
        add(closeButton);
    }

    public void open(CirdaxResponseWrapperDTO data) {
        content.removeAll();

        if (data.getAccessResponse() != null) {
            title.setText("RequestUoiAccessToken response: ");
            content.add(data.getAccessResponse().getRawResponse());
        }

        else if (data.getDocumentsResponse() != null) {

            if (!data.getDocumentsResponse().getCirdaxDocumentsDTOList().isEmpty()) {
                title.setText("Documents");
                VerticalLayout vl = new VerticalLayout();
                data.getDocumentsResponse().getCirdaxDocumentsDTOList().forEach(e -> {
                    HorizontalLayout hl = new HorizontalLayout();
                    if (StringUtils.hasText(e.getDeepLinkUrl())) {
                        Anchor a = new Anchor(e.getDeepLinkUrl(), e.getDocumentFileName());
                        a.setTarget("_blank");
                        hl.add(a);
                    }
                    else {
                        hl.add(new Text(e.getDocumentFileName()));
                    }

                    vl.add(hl);
                });
                content.add(vl);
            }

            else {
                title.setText("UoiQueryDocuments response: ");
                content.add(data.getDocumentsResponse().getRawResponse());
            }
        }

        open();
    }
}
