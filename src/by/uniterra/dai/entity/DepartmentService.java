package by.uniterra.dai.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
//import javax.persistence.Persistence;
//import javax.persistence.TypedQuery;





public class DepartmentService
{
    //ok
    public Department save(EntityManager em,Department department)
    {
	Department depFromDB = null;
	try
	{
	    em.getTransaction().begin();
	    depFromDB = em.merge(department);
	    em.getTransaction().commit();
	} 
	catch (Exception e)
	{
	    em.getTransaction().rollback();
	    throw e;
	}
	return depFromDB;

    }
    //ок
    public Department delete(EntityManager em, int id)
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
    //ок
    public void PrintAllDepartments(EntityManager em)
    {
	Query q = em.createQuery("select d from Department d");
	@SuppressWarnings("unchecked")
	List<Department> depList = q.getResultList();
	for (Department department : depList)
	{
	    System.out.println(department);
	}
	System.out.println("Size: " + depList.size());
    }

    /*private Department get(EntityManager em, int id)
    {
	return em.find(Department.class, id);
    }*/


/*    public List<Department> getAll(EntityManager em)
    {
	TypedQuery<Department> namedQuery = em.createNamedQuery(
		"Department.getAll", Department.class);
	return namedQuery.getResultList();
    }
*/
}
