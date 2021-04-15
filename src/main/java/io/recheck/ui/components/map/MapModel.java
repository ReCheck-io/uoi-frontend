package io.recheck.ui.components.map;

import java.util.Map;

public interface MapModel<KD, VD> {

    Map<KD,VD> getMap();
    void setMap(Map<KD,VD> map);

}
