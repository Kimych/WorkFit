package by.uniterra.dai.eao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ServiceBaseEAO<T extends Serializable>
{
	protected EntityManager em;
	protected Class<T> classType;
	//objects refresh flag
    protected boolean m_bRefreshCachedObjects = true;

	public ServiceBaseEAO(EntityManager em, Class<T> classType)
	{
		this.em = em;
		this.classType = classType;
	}

	/**
	 * Save given entity
	 * 
	 * @param entity
	 *            - entity to be saved
	 * @return persisted entity
	 */
	public T save(T entity)
	{
		T newEntity = null;
		EntityTransaction tx = null;
		try
		{
		    tx = em.getTransaction();
		    tx.begin();
			newEntity = em.merge(entity);
			tx.commit();
		}
		catch (Exception e)
		{
		    if(tx != null && tx.isActive())
		    {
		        tx.rollback();
		    }
			throw e;
		}
		return newEntity;

	}

	/**
	 * Delete entity by given Id
	 * 
	 * @param id
	 *            - entity ID
	 */
	public void delete(Object id)
	{
	    EntityTransaction tx = null;
		try
		{
		    tx = em.getTransaction();
		    tx.begin();
			em.remove(id.getClass().equals(classType) ? id : em.find(classType, id));
			tx.commit();
		}
		catch (Exception e)
		{
		    if(tx != null && tx.isActive())
            {
                tx.rollback();
            }
			throw e;
		}
	}

	public void printAllItems()
	{
		Query q = em.createQuery("select d from " + classType.getSimpleName()
				+ " d");
		@SuppressWarnings("unchecked")
		List<T> lstEntities = q.getResultList();
		for (T entity : lstEntities)
		{
			System.out.println(entity);
		}
		System.out.println("Size: " + lstEntities.size());
	}

	public T find(Object id)
	{
		// get object
		T tObject = em.find(classType, id);
		// return refreshed item
		return tObject;
	}

	public void remove(T entity)
	{
		if (entity != null)
		{
		    EntityTransaction tx = null;
			try
			{
				tx = em.getTransaction();
				tx.begin();
				em.remove(entity);
				tx.commit();
			}
			catch (Exception e)
			{
			    if(tx != null && tx.isActive())
	            {
	                tx.rollback();
	            }
				throw e;
			}
		}
	}
	
	public List<T> loadAll()
    {
        List<T> lstObjects = em.createQuery(getBasicSQL(), classType).getResultList(); 
        return lstObjects;
    }
	
    protected String getBasicSQL()
    {
        return "select o from " + classType.getSimpleName() + " o ";
    }

}