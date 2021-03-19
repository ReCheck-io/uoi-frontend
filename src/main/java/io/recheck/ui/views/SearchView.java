package io.recheck.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.recheck.ui.RestClientService;
import io.recheck.ui.entity.UOINode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@CssImport("./views/search/search-view.css")
@Route(value = "search", layout = MainView.class)
@PageTitle("Search")
public class SearchView extends Div {

    private VerticalLayout gridLayout = new VerticalLayout();
    private Label gridLabel = new Label("Results:");
    private Grid<UOINode> grid = new Grid<>();


    private TextField searchByUOIField = new TextField();
    private TextField searchByPropertiesByKeyTextField = new TextField();
    private TextField searchByPropertiesByValueTextField = new TextField();
    private Checkbox searchByPropertiesCheckbox = new Checkbox();

    private Button searchButton = new Button("Search");


    private VerticalLayout searchLayout = new VerticalLayout();
    private HorizontalLayout searchInputUOILayout = new HorizontalLayout();
    private HorizontalLayout searchInputPropertiesLayout = new HorizontalLayout();


    public SearchView(@Autowired RestClientService restClientService) {
        addClassName("search-view");


        searchInputUOILayout.add(searchByUOIField);
        searchInputPropertiesLayout.add(searchByPropertiesByKeyTextField, searchByPropertiesByValueTextField, searchByPropertiesCheckbox);
        searchInputPropertiesLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        VoidFunction searchByUOIFunction = () -> {
            UOINode uoiNode = restClientService.searchByUOI(searchByUOIField.getValue());
            grid.setItems(Collections.emptyList());
            if (uoiNode != null) {
                grid.setItems(uoiNode);
                gridLayout.setVisible(true);
            }
        };

        VoidFunction searchByPropertiesFunction = () -> {
            List<UOINode> uoiNodes = restClientService.searchByProperties(searchByPropertiesByKeyTextField.getValue(),
                    searchByPropertiesByValueTextField.getValue(), searchByPropertiesCheckbox.getValue());
            grid.setItems(Collections.emptyList());
            if (uoiNodes != null && !uoiNodes.isEmpty()) {
                grid.setItems(uoiNodes);
                gridLayout.setVisible(true);
            }
        };


        Tab tab1 = new Tab("Search By UOI");
        Tab tab2 = new Tab("Search By Properties");
        Tabs tabs = new Tabs(tab1, tab2);

        Map<Tab, Component> tabsToLayouts = new HashMap<>();
        tabsToLayouts.put(tab1, searchInputUOILayout);
        tabsToLayouts.put(tab2, searchInputPropertiesLayout);

        Map<Tab, VoidFunction> tabsToSearchFunction = new HashMap<>();
        tabsToSearchFunction.put(tab1, searchByUOIFunction);
        tabsToSearchFunction.put(tab2, searchByPropertiesFunction);

        Map<Component, VoidFunction> layoutsToThunk = new HashMap<>();
        layoutsToThunk.put(searchInputUOILayout, this::toSearchByUOILayout);
        layoutsToThunk.put(searchInputPropertiesLayout, this::toSearchByPropertiesLayout);

        tabs.addSelectedChangeListener(event -> {
            tabsToLayouts.values().forEach(page -> page.setVisible(false));
            Component selectedLayout = tabsToLayouts.get(tabs.getSelectedTab());
            selectedLayout.setVisible(true);
            layoutsToThunk.get(selectedLayout).apply();
        });

        searchInputPropertiesLayout.setVisible(false);
        toSearchByUOILayout();


        grid.addColumn(UOINode::getUoi).setHeader("UOI");
        grid.addColumn(UOINode::getCountryCode).setHeader("Country Code").setVisible(false);
        grid.addColumn(UOINode::getLevel).setHeader("Level");
        grid.addColumn(UOINode::getParentUOI).setHeader("Parent UOI");
        grid.getColumns().forEach(c -> c.setAutoWidth(true));
        gridLayout.add(gridLabel, grid);


        final String propertiesColumnKey = "PropertiesAsJson";
        searchButton.addClickListener(event -> {
            tabsToSearchFunction.get(tabs.getSelectedTab()).apply();
            if (searchByPropertiesCheckbox.getValue()) {
                if (grid.getColumnByKey(propertiesColumnKey) == null)
                    grid.addColumn(UOINode::getPropertiesAsJson).setKey(propertiesColumnKey).setAutoWidth(true);
            }
            else {
                if (grid.getColumnByKey(propertiesColumnKey) != null)
                    grid.removeColumnByKey(propertiesColumnKey);
            }
        });


        searchLayout.getStyle().clear();
        searchLayout.addClassName("searchLayout");
        searchLayout.add(tabs, new Div(searchInputUOILayout, searchInputPropertiesLayout), searchButton, gridLayout);
        add(searchLayout);

    }


    private void toSearchByUOILayout() {
        searchByUOIField.setValue("");
        searchByUOIField.setPlaceholder("UOI");
    }

    private void toSearchByPropertiesLayout() {
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
