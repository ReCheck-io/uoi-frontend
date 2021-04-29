package io.recheck.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.ui.components.list.ComponentList;
import io.recheck.ui.components.map.ComponentMap;
import io.recheck.ui.components.map.entryConverter.ConverterKeyValueTextField;
import io.recheck.ui.components.uoi.*;
import io.recheck.ui.components.uoi.model.DocumentsModel;
import io.recheck.ui.components.uoi.model.PropertiesModel;
import io.recheck.ui.components.uoi.model.UOIFormModel;
import io.recheck.ui.components.uoi.uoiGrid.UOIGrid;
import io.recheck.ui.components.uoi.uoiGrid.UOIGridListeners;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.RestClientService;
import io.recheck.ui.rest.dto.UpdatePropertiesDTO;
import io.recheck.ui.rest.dto.UpdateRelationshipDTO;

public class BaseView extends Div {

    protected RestClientService restClientService;

    protected UOIFormLayout uoiFormLayout = new UOIFormLayout();
    protected PropertiesLayout propertiesLayout = new PropertiesLayout(new ComponentMap(new ConverterKeyValueTextField()));
    protected DocumentsLayout documentsLayout = new DocumentsLayout(new ComponentList(new DocumentsConverter()));
    protected UoiHierarchyLayout uoiHierarchyLayout = new UoiHierarchyLayout();

    protected UOIGrid uoiGrid;
    protected UOIGridListeners uoiGridListeners;
    protected VerticalLayout gridLayout;

    protected VerticalLayout viewLayout = new VerticalLayout();

    protected void initListeners() {

        uoiFormLayout.updateClickListener(e -> {
            UOIFormModel uoiFormModel = uoiFormLayout.getData();

            UpdateRelationshipDTO updateRelationshipDTO = new UpdateRelationshipDTO(uoiFormModel.getUoi(), uoiFormModel.getParentUOI());
            restClientService.makeRelationship(updateRelationshipDTO);

            uoiGrid.setParentUoi(updateRelationshipDTO.getChildNode(), updateRelationshipDTO.getParentNode());

            toInitState();
        });

        uoiFormLayout.cancelClickListener(e -> toInitState());



        propertiesLayout.updateClickListener(e -> {
            PropertiesModel propertiesModel = propertiesLayout.getData();
            propertiesModel.getProperties().forEach((key, value) -> {
                restClientService.updateProperties(new UpdatePropertiesDTO(propertiesModel.getUoi(), key, value));
            });

            uoiGrid.setProperties(propertiesModel.getUoi(), propertiesModel.getProperties());

            toInitState();
        });

        propertiesLayout.cancelClickListener(e -> toInitState());


        documentsLayout.closeClickListener(e -> toInitState());

        uoiHierarchyLayout.closeClickListener(e -> toInitState());


        uoiGridListeners = new UOIGridListeners() {
            @Override
            public void viewDataClickListener(UOINode uoiNode) {
                toInitState();
                uoiFormLayout.toUpdateState(uoiNode);
            }

            @Override
            public void viewHierarchyClickListener(UOINode uoiNode) {
                toInitState();
                uoiHierarchyLayout.setDataAndVisible(uoiNode.getUoi(), uoiNode.getParentUOI(), uoiNode.getChildren(), true);
            }

            @Override
            public void viewPropertiesClickListener(UOINode uoiNode) {
                toInitState();
                propertiesLayout.setDataAndVisible(new PropertiesModel(uoiNode), true);
            }

            @Override
            public void viewDocumentsClickListener(UOINode uoiNode) {
                toInitState();
                documentsLayout.setDataAndVisible(new DocumentsModel(uoiNode), true);
            }
        };

    }

    protected void toInitState() {
        uoiHierarchyLayout.setVisible(false);
        uoiFormLayout.setVisible(false);
        propertiesLayout.setVisible(false);
        documentsLayout.setVisible(false);
    }

}
