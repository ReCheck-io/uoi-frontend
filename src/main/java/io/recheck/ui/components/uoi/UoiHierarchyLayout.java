package io.recheck.ui.components.uoi;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class UoiHierarchyLayout extends VerticalLayout {

    private TreeGrid<String> tree;
    private VerticalLayout treeLayout = new VerticalLayout();

    private H3 title = new H3("Hierarchy");
    private Button closeButton = new Button("Close");

    public UoiHierarchyLayout() {
        initLayout();
    }

    public void closeClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        closeButton.addClickListener(listener);
    }

    public void initLayout() {
        add(title, treeLayout, closeButton);
        setVisible(false);
    }

    public void setDataAndVisible(String current, String parent, List<String> children, boolean visible) {
        setData(current, parent, children);
        setVisible(visible);
    }

    private void setData(String current, String parent, List<String> children) {
        clearData();

        log.debug("parent {}, current {}, \n children {}", parent, current, children);

        TreeData<String> treeData = new TreeData<>();
        if (StringUtils.hasText(parent)) {
            treeData.addRootItems("Parent : " + parent);
            treeData.addItem("Parent : " + parent, "Current : " + current);
        }
        else {
            treeData.addRootItems("Current : " + current);
        }
        if (!children.isEmpty()) {
            treeData.addItems("Current : " + current, children);
        }

        TreeDataProvider<String> treeDataProvider = new TreeDataProvider<>(treeData);

        tree = new TreeGrid(treeDataProvider);

        tree.addHierarchyColumn(String::toString);

        treeLayout.add(tree);
    }

    private void clearData() {
        treeLayout.removeAll();
    }

}
