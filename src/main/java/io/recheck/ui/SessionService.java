package io.recheck.ui;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import io.recheck.ui.entity.UOINode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@VaadinSessionScope
public class SessionService {

    private List<UOINode> dataProvider = new ArrayList<>();

    public List<UOINode> getDataProvider(){
        return dataProvider;
    }
}
