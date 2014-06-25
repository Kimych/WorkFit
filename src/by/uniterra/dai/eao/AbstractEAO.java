/**
 * Filename  : AbstractEAO.java
 *
 *  ***************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ***************************************************************************
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.dai.eao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractEAO<T extends Serializable> 
{
	//associated Entity Manager
	protected EntityManager m_emEntityManager;
	
	//objects refresh flag
	protected boolean m_bRefreshCachedObjects = true;
	
	//Additional type information that we can't get from T
	protected Class<T> mc_EntityClass;

	/**
	 * Provide type information.<br>
	 * Usage:<br>super(EntityName.class);
	 * @param entityClass
	 */
	public AbstractEAO(Class<T> entityClass, EntityManager emEntityManager) 
	{
		//set managed entity
		mc_EntityClass = entityClass;
		//set EntityManager
		m_emEntityManager = emEntityManager;
	}
	
	/**
	 * Get assigned Entity Manager
	 * 
	 * @return Entity manager.
	 */
	protected EntityManager getEntityManager()
	{
		return m_emEntityManager;
	}

	/**
	 * Load all entities
	 * 
	 * @return - list of loaded entities
	 */
	public List<T> loadAll()
	{
	    List<T> lstObjects = m_emEntityManager.createQuery(getBasicSQL(), mc_EntityClass).getResultList();
	    //refresh all entities
	    if (m_bRefreshCachedObjects)
        {
    	    for(T t : lstObjects)
            {
    	        refresh(t);
            }
        }
	    //return updated list
		return lstObjects;
	}
	
	/**
	 * Execute named query
	 * 
	 * @param queryName - query name
	 * 
	 * @return query result list
	 */
	public List<T> loadNamedQuery(String queryName)
	{
	    List<T> lstObjects = getNamedQuery(queryName).getResultList();
        //refresh all entities
        if (m_bRefreshCachedObjects)
        {
            for(T t : lstObjects)
            {
                refresh(t);
            }
        }
		return lstObjects;
	}
	
	public TypedQuery<T> getNamedQuery(String queryName)
	{
		return m_emEntityManager.createNamedQuery(queryName, mc_EntityClass);
	}

	public List<T> loadQuery(String queryString)
	{
	    List<T> lstObjects = getQuery(queryString).getResultList();
        //refresh all entities
        if (m_bRefreshCachedObjects)
        {
            for(T t : lstObjects)
            {
                refresh(t);
            }
        }
		return lstObjects;
	}

	public TypedQuery<T> getQuery(String queryString)
	{
		return m_emEntityManager.createQuery(queryString, mc_EntityClass);
	}
	
	/**
	 * Save given entity
	 * 
	 * @param entity - entity to be saved
	 */
	public T save(T entity) 
	{
		try
		{
			m_emEntityManager.getTransaction().begin();
			entity = m_emEntityManager.merge(entity);
			m_emEntityManager.getTransaction().commit();
		}
		catch (Exception e) 
		{
			m_emEntityManager.getTransaction().rollback();
			throw e;
		}
		return entity;
	}

	/**
	 * Remove entity.
	 * 
	 * @param entity - entity to remove
	 */
	public void remove(T entity) 
	{
		if (entity != null) 
		{
			try
			{
				m_emEntityManager.getTransaction().begin();
				m_emEntityManager.remove (entity);
				m_emEntityManager.getTransaction().commit();
			}
			catch (Exception e) 
			{
				m_emEntityManager.getTransaction().rollback();
				throw e;
			}
		}
	}

	/**
	 * Remove by PK
	 * 
	 * @param id - id of the entity to be removed
	 */
	public void remove(Object id) 
	{
		remove(m_emEntityManager.find(mc_EntityClass, id));
	}

	/**
	 * Find by PK
	 * 
	 * @param id - id to search by
	 * @return - found object
	 */
	public T find(Object id) 
	{
	    //get object
	    T tObject = m_emEntityManager.find(mc_EntityClass, id);
	    //refresh entity
	    if (m_bRefreshCachedObjects)
	    {
	        refresh(tObject);
	    }
	    //return refreshed item
		return tObject;
	}

	/**
	 * Reload data of the entity from DB.
	 * 
	 * @param entity - entiry to be refreshed
	 */
	public void refresh(T entity) 
	{
		try
		{
			m_emEntityManager.getTransaction().begin();
			m_emEntityManager.refresh (entity);
			m_emEntityManager.getTransaction().commit();
		}
		catch (Exception e) 
		{
			m_emEntityManager.getTransaction().rollback();
			throw e;
		}
	}

	/**
     * builds basic SQL statement for the Entity in a style "Select All".
     * 
     * @return SQL statement
     */
    protected String getBasicSQL()
    {
        return "select o from " + mc_EntityClass.getSimpleName() + " o ";
    }
	
    /**
     * Get objects count
     * 
     * @return object count
     */
	public long count() 
	{
		CriteriaQuery<Object> criteriaQuery = m_emEntityManager.getCriteriaBuilder()
				.createQuery();
		Root<T> root = criteriaQuery.from(mc_EntityClass);
		criteriaQuery.select(m_emEntityManager.getCriteriaBuilder()
				.count(root));
		Query query = m_emEntityManager.createQuery(criteriaQuery);
		return ((Long) query.getSingleResult()).longValue();
	}
	
	/**
     * Make a batch insert for AbstractBaseJPA objects
     * 
     * @param lstDataToInsert - list of AbstractEAO entities
     * @return true if success; false otherwises
     */
    public boolean insertBulk(List<?> lstDataToInsert)
    {
        boolean bResult = false;
        try
        {
            m_emEntityManager.getTransaction().begin();
            int ip = 0;
            for(Object object : lstDataToInsert)
            {
                m_emEntityManager.merge(object);
                ip++;
                // optimize cache
                if((ip % 20) == 0)
                {
                    m_emEntityManager.flush();
                    m_emEntityManager.clear();
                }
            }
            m_emEntityManager.getTransaction().commit();
            bResult = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            m_emEntityManager.getTransaction().rollback();
        }
        return bResult;
    }

    /**
     * Returns the state of "refresh each object" flag.
     *
     * @return Returns the m_bRefreshCacheedObjects.
     */
    public boolean isRefreshCachedObjects()
    {
        return m_bRefreshCachedObjects;
    }

    /**
     * Sets new state for "refresh each object" flag
     *
     * @param m_bRefreshCachedObjects The m_bRefreshCacheedObjects to set.
     */
    public void setRefreshCachedObjects(boolean m_bRefreshCachedObjects)
    {
        this.m_bRefreshCachedObjects = m_bRefreshCachedObjects;
    }
}