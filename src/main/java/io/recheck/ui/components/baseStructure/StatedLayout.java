package io.recheck.ui.components.baseStructure;

public interface StatedLayout<T extends ComponentsData> extends Layout<T> {

    void toCreateState();
    void toUpdateState();

}
