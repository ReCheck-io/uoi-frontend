package io.recheck.ui.components.baseStructure;


import java.io.Serializable;

public interface ClickListener<T> extends Serializable {
    void onClick(T item);
}
