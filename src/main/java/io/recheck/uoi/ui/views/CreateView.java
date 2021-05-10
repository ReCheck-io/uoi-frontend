package io.recheck.uoi.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import io.recheck.SessionService;
import io.recheck.accounts.AccountService;
import io.recheck.rest.RestClientService;
import io.recheck.rest.dto.NewUoiDTO;
import io.recheck.uoi.entity.UOINode;
import io.recheck.uoi.ui.components.uoiGrid.UOIGrid;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;


@CssImport("./views/create/create-view.css")
@Route(value = "create", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Create / Update")
public class CreateView extends BaseView {

    private Button newButton = new Button("New UOI");

    private VerticalLayout viewLayout = new VerticalLayout();


    public CreateView(RestClientService restClientService, SessionService sessionService, AccountService accountService) {
        super(restClientService, sessionService, accountService);
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
