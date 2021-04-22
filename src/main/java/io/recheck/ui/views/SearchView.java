package io.recheck.ui.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.recheck.ui.components.*;
import io.recheck.ui.components.baseStructure.ClickListener;
import io.recheck.ui.components.map.ComponentMap;
import io.recheck.ui.components.map.entryConverter.ConverterKeyValueTextField;
import io.recheck.ui.components.uoi.*;
import io.recheck.ui.components.uoi.model.PropertiesModel;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.RestClientService;
import io.recheck.ui.rest.dto.UpdatePropertiesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private SearchByUoiLayout searchByUoiLayout = new SearchByUoiLayout(new SearchByUoiComponents());
    private SearchByPropertiesLayout searchByPropertiesLayout = new SearchByPropertiesLayout(new SearchByPropertiesComponents());
    private PropertiesLayout propLayout = new PropertiesLayout(new PropertiesComponents(new ComponentMap(new ConverterKeyValueTextField())));

    LayoutTab tab1 = new LayoutTab(searchByUoiLayout,"Search By UOI");
    LayoutTab tab2 = new LayoutTab(searchByPropertiesLayout,"Search By Properties");
    LayoutTabs tabs = new LayoutTabs(tab1, tab2);

    private VerticalLayout searchLayout = new VerticalLayout();

    public SearchView(@Autowired RestClientService restClientService) {
        this.restClientService = restClientService;

        initComponents();
        initListeners();
        initLayout();
    }

    private void initLayout() {
        searchLayout.add(tabs, new Div(searchByUoiLayout, searchByPropertiesLayout), gridLayout);

        add(new HorizontalLayout(searchLayout, propLayout));

        applyCss();
    }

    private void initComponents() {
        uoiGrid = new UOIGrid(Collections.emptyList());
        gridLayout = new GridVerticalLayout("Results:", uoiGrid);
    }

    private void initListeners() {
        SearchByUoiComponents uoiComponents = searchByUoiLayout.getComponents();
        uoiComponents.searchClickListener(event -> {
            ResponseEntity<String> responseEntity = restClientService.searchByUoi(uoiComponents.getData());
            List<UOINode> uoiNodes = getSearchByUOIResult(responseEntity);
            uoiGrid.setItems(uoiNodes);
        });

        SearchByPropertiesComponents sbpComponents = searchByPropertiesLayout.getComponents();
        sbpComponents.searchClickListener(event -> {
            ResponseEntity<String> responseEntity = restClientService.searchByProperties(sbpComponents.getData());
            List<UOINode> uoiNodes = getSearchByPropertiesResult(responseEntity);
            uoiGrid.setItems(uoiNodes);
        });


        PropertiesComponents propComponents = propLayout.getComponents();
        uoiGrid.addItemClickListener((ClickListener<UOINode>) uoiNode -> {
            propLayout.toUpdateState();
            propComponents.clearData();
            propComponents.setData(new PropertiesModel(uoiNode));
        });

        propComponents.updateClickListener(e -> {
            PropertiesModel propertiesModel = propComponents.getData();
            propertiesModel.getProperties().forEach((key, value) -> {
                restClientService.updateProperties(new UpdatePropertiesDTO(propertiesModel.getUoi(), key, value));
            });

            uoiGrid.setProperties(propertiesModel.getUoi(), propertiesModel.getProperties());

            propLayout.toCreateState();
            propComponents.clearData();
        });
    }

    private void applyCss() {
        addClassName("search-view");

        searchLayout.getStyle().clear();
        searchLayout.addClassName("searchLayout");

        propLayout.getStyle().clear();
        propLayout.addClassName("propertiesLayout");
    }

    private List<UOINode> getSearchByUOIResult(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.value() == 404) {
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
