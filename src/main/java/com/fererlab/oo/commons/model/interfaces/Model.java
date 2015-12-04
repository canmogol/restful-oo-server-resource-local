package com.fererlab.oo.commons.model.interfaces;

import java.io.Serializable;

public interface Model<T> extends Serializable {

    T getId();

    void setId(T t);

}
