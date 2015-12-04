package com.fererlab.oo.commons.restful;

import com.fererlab.oo.commons.model.interfaces.EntityManagerAware;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerAwareInterceptor {

    @PersistenceContext
    private EntityManager entityManager;

    @AroundInvoke
    public Object mdbInterceptor(InvocationContext ctx) throws Exception {
        for (Object param : ctx.getParameters()) {
            if (param != null && param instanceof EntityManagerAware) {
                EntityManagerAware entityManagerAware = (EntityManagerAware) param;
                entityManagerAware.setEntityManager(entityManager);
            }
        }
        return ctx.proceed();
    }


}
