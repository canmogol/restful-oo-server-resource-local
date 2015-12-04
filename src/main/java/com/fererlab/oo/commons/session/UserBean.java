package com.fererlab.oo.commons.session;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@LocalBean
@Stateless(name = "UserBean", mappedName = "UserBean")
public class UserBean {

    @Inject
    private SessionData sessionData;

    public String getUserName() {
        return sessionData.getUsername();
    }

}
