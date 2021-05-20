package io.recheck.uoi.ui.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.recheck.SessionService;
import io.recheck.accounts.AccountService;
import io.recheck.rest.RestClientService;
import io.recheck.uoi.entity.UOINode;
import io.recheck.uoi.ui.components.LayoutTab;
import io.recheck.uoi.ui.components.LayoutTabs;
import io.recheck.uoi.ui.components.SearchByPropertiesLayoutTab;
import io.recheck.uoi.ui.components.SearchByUoiLayoutTab;
import io.recheck.uoi.ui.components.uoiGrid.UOIGrid;

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


    public SearchView(RestClientService restClientService, SessionService sessionService, AccountService accountService) {
        super(restClientService, sessionService, accountService);
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
            List<UOINode> uoiNodes = restClientService.searchByUoi(searchByUoiLayout.getData());
            uoiGrid.setItems(uoiNodes);
        });

        searchByPropertiesLayout.searchClickListener(event -> {
            toInitState();
            List<UOINode> uoiNodes = restClientService.searchByProperties(searchByPropertiesLayout.getData());
            uoiGrid.setItems(uoiNodes);
        });

        super.initListeners();
    }

}
