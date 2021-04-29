package io.recheck.ui.components.map;

import com.vaadin.flow.component.Component;
import io.recheck.ui.components.map.entryConverter.Converter;

import java.util.HashMap;
import java.util.Map;

@lombok.Data
public class ComponentMap<KC extends Component, VC extends Component, D extends MapModel, KD, VD> {

    private HashMap<KC, VC> currentMap = new HashMap<>();
    private D currentData;
    private Converter<KC,VC, D, KD, VD> converter;

    public ComponentMap(Converter<KC, VC, D, KD, VD> converter) {
        this.converter = converter;
    }

    public D getData() {
        Map<KD, VD> map = new HashMap<>();

        currentMap.forEach((key, value) -> {
            KD kd = converter.toKeyData(key);
            VD vd = converter.toValueData(value);
            if (converter.keyDataIsNotEmpty(kd) && converter.valueDataIsNotEmpty(vd)) {
                map.put(kd, vd);
            }
        });
        currentData.setMap(map);

        return currentData;
    }

    public void setData(D data) {
        this.currentData = data;

        Map<KD,VD> map = currentData.getMap();
        map.forEach((key,value) -> {
            put(data, key, value);
        });
    }

    public void clearData() {
        currentMap.clear();
    }

    public KC putEmpty() {
        KC k = converter.createEmptyKeyComponent();
        currentMap.put(k, converter.createEmptyValueComponent());
        return k;
    }

    private void put(D data, KD key, VD value) {
        KC k = converter.keyDataIsNotEmpty(key) ? converter.toKeyComponent(data, key) : converter.createEmptyKeyComponent();
        VC v = converter.valueDataIsNotEmpty(value) ? converter.toValueComponent(data, value) : converter.createEmptyValueComponent();
        currentMap.put(k, v);
    }
}
