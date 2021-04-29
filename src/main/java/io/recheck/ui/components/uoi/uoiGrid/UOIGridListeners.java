package io.recheck.ui.components.uoi.uoiGrid;

import io.recheck.ui.entity.UOINode;

public interface UOIGridListeners {

    void viewDataClickListener(UOINode uoiNode);
    void viewHierarchyClickListener(UOINode uoiNode);
    void viewPropertiesClickListener(UOINode uoiNode);
    void viewDocumentsClickListener(UOINode uoiNode);

}
