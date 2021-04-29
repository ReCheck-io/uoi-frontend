package io.recheck.ui.components.uoi.model;

import io.recheck.ui.components.list.ListModel;
import io.recheck.ui.entity.DocumentsSource;
import io.recheck.ui.entity.UOINode;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class DocumentsModel implements ListModel {

    private String uoi;
    private List<DocumentsSource> documents;

    public DocumentsModel(UOINode uoiNode) {
        BeanUtils.copyProperties(uoiNode, this);
    }

    @Override
    public List getList() {
        return documents;
    }

    @Override
    public void setList(List list) {
        this.documents = list;
    }
}
