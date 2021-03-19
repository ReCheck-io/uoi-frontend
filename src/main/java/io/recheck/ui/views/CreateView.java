package io.recheck.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import io.recheck.ui.RestClientService;
import io.recheck.ui.SessionService;
import io.recheck.ui.entity.LEVEL;
import io.recheck.ui.entity.UOINode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@CssImport("./views/create/create-view.css")
@Route(value = "create", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Create / Update")
public class CreateView extends Div {

    private HorizontalLayout mainCreateLayout = new HorizontalLayout();
    private VerticalLayout createResultLayout = new VerticalLayout();

    private TextField countryCodeField = new TextField();
    private ComboBox<String> levelField = new ComboBox<>();
    private TextField parentUOIField = new TextField();

    private Button createButton = new Button("Create");
    private Button updateButton = new Button("Update");
    private Button cancelButton = new Button("Cancel");
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private VerticalLayout gridLayout = new VerticalLayout();
    private Label gridLabel = new Label("Existing UOI nodes");
    private Grid<UOINode> grid = new Grid<>();


    private HorizontalLayout propertiesLayout = new HorizontalLayout();
    private VerticalLayout propertiesLayoutVert = new VerticalLayout();

    private HorizontalLayout propertiesLayoutTitleButtons = new HorizontalLayout();
    private H3 propertiesTitle = new H3("Properties");
    private Button propertiesAddButton = new Button("Add");

    private HorizontalLayout propertiesLayoutLabels = new HorizontalLayout();
    private Label propertiesKeyLabel = new Label("Key:");
    private Label propertiesValueLabel = new Label("Value:");

    public CreateView(@Autowired RestClientService restClientService, @Autowired SessionService sessionService) {
        addClassName("create-view");

        //Create layout
        createResultLayout.getStyle().clear();
        createResultLayout.addClassName("createResultLayout");

        countryCodeField.setPlaceholder("countryCode");
        levelField.setItems(Arrays.stream(LEVEL.values()).map(Enum::name).collect(Collectors.toList()));
        levelField.setPlaceholder("Choose level");
        parentUOIField.setPlaceholder("parentUOI");

        FormLayout formLayout = new FormLayout();
        formLayout.add(countryCodeField, levelField, parentUOIField);
        createResultLayout.add(formLayout);

        buttonsLayout.add(createButton, updateButton, cancelButton);
        createResultLayout.add(buttonsLayout);

        //Result / Grid layout
        grid.addColumn(UOINode::getUoi).setHeader("UOI");
        grid.addColumn(UOINode::getCountryCode).setHeader("Country Code").setVisible(false);
        grid.addColumn(UOINode::getLevel).setHeader("Level");
        grid.addColumn(UOINode::getParentUOI).setHeader("Parent UOI");
        grid.getColumns().forEach(c -> c.setAutoWidth(true));
        gridLayout.add(gridLabel, grid);
        grid.setItems(sessionService.getDataProvider());

        createResultLayout.add(gridLayout);


        //Properties layout
        propertiesLayoutTitleButtons.add(propertiesTitle, propertiesAddButton);
        propertiesLayoutTitleButtons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        propertiesValueLabel.addClassName("valueLabel");
        propertiesLayoutLabels.add(propertiesKeyLabel, propertiesValueLabel);
        propertiesLayoutVert.add(propertiesLayoutTitleButtons, propertiesLayoutLabels);
        propertiesLayout.add(propertiesLayoutVert);

        //Main layout & default state
        mainCreateLayout.add(createResultLayout, propertiesLayout);
        add(mainCreateLayout);
        toCreateState();


        //=================================================
        //                  EVENTS
        //=================================================
        createButton.addClickListener(e -> {
            sessionService.getDataProvider().add(restClientService.newUOI(countryCodeField.getValue(), levelField.getValue(), parentUOIField.getValue()));
            grid.getDataProvider().refreshAll();
            grid.getColumns().forEach(c -> c.setAutoWidth(true));
            clearFormLayoutFields();
        });

        updateButton.addClickListener(e -> {
            UOINode uoiNode = grid.getSelectedItems().iterator().next();
            Map<String, String> properties = getProperties();
            restClientService.updateProperties(uoiNode.getUoi(), properties);
            uoiNode.setProperties(properties);
            toCreateState();
        });

        cancelButton.addClickListener(e -> toCreateState());

        grid.addItemClickListener(event -> {
            toUpdateState();

            UOINode item = event.getItem();
            grid.select(item);

            countryCodeField.setValue(item.getCountryCode());
            levelField.setValue(item.getLevel().name());
            parentUOIField.setValue(Optional.ofNullable(item.getParentUOI()).orElse(""));
            item.getProperties().forEach(this::addPropertiesTextFields);
        });

        propertiesAddButton.addClickListener(e -> addPropertiesTextFields());





    }

    private Map<String, String> getProperties() {
        List<Component> propertiesLayoutTextFieldsList = propertiesLayoutVert.getChildren()
                .filter(p -> p.getElement().getClassList().contains("propertiesLayoutTextFields"))
                .collect(Collectors.toList());


        Map<String, String> propertiesUOI = new HashMap<>();
        for (Component propertiesLayoutTextFields : propertiesLayoutTextFieldsList) {
            Optional<Component> keyCP = propertiesLayoutTextFields.getChildren()
                    .filter(prop -> ((TextField) prop).getClassNames().contains("keyField"))
                    .findFirst();
            Optional<Component> valueCP = propertiesLayoutTextFields.getChildren()
                    .filter(prop -> ((TextField) prop).getClassNames().contains("valueField"))
                    .findFirst();

            if (keyCP.isPresent() && valueCP.isPresent()) {
                String key = ((TextField) keyCP.get()).getValue();
                String value = ((TextField) valueCP.get()).getValue();
                if (StringUtils.hasText(key) && StringUtils.hasText(value)) {
                    propertiesUOI.put(key, value);
                }
            }
        }
        return propertiesUOI;
    }

    private void addPropertiesTextFields() {
        addPropertiesTextFields(null, null);
    }
    
    private void addPropertiesTextFields(String key, String value) {
        TextField keyField = new TextField();
        keyField.addClassName("keyField");
        TextField valueField = new TextField();
        valueField.addClassName("valueField");

        if (StringUtils.hasText(key))
            keyField.setValue(key);
        if (StringUtils.hasText(value))
            valueField.setValue(value);

        HorizontalLayout propertiesLayoutTextFields = new HorizontalLayout();
        propertiesLayoutTextFields.add(keyField, valueField);
        propertiesLayoutTextFields.addClassName("propertiesLayoutTextFields");

        propertiesLayoutVert.add(propertiesLayoutTextFields);
        propertiesLayoutLabels.setVisible(true);
    }

    private void toUpdateState() {
        propertiesLayoutVert.removeAll();
        propertiesLayoutVert.add(propertiesLayoutTitleButtons, propertiesLayoutLabels);
        createButton.setVisible(false);
        updateButton.setVisible(true);
        cancelButton.setVisible(true);
        propertiesLayout.setVisible(true);
        countryCodeField.setEnabled(false);
        levelField.setEnabled(false);
    }

    private void toCreateState() {
        clearFormLayoutFields();

        createButton.setVisible(true);
        updateButton.setVisible(false);
        cancelButton.setVisible(false);
        propertiesLayoutLabels.setVisible(false);
        propertiesLayout.setVisible(false);
        countryCodeField.setEnabled(true);
        levelField.setEnabled(true);
    }

    private void clearFormLayoutFields() {
        countryCodeField.setValue("");
        levelField.setValue("");
        parentUOIField.setValue("");
    }

}
