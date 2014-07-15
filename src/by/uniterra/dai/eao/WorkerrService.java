package by.uniterra.dai.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.uniterra.dai.entity.Worker;


public class WorkerrService
{

    private EntityManager em;
    
    
    /**
     * 
     * Constructor.
     *
     * @param em - EntityManager to be used in the class
     */
    public WorkerrService(EntityManager em)
    {
        super();
        this.em = em;
    }

    public Worker save(Worker worker)
    {
        Worker workFromDB = null;
        try
        {
            em.getTransaction().begin();
            workFromDB = em.merge(worker);
            em.getTransaction().commit();
        }
        catch(Exception e)
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
        for(Worker worker : workList)
        {
            System.out.println(worker);
        }
        System.out.println("Size: " + workList.size());
    }

}
