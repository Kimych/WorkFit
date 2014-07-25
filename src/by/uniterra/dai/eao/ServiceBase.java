package by.uniterra.dai.eao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ServiceBase<T extends Serializable>
{
	protected EntityManager em;
	protected Class<T> classType;

	public ServiceBase(EntityManager em, Class<T> classType)
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
		try
		{
			em.getTransaction().begin();
			newEntity = em.merge(entity);
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			em.getTransaction().rollback();
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
		try
		{
			em.getTransaction().begin();
			em.remove(id.getClass().equals(classType) ? id : em.find(classType, id));
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			em.getTransaction().rollback();
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
			try
			{
				em.getTransaction().begin();
				em.remove(entity);
				em.getTransaction().commit();
			}
			catch (Exception e)
			{
				em.getTransaction().rollback();
				throw e;
			}
		}
	}

}