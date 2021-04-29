package io.recheck.ui.components.list;

import java.util.List;

public interface ListModel<D> {

    List<D> getList();
    void setList(List<D> list);

}
