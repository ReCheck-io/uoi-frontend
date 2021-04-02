package io.recheck.ui.components;

public interface Layout<T extends ComponentsData> {

    T getComponents();
    void initLayout(T components);

}
