package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.Worker;


public class WorkerrService extends ServiceBase<Worker>
{
    /**
     * 
     * Constructor.
     *
     * @param em - EntityManager to be used in the class
     */
    public WorkerrService(EntityManager em)
    {
        super(em, Worker.class);
    }

}
