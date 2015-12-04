package com.fererlab.oo.commons.model.interfaces;

import javax.persistence.EntityManager;

public interface EntityManagerAware {

    void setEntityManager(EntityManager entityManager);

}
