package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Authorization;

public class AuthorizationEAO extends ServiceBaseEAO<Authorization>
{

    public AuthorizationEAO(EntityManager em)
    {
        super(em, Authorization.class);
    }

}
