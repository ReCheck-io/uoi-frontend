package io.recheck.ui.components.baseStructure;

public interface Layout<T extends ComponentsData> {

    T getComponents();
    void initLayout(T components);

}
