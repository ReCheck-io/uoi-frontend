package io.recheck.ui.components.uoi.uoiGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.provider.ListDataProvider;
import io.recheck.ui.components.ExtendedGrid;
import io.recheck.ui.entity.DocumentsSource;
import io.recheck.ui.entity.UOINode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class UOIGrid extends ExtendedGrid<UOINode> {

    protected UOIGridListeners uoiGridListeners;

    public UOIGrid(List<UOINode> dataProvider, UOIGridListeners uoiGridListeners) {
        super(dataProvider);
        this.uoiGridListeners = uoiGridListeners;
        initColumns();
        configureColumns();
    }

    protected void initColumns() {
        initUoiColumn();
        initComponentColumns();
        configureColumns();
    }

    protected void initComponentColumns() {
        addComponentColumn(uoiNode -> {
            Button button = new Button("Data");
            button.addClickListener(e -> {
                uoiGridListeners.viewDataClickListener(uoiNode);
            });
            return button;
        });

        addComponentColumn(uoiNode -> {
            Button button = new Button("Hierarchy");
            button.addClickListener(e -> {
                uoiGridListeners.viewHierarchyClickListener(uoiNode);
            });
            return button;
        });

        addComponentColumn(uoiNode -> {
            Button button = new Button("Properties");
            button.addClickListener(e -> {
                uoiGridListeners.viewPropertiesClickListener(uoiNode);
            });
            return button;
        });

        addComponentColumn(uoiNode -> {
            Button button = new Button("Documents");
            button.addClickListener(e -> {
                uoiGridListeners.viewDocumentsClickListener(uoiNode);
            });
            return button;
        });
    }

    protected void initUoiColumn() {
        addColumn(UOINode::getUoi);
    }

    protected void configureColumns() {
        getColumns().forEach(c -> c.setAutoWidth(true));
        setSelectionMode(SelectionMode.NONE);
    }

    public void setDocuments(String uoi, List<DocumentsSource> documents) {
        Optional<UOINode> uoiNode = findItemByUoi(uoi);
        if (uoiNode.isPresent()) {
            uoiNode.get().setDocuments(documents);
        }
        refreshUI();
    }

    public void setChildrens(String uoi, ArrayList<String> children) {
        Optional<UOINode> uoiNode = findItemByUoi(uoi);
        if (uoiNode.isPresent()) {
            uoiNode.get().setChildren(children);
        }
        refreshUI();
    }

    public void setProperties(String uoi, Map<String, String> properties) {
        Optional<UOINode> uoiNode = findItemByUoi(uoi);
        if (uoiNode.isPresent()) {
            uoiNode.get().setProperties(properties);
        }
        refreshUI();
    }

    public void setParentUoi(String uoi, String parentUoi) {
        Optional<UOINode> uoiNode = findItemByUoi(uoi);
        if (uoiNode.isPresent()) {
            uoiNode.get().setParentUOI(parentUoi);
        }
        refreshUI();
    }

    private Optional<UOINode> findItemByUoi(String uoi) {
        ListDataProvider<UOINode> dataProvider = (ListDataProvider) getDataProvider();
        return dataProvider.getItems().stream().filter(node -> node.getUoi().equals(uoi)).findFirst();
    }
}
