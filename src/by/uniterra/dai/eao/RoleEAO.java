package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Role;

public class RoleEAO extends ServiceBaseEAO<Role>
{

    public RoleEAO(EntityManager em)
    {
        super(em, Role.class);
    }

}
