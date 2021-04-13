package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import io.recheck.ui.components.baseStructure.StatedLayout;

public class UOIFormLayout extends Div implements StatedLayout<UOIFormComponents> {

    private UOIFormComponents uoiFormComponents;

    public UOIFormLayout(UOIFormComponents uoiFormComponents) {
        this.uoiFormComponents = uoiFormComponents;
        initLayout(uoiFormComponents);
    }

    public void toCreateState() {

        uoiFormComponents.getCountryCodeField().setEnabled(true);
        uoiFormComponents.getLevelField().setEnabled(true);

        uoiFormComponents.getCreateButton().setVisible(true);
        uoiFormComponents.getUpdateButton().setVisible(false);
        uoiFormComponents.getCancelButton().setVisible(false);
    }

    public void toUpdateState() {

        uoiFormComponents.getUpdateButton().setVisible(true);
        uoiFormComponents.getCancelButton().setVisible(true);
        uoiFormComponents.getCreateButton().setVisible(false);

        uoiFormComponents.getCountryCodeField().setEnabled(false);
        uoiFormComponents.getLevelField().setEnabled(false);
    }

    @Override
    public UOIFormComponents getComponents() {
        return uoiFormComponents;
    }

    @Override
    public void initLayout(UOIFormComponents components) {
        FormLayout formLayout = new FormLayout();
        formLayout.add(uoiFormComponents.getCountryCodeField(),
                uoiFormComponents.getLevelField(),
                uoiFormComponents.getParentUOIField());
        add(formLayout);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(uoiFormComponents.getCreateButton(),
                uoiFormComponents.getUpdateButton(),
                uoiFormComponents.getCancelButton());
        add(buttonsLayout);

        toCreateState();
    }
}
