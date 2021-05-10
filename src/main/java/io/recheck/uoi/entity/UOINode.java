package io.recheck.uoi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UOINode {

    private String uoi;

    private String parentUOI;
    private String countryCode;
    private LEVEL level;

    private String timestamp;
    private String uoiClass;
    private String owner;

    private Map<String, String> properties = new HashMap<>();

    private ArrayList<String> children = new ArrayList();

    private List<DocumentsSource> documents = new ArrayList<>(Arrays.asList(DocumentsSource.CIRDAX));

    public UOINode(String uoi) {
        this.uoi = uoi;
    }

    public boolean equals(Object o){
        if(o == null)
            return false;
        if(!(o instanceof UOINode))
            return false;

        UOINode other = (UOINode) o;
        return this.uoi.equals(other.uoi);
    }


}
