package io.recheck.ui.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.recheck.ui.rest.RestClientService;
import io.recheck.ui.components.GridVerticalLayout;
import io.recheck.ui.components.uoi.UOIGrid;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.dto.SearchByPropertiesDTO;
import io.recheck.ui.rest.dto.SearchByUoiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.*;

@CssImport("./views/search/search-view.css")
@Route(value = "search", layout = MainView.class)
@PageTitle("Search")
public class SearchView extends Div {

    private RestClientService restClientService;

    private GridVerticalLayout gridLayout;
    private UOIGrid uoiGrid;


    private TextField searchByUOIField = new TextField();
    private TextField searchByPropertiesByKeyTextField = new TextField();
    private TextField searchByPropertiesByValueTextField = new TextField();
    private Checkbox searchByPropertiesCheckbox = new Checkbox();

    private Button searchButton = new Button("Search");


    private VerticalLayout searchLayout = new VerticalLayout();
    private HorizontalLayout searchInputUOILayout = new HorizontalLayout();
    private HorizontalLayout searchInputPropertiesLayout = new HorizontalLayout();


    public SearchView(@Autowired RestClientService restClientService) {
        this.restClientService = restClientService;

        //Build Search layout
        uoiGrid = new UOIGrid(Collections.emptyList());
        gridLayout = new GridVerticalLayout("Results:", uoiGrid);

        searchInputUOILayout.add(searchByUOIField);
        searchInputPropertiesLayout.add(searchByPropertiesByKeyTextField, searchByPropertiesByValueTextField, searchByPropertiesCheckbox);
        searchInputPropertiesLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        Tab tab1 = new Tab("Search By UOI");
        Tab tab2 = new Tab("Search By Properties");
        Tabs tabs = new Tabs(tab1, tab2);

        applyCss();
        searchInputPropertiesLayout.setVisible(false);
        searchInputUOILayout.setVisible(true);
        clearSearchByUOILayout();
        searchLayout.add(tabs, new Div(searchInputUOILayout, searchInputPropertiesLayout), searchButton, gridLayout);
        add(searchLayout);

        //=================================================
        //                  EVENTS
        //=================================================
        Map<Tab, Component> tabsToLayouts = new HashMap<>();
        tabsToLayouts.put(tab1, searchInputUOILayout);
        tabsToLayouts.put(tab2, searchInputPropertiesLayout);

        Map<Tab, VoidFunction> tabsToSearchFunction = new HashMap<>();
        tabsToSearchFunction.put(tab1, this::clickSearchByUOI);
        tabsToSearchFunction.put(tab2, this::clickSearchByProperties);

        Map<Tab, VoidFunction> tabsToClearFunction = new HashMap<>();
        tabsToClearFunction.put(tab1, this::clearSearchByUOILayout);
        tabsToClearFunction.put(tab2, this::clearSearchByPropertiesLayout);

        tabs.addSelectedChangeListener(event -> {
            tabsToLayouts.values().forEach(page -> page.setVisible(false));
            Component selectedLayout = tabsToLayouts.get(tabs.getSelectedTab());
            tabsToClearFunction.get(tabs.getSelectedTab()).apply();
            selectedLayout.setVisible(true);
        });

        searchButton.addClickListener(event -> {
            tabsToSearchFunction.get(tabs.getSelectedTab()).apply();
            uoiGrid.setVisibleColumn(UOIGrid.COLUMN_KEYS.CL_KEY_LEVEL, searchByPropertiesCheckbox.getValue());
            uoiGrid.setVisibleColumn(UOIGrid.COLUMN_KEYS.CL_KEY_PARENT, searchByPropertiesCheckbox.getValue());
            uoiGrid.setVisibleColumn(UOIGrid.COLUMN_KEYS.CL_KEY_PROPERTIES, searchByPropertiesCheckbox.getValue());
        });

    }

    private void applyCss() {
        addClassName("search-view");

        searchLayout.getStyle().clear();
        searchLayout.addClassName("searchLayout");
    }

    private void clickSearchByUOI(){
        ResponseEntity<String> responseEntity = restClientService.searchByUoi(new SearchByUoiDTO(searchByUOIField.getValue()));
        List<UOINode> uoiNodes = getSearchByUOIResult(responseEntity);
        uoiGrid.setItems(uoiNodes);
    }

    private void clickSearchByProperties() {
        SearchByPropertiesDTO searchByPropertiesDTO = new SearchByPropertiesDTO(searchByPropertiesByKeyTextField.getValue(),
                searchByPropertiesByValueTextField.getValue(),
                searchByPropertiesCheckbox.getValue());
        ResponseEntity<String> responseEntity = restClientService.searchByProperties(searchByPropertiesDTO);
        List<UOINode> uoiNodes = getSearchByPropertiesResult(responseEntity);
        uoiGrid.setItems(uoiNodes);
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


    private void clearSearchByUOILayout() {
        searchByUOIField.setValue("");
        searchByUOIField.setPlaceholder("UOI");
    }

    private void clearSearchByPropertiesLayout() {
        searchByPropertiesByKeyTextField.setValue("");
        searchByPropertiesByKeyTextField.setPlaceholder("Key");
        searchByPropertiesByValueTextField.setValue("");
        searchByPropertiesByValueTextField.setPlaceholder("Value");

        searchByPropertiesCheckbox.setLabel("show properties");
        searchByPropertiesCheckbox.setValue(false);
    }

    interface VoidFunction {
        void apply();
    }

}
