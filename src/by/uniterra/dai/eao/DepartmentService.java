package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Department;

public class DepartmentService extends ServiceBase<Department>
{
    /**
     * 
     * Constructor.
     * 
     * @param em - EntityManager to be used in the class
     */
    public DepartmentService(EntityManager em)
    {
        super(em, Department.class);
    }

}
