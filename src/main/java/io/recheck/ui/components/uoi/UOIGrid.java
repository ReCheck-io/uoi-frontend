package io.recheck.ui.components.uoi;

import com.vaadin.flow.data.provider.ListDataProvider;
import io.recheck.ui.components.ExtendedGrid;
import io.recheck.ui.entity.UOINode;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UOIGrid extends ExtendedGrid<UOINode> {

    public enum COLUMN_KEYS {
        CL_KEY_UOI, CL_KEY_COUNTRY, CL_KEY_LEVEL, CL_KEY_PARENT, CL_KEY_PROPERTIES
    }

    public UOIGrid(List<UOINode> dataProvider) {
        super(dataProvider);
        initColumns();
    }

    private void initColumns() {
        addColumn(UOINode::getUoi).setHeader("UOI").setKey(COLUMN_KEYS.CL_KEY_UOI.name());
        addColumn(UOINode::getCountryCode).setHeader("Country Code").setKey(COLUMN_KEYS.CL_KEY_COUNTRY.name()).setVisible(false);
        addColumn(UOINode::getLevel).setHeader("Level").setKey(COLUMN_KEYS.CL_KEY_LEVEL.name());
        addColumn(UOINode::getParentUOI).setHeader("Parent UOI").setKey(COLUMN_KEYS.CL_KEY_PARENT.name());
        addColumn(UOINode::getPropertiesAsJson).setHeader("Properties as JSON").setKey(COLUMN_KEYS.CL_KEY_PROPERTIES.name());
        getColumns().forEach(c -> c.setAutoWidth(true));
    }

    public void setVisibleColumn(COLUMN_KEYS key, boolean visible) {
        Optional.ofNullable(getColumnByKey(key.name())).ifPresent(cl -> cl.setVisible(visible));
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
