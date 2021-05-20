package io.recheck.uoi.ui.components.uoiGrid;

import io.recheck.uoi.entity.UOINode;

public interface UOIGridListeners {

    void viewDataClickListener(UOINode uoiNode);
    void viewHierarchyClickListener(UOINode uoiNode);
    void viewPropertiesClickListener(UOINode uoiNode);
    void viewDocumentsClickListener(UOINode uoiNode);

}
