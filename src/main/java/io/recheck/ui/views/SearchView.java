package io.recheck.ui.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.recheck.ui.components.LayoutTab;
import io.recheck.ui.components.LayoutTabs;
import io.recheck.ui.components.uoi.SearchByPropertiesLayoutTab;
import io.recheck.ui.components.uoi.SearchByUoiLayoutTab;
import io.recheck.ui.components.uoi.uoiGrid.UOIGrid;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@CssImport("./views/search/search-view.css")
@Route(value = "search", layout = MainView.class)
@PageTitle("Search")
public class SearchView extends BaseView {

    private SearchByUoiLayoutTab searchByUoiLayout = new SearchByUoiLayoutTab();
    private SearchByPropertiesLayoutTab searchByPropertiesLayout = new SearchByPropertiesLayoutTab();
    LayoutTab tab1 = new LayoutTab(searchByUoiLayout,"Search By UOI");
    LayoutTab tab2 = new LayoutTab(searchByPropertiesLayout,"Search By Properties");
    LayoutTabs tabs = new LayoutTabs(tab1, tab2);

    private VerticalLayout searchLayout = new VerticalLayout();


    public SearchView(@Autowired RestClientService restClientService) {
        this.restClientService = restClientService;

        initListeners();
        initLayout();
    }

    private void initLayout() {
        uoiGrid = new UOIGrid(Collections.emptyList(), uoiGridListeners);
        gridLayout = new VerticalLayout(new Label("Results:"), uoiGrid);

        searchLayout.add(tabs, new Div(searchByUoiLayout, searchByPropertiesLayout), gridLayout);
        viewLayout.add(documentsLayout, propertiesLayout, uoiFormLayout, uoiHierarchyLayout);

        add(new HorizontalLayout(searchLayout, viewLayout));

        applyCss();
    }

    protected void applyCss() {
        addClassName("search-view");

        searchLayout.getStyle().clear();
        searchLayout.addClassName("leftColumnLayout");

        viewLayout.getStyle().clear();
        viewLayout.addClassName("rightColumnLayout");
    }

    protected void initListeners() {
        tabs.addSelectedChangeListener(event -> {
            tabs.selectedChangeEvent();
            toInitState();
        });

        searchByUoiLayout.searchClickListener(event -> {
            toInitState();
            ResponseEntity<String> responseEntity = restClientService.searchByUoi(searchByUoiLayout.getData());
            List<UOINode> uoiNodes = restClientService.getSearchByUOIResult(responseEntity);
            uoiGrid.setItems(uoiNodes);
        });

        searchByPropertiesLayout.searchClickListener(event -> {
            toInitState();
            ResponseEntity<String> responseEntity = restClientService.searchByProperties(searchByPropertiesLayout.getData());
            List<UOINode> uoiNodes = restClientService.getSearchByPropertiesResult(responseEntity);
            uoiGrid.setItems(uoiNodes);
        });

        super.initListeners();
    }

}
