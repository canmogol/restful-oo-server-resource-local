package com.fererlab.oo.commons.model.interfaces;

import java.util.List;

public interface Persistable<E, PK> {

    void create();

    void update();

    void delete();

    void find() throws Exception;

    List<E> findAll();

    PK count();

}
