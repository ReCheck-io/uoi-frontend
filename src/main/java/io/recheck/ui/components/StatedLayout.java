package io.recheck.ui.components;

public interface StatedLayout<T extends ComponentsData> extends Layout<T> {

    void toCreateState();
    void toUpdateState();

}
