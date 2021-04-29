package io.recheck.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import io.recheck.ui.SessionService;
import io.recheck.ui.components.uoi.uoiGrid.UOIGrid;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.RestClientService;
import io.recheck.ui.rest.dto.NewUoiDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;


@CssImport("./views/create/create-view.css")
@Route(value = "create", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Create / Update")
@Slf4j
public class CreateView extends BaseView {

    private SessionService sessionService;

    private Button newButton = new Button("New UOI");

    private VerticalLayout viewLayout = new VerticalLayout();


    public CreateView(@Autowired RestClientService restClientService, @Autowired SessionService sessionService) {
        this.restClientService = restClientService;
        this.sessionService = sessionService;

        initListeners();
        initLayout();
    }

    private void initLayout() {
        uoiGrid = new UOIGrid(sessionService.getDataProvider(), uoiGridListeners);

        HorizontalLayout horizontalLayout = new HorizontalLayout(new Label("Existing UOI nodes"), newButton);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        gridLayout = new VerticalLayout(horizontalLayout, uoiGrid);

        viewLayout.add(uoiFormLayout, documentsLayout, propertiesLayout, uoiHierarchyLayout);

        add(new HorizontalLayout(gridLayout, viewLayout));

        applyCss();
    }

    protected void applyCss() {
        addClassName("create-view");

        gridLayout.getStyle().clear();
        gridLayout.addClassName("leftColumnLayout");

        viewLayout.getStyle().clear();
        viewLayout.addClassName("rightColumnLayout");
    }

    protected void initListeners() {
        newButton.addClickListener(e -> {
            toInitState();
            uoiFormLayout.toCreateState();
        });

        uoiFormLayout.createClickListener(e -> {
            NewUoiDTO newUoiDTO = new NewUoiDTO(uoiFormLayout.getData());
            if (StringUtils.hasText(newUoiDTO.getCountryCode()) || newUoiDTO.getLevel() != null) {
                ResponseEntity<UOINode> uoiNodeResponseEntity = restClientService.newUoi(newUoiDTO);
                uoiGrid.addItem(uoiNodeResponseEntity.getBody());
            }
            toInitState();
        });

        super.initListeners();
    }

}
