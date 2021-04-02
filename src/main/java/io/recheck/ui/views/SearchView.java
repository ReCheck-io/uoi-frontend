package io.recheck.ui.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.recheck.ui.components.GridVerticalLayout;
import io.recheck.ui.components.LayoutTab;
import io.recheck.ui.components.LayoutTabs;
import io.recheck.ui.components.uoi.*;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CssImport("./views/search/search-view.css")
@Route(value = "search", layout = MainView.class)
@PageTitle("Search")
public class SearchView extends Div {

    private RestClientService restClientService;

    private GridVerticalLayout gridLayout;
    private UOIGrid uoiGrid;

    private SearchByUoiComponents uoiComponents = new SearchByUoiComponents();
    private SearchByUoiLayout uoiLayout = new SearchByUoiLayout(uoiComponents);

    private SearchByPropertiesComponents propertiesComponents = new SearchByPropertiesComponents();
    private SearchByPropertiesLayout propertiesLayout = new SearchByPropertiesLayout(propertiesComponents);

    LayoutTab tab1 = new LayoutTab(uoiLayout,"Search By UOI");
    LayoutTab tab2 = new LayoutTab(propertiesLayout,"Search By Properties");
    LayoutTabs tabs = new LayoutTabs(tab1, tab2);

    private VerticalLayout searchLayout = new VerticalLayout();

    public SearchView(@Autowired RestClientService restClientService) {
        this.restClientService = restClientService;

        initComponents();
        initListeners();
        initLayout();
    }

    private void initLayout() {
        searchLayout.add(tabs, new Div(uoiLayout, propertiesLayout), gridLayout);
        add(searchLayout);

        applyCss();
    }

    private void initComponents() {
        uoiGrid = new UOIGrid(Collections.emptyList());
        gridLayout = new GridVerticalLayout("Results:", uoiGrid);
    }

    private void initListeners() {
        uoiComponents.searchClickListener(event -> {
            ResponseEntity<String> responseEntity = restClientService.searchByUoi(uoiComponents.getData());
            List<UOINode> uoiNodes = getSearchByUOIResult(responseEntity);
            uoiGrid.setItems(uoiNodes);
        });

        propertiesComponents.searchClickListener(event -> {
            ResponseEntity<String> responseEntity = restClientService.searchByProperties(propertiesComponents.getData());
            List<UOINode> uoiNodes = getSearchByPropertiesResult(responseEntity);
            uoiGrid.setItems(uoiNodes);

            uoiGrid.setVisibleColumn(UOIGrid.COLUMN_KEYS.CL_KEY_LEVEL, propertiesComponents.getCheckbox().getValue());
            uoiGrid.setVisibleColumn(UOIGrid.COLUMN_KEYS.CL_KEY_PARENT, propertiesComponents.getCheckbox().getValue());
            uoiGrid.setVisibleColumn(UOIGrid.COLUMN_KEYS.CL_KEY_PROPERTIES, propertiesComponents.getCheckbox().getValue());
        });
    }

    private void applyCss() {
        addClassName("search-view");

        searchLayout.getStyle().clear();
        searchLayout.addClassName("searchLayout");
    }

    private List<UOINode> getSearchByUOIResult(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        if (responseBody.equals("The node does not exist.")) {
            return Collections.emptyList();
        }
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                UOINode uoiNode = objectMapper.readValue(responseBody, UOINode.class);
                return Arrays.asList(uoiNode);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        }
    }

    private List<UOINode> getSearchByPropertiesResult(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<UOINode> uoiNodes = objectMapper.readValue(responseBody, new TypeReference<List<UOINode>>(){});
            return uoiNodes;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
