package by.uniterra.dai.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.Department;

public class DepartmentService
{
    private EntityManager em;

    /**
     * 
     * Constructor.
     *
     * @param em - EntityManager to be used in the class
     */
    public DepartmentService(EntityManager em)
    {
        super();
        this.em = em;
    }

    public Department save(Department department)
    {
        Department depFromDB = null;
        try
        {
            em.getTransaction().begin();
            depFromDB = em.merge(department);
            em.getTransaction().commit();
        }
        catch(Exception e)
        {
            em.getTransaction().rollback();
            throw e;
        }
        return depFromDB;

    }

    public Department delete(int id)
    {
        Department department = em.find(Department.class, id);
        try
        {
            em.getTransaction().begin();
            em.remove(department);
            em.getTransaction().commit();
        }
        catch(Exception e)
        {
            em.getTransaction().rollback();
            throw e;
        }
        return department;
    }

    public void PrintAllDepartments()
    {
        Query q = em.createQuery("select d from Department d");
        @SuppressWarnings("unchecked")
        List<Department> depList = q.getResultList();
        for(Department department : depList)
        {
            System.out.println(department);
        }
        System.out.println("Size: " + depList.size());
    }

}
