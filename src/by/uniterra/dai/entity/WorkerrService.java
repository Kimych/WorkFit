package by.uniterra.dai.entity;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;


public class WorkerrService
{
    
    private  EntityManager em = Main.factory.createEntityManager();
	    
    public Worker save( Worker worker)
    {
	Worker workFromDB = null;
	try
	{
	    em.getTransaction().begin();
	    workFromDB = em.merge(worker);
	    em.getTransaction().commit();
	} 
	catch (Exception e)
	{
	    em.getTransaction().rollback();
	    throw e;
	}
	return workFromDB;

    }
    
    public Worker delete(int id)
    {
	Worker worker = em.find(Worker.class, id);
	try
	{
	em.getTransaction().begin();
	em.remove(worker);
	em.getTransaction().commit();
	}
	catch(Exception e)
	{
	    em.getTransaction().rollback();
	    throw e;
	}
	return worker;
    }
    
    public void PrintAllWorkers()
    {
	Query q = em.createQuery("select w from Worker w");
	@SuppressWarnings("unchecked")
	List<Worker> workList = q.getResultList();
	for (Worker worker : workList)
	{
	    System.out.println(worker);
	}
	System.out.println("Size: " + workList.size());
    }
    
    public  void emClose()
    {
	em.close();
    }
 
    

}
