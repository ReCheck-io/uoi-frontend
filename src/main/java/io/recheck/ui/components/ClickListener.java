package io.recheck.ui.components;


import java.io.Serializable;

public interface ClickListener<T> extends Serializable {
    void onClick(T item);
}
