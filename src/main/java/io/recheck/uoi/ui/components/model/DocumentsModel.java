package io.recheck.uoi.ui.components.model;

import io.recheck.uoi.entity.DocumentsSource;
import io.recheck.uoi.entity.UOINode;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class DocumentsModel {

    private String uoi;
    private List<DocumentsSource> documents;

    public DocumentsModel(UOINode uoiNode) {
        BeanUtils.copyProperties(uoiNode, this);
    }

}
