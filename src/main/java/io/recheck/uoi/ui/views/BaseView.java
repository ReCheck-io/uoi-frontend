package io.recheck.uoi.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.recheck.SessionService;
import io.recheck.accounts.AccountService;
import io.recheck.accounts.entity.Account;
import io.recheck.rest.RestClientService;
import io.recheck.rest.dto.CirdaxDocumentsRequestAccessDTO;
import io.recheck.rest.dto.CirdaxDocumentsResponseDTO;
import io.recheck.rest.dto.UpdatePropertiesDTO;
import io.recheck.rest.dto.UpdateRelationshipDTO;
import io.recheck.uoi.entity.UOINode;
import io.recheck.uoi.ui.components.*;
import io.recheck.uoi.ui.components.map.ComponentMap;
import io.recheck.uoi.ui.components.map.entryConverter.ConverterKeyValueTextField;
import io.recheck.uoi.ui.components.model.DocumentsModel;
import io.recheck.uoi.ui.components.model.PropertiesModel;
import io.recheck.uoi.ui.components.model.RequestAccessModel;
import io.recheck.uoi.ui.components.model.UOIFormModel;
import io.recheck.uoi.ui.components.uoiGrid.UOIGrid;
import io.recheck.uoi.ui.components.uoiGrid.UOIGridListeners;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class BaseView extends Div {

    protected final RestClientService restClientService;
    protected final SessionService sessionService;
    protected final AccountService accountService;

    protected UOIFormLayout uoiFormLayout = new UOIFormLayout();
    protected PropertiesLayout propertiesLayout = new PropertiesLayout(new ComponentMap(new ConverterKeyValueTextField()));
    protected DocumentsLayout documentsLayout = new DocumentsLayout();
    protected UoiHierarchyLayout uoiHierarchyLayout = new UoiHierarchyLayout();
    protected DocumentsRequestAccessDialog documentsRequestAccessDialog = new DocumentsRequestAccessDialog();
    protected DocumentsRequestAccessListeners documentsRequestAccessListeners;
    protected DocumentsResponseAccessDialog documentsResponseAccessDialog = new DocumentsResponseAccessDialog();

    protected UOIGrid uoiGrid;
    protected UOIGridListeners uoiGridListeners;
    protected VerticalLayout gridLayout;

    protected VerticalLayout viewLayout = new VerticalLayout();

    public BaseView(RestClientService restClientService, SessionService sessionService, AccountService accountService) {
        this.restClientService = restClientService;
        this.sessionService = sessionService;
        this.accountService = accountService;
    }

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


        documentsRequestAccessDialog.confirmClickListener(listener -> {
            RequestAccessModel data = documentsRequestAccessDialog.getData();
            Optional<Account> account = accountService.findAccountByUserName(data.getUsername());
            if (account.isPresent()) {
                accountService.setLoggedAccount(account.get());
                documentsRequestAccessDialog.close();
                documentsRequestAccessListeners.doIfAccountPresent(data.getUoi(), account.get());
            }
            else {
                documentsRequestAccessDialog.invalidAccount();
            }
        });

        documentsRequestAccessListeners = (uoi, account) -> {
            CirdaxDocumentsRequestAccessDTO accessDTO = new CirdaxDocumentsRequestAccessDTO(uoi, account.getUserName(), "UOI Frontend");
            ResponseEntity<CirdaxDocumentsResponseDTO> cirdaxDocumentsResponseDTOResponseEntity = restClientService.requestAccess(accessDTO);
            CirdaxDocumentsResponseDTO cirdaxDocumentsResponseDTO = cirdaxDocumentsResponseDTOResponseEntity.getBody();
            documentsResponseAccessDialog.open(cirdaxDocumentsResponseDTO);
        };


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
                DocumentsModel model = new DocumentsModel(uoiNode);
                DocumentsListeners listeners = (source) -> {
                    Optional<Account> loggedAccount = accountService.getLoggedAccount();
                    if (loggedAccount.isPresent()) {
                        documentsRequestAccessListeners.doIfAccountPresent(model.getUoi(), loggedAccount.get());
                    }
                    else {
                        documentsRequestAccessDialog.open(model.getUoi());
                    }
                };
                documentsLayout.setDataAndVisible(model, listeners, true);
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
