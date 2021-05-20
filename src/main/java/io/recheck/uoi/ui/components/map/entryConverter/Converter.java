package io.recheck.uoi.ui.components.map.entryConverter;

import com.vaadin.flow.component.Component;
import io.recheck.uoi.ui.components.map.MapModel;


public interface Converter<KC extends Component, VC extends Component, D extends MapModel, KD, VD> {

    KC toKeyComponent(D data, KD key);
    KC createEmptyKeyComponent();
    KD toKeyData(KC key);
    boolean keyDataIsNotEmpty(KD keyData);

    VC toValueComponent(D data, VD value);
    VC createEmptyValueComponent();
    VD toValueData(VC value);
    boolean valueDataIsNotEmpty(VD valueData);

}
