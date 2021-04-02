package io.recheck.ui.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import io.recheck.ui.SessionService;
import io.recheck.ui.components.ClickListener;
import io.recheck.ui.components.GridVerticalLayout;
import io.recheck.ui.components.uoi.*;
import io.recheck.ui.components.uoi.model.PropertiesModel;
import io.recheck.ui.components.uoi.model.UOIFormModel;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.RestClientService;
import io.recheck.ui.rest.dto.NewUoiDTO;
import io.recheck.ui.rest.dto.UpdatePropertiesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.Optional;


@CssImport("./views/create/create-view.css")
@Route(value = "create", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Create / Update")
@Slf4j
public class CreateView extends Div {

    private RestClientService restClientService;
    private SessionService sessionService;

    private UOIFormLayout uoiFormLayout = new UOIFormLayout(new UOIFormComponents());
    private PropertiesLayout propertiesLayout = new PropertiesLayout(new PropertiesComponents());

    private UOIGrid uoiGrid;
    private GridVerticalLayout gridLayout;

    private VerticalLayout createResultLayout = new VerticalLayout();


    public CreateView(@Autowired RestClientService restClientService, @Autowired SessionService sessionService) {
        this.restClientService = restClientService;
        this.sessionService = sessionService;

        initComponents();
        initListeners();
        initLayout();
    }

    private void initLayout() {
        //Main layout & default state
        //Create layout
        createResultLayout.add(uoiFormLayout);
        //Result / Grid layout
        createResultLayout.add(gridLayout);

        HorizontalLayout mainCreateLayout = new HorizontalLayout();
        mainCreateLayout.add(createResultLayout, propertiesLayout);
        add(mainCreateLayout);

        applyCss();
    }

    private void initComponents() {
        uoiGrid = new UOIGrid(sessionService.getDataProvider());
        gridLayout = new GridVerticalLayout("Existing UOI nodes", uoiGrid);
    }

    private void initListeners() {
        uoiFormLayout.getComponents().createClickListener(e -> createClickListener());
        uoiFormLayout.getComponents().updateClickListener(e -> updateClickListener());
        uoiFormLayout.getComponents().cancelClickListener(e -> toCreateState());

        propertiesLayout.getComponents().updateClickListener(e -> updateClickListener());

        uoiGrid.addItemClickListener((ClickListener<UOINode>) item -> toUpdateState(item));
    }

    private void applyCss() {
        addClassName("create-view");

        createResultLayout.getStyle().clear();
        createResultLayout.addClassName("createResultLayout");

        propertiesLayout.getStyle().clear();
        propertiesLayout.addClassName("propertiesLayout");
    }

    private void createClickListener() {
        NewUoiDTO newUoiDTO = new NewUoiDTO(uoiFormLayout.getComponents().getData());
        if (StringUtils.hasText(newUoiDTO.getCountryCode()) || newUoiDTO.getLevel() != null) {
            ResponseEntity<UOINode> uoiNodeResponseEntity = restClientService.newUoi(newUoiDTO);
            uoiGrid.addItem(uoiNodeResponseEntity.getBody());
        }
        toCreateState();
    }

    private void updateClickListener() {
        PropertiesModel propertiesModel = propertiesLayout.getComponents().getData();
        propertiesModel.getProperties().forEach((key, value) -> {
            restClientService.updateProperties(new UpdatePropertiesDTO(propertiesModel.getUoi(), key, value));
        });

        Optional<UOINode> uoiNode = uoiGrid.findItemByUoi(propertiesModel.getUoi());
        if (uoiNode.isPresent()) {
            uoiNode.get().setProperties(propertiesModel.getProperties());
        }

        toCreateState();
    }

    private void toUpdateState(UOINode uoiNode) {
        uoiFormLayout.toUpdateState();
        uoiFormLayout.getComponents().setData(new UOIFormModel(uoiNode));

        propertiesLayout.toUpdateState();
        propertiesLayout.getComponents().clearData();
        propertiesLayout.getComponents().setData(new PropertiesModel(uoiNode));
    }

    private void toCreateState() {
        uoiFormLayout.toCreateState();
        uoiFormLayout.getComponents().clearData();

        uoiGrid.refreshUI();
        uoiGrid.deselectAll();

        propertiesLayout.toCreateState();
        propertiesLayout.getComponents().clearData();
    }

}
