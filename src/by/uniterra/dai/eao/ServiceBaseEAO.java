package by.uniterra.dai.eao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MethodNotSupportedException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import by.uniterra.dai.model.AbstractEntityListProcessor;
import by.uniterra.dai.model.AbstractEntityProcessor;


public class ServiceBaseEAO<T extends Serializable>
{
    // members
    protected EntityManager emEntityManager;// associated Entity Manager
    protected boolean bRefreshCachedObjects = false; // objects refresh flag
    protected Class<T> classEntity; // Additional type information that we can't get from T
    
    protected class BulkSaver extends AbstractEntityListProcessor<T>
    {
        /**
         * Constructor.
         * 
         * @param entity - entities list to be processed
         * @param emManager - associated EntityManager
         */
        public BulkSaver(List<T> entity, EntityManager emManager)
        {
            super(entity, emManager);
        }

        @Override
        protected List<T> doProcess()
        {
            int ip = 0;
            for (int i = 0; i < lstEntities.size(); i++)
            {
                // update given entity with saved instance
                lstEntities.set(i, emManager.merge(lstEntities.get(i)));
                ip++;
                // optimize cache
                if ((ip % 20) == 0)
                {
                    emManager.flush();
                    emManager.clear();
                }
            }
            return lstEntities;
        }
    }
    protected class Deleter extends AbstractEntityProcessor<T>
    {
        // members
        private List<T> lstItemsToDelete;

        public Deleter(List<T> loadAll, EntityManager emManager)
        {
            super(null, emManager);
            lstItemsToDelete = loadAll;
        }

        /**
         * Constructor.
         * 
         * @param entity - entity to be processed
         * @param emManager - associated EntityManager
         */
        public Deleter(T entity, EntityManager emManager)
        {
            this(new ArrayList<T>(Arrays.asList(entity)), emManager);
        }

        @Override
        protected T doProcess()
        {
            for (T t : lstItemsToDelete)
            {
                // check id the entity managed
                if (!emManager.contains(t))
                {
                    // make it managed
                    t = emManager.merge(t);
                }
                // remove managed entity
                emManager.remove(t);
            }
            // return it
            return null;
        }
    }
    // inner classes
    protected class Refresher extends AbstractEntityProcessor<T>
    {
        /**
         * Constructor.
         * 
         * @param entity - entity to be processed
         * @param emManager - associated EntityManager
         */
        public Refresher(T entity, EntityManager emManager)
        {
            super(entity, emManager);
        }

        /**
         * @see net.envinet.common.model.AbstractEAO.Processor#doProcess()
         */
        @Override
        protected T doProcess()
        {
            // refresh entity
            emManager.refresh(tEntity);
            // return it
            return tEntity;
        }
    }
    protected class RefresherList extends AbstractEntityListProcessor<T>
    {
        /**
         * Constructor.
         * 
         * @param entity - entity list to be refreshed
         * @param emManager - associated entity manager
         */
        public RefresherList(List<T> entity, EntityManager emManager)
        {
            super(entity, emManager);
        }

        /**
         * @see net.envinet.common.model.AbstractEntityListProcessor#doProcess()
         */
        @Override
        protected List<T> doProcess()
        {
            // refresh each entity
            for (T tEntity : lstEntities)
            {
                emManager.refresh(tEntity);
            }
            return lstEntities;
        }
    }
    protected class Saver extends AbstractEntityProcessor<T>
    {
        /**
         * Constructor.
         * 
         * @param entity - entity to be processed
         * @param emManager - associated EntityManager
         */
        public Saver(T entity, EntityManager emManager)
        {
            super(entity, emManager);
        }

        /**
         * @see net.envinet.common.model.AbstractEAO.Processor#doProcess()
         */
        @Override
        protected T doProcess()
        {
            // return saved object
            return emManager.merge(tEntity);
        }
    }

    /**
     * Provide type information.<br>
     * Usage:<br>
     * super(EntityName.class);
     * 
     * @param entityClass
     */
    public ServiceBaseEAO(EntityManager emEntityManager, Class<T> entityClass)
    {
        // set managed entity
        classEntity = entityClass;
        // set EntityManager
        this.emEntityManager = emEntityManager;
    }

    public boolean contains(Object objItem)
    {
        return emEntityManager.contains(objItem);
    }
    
    /**
     * Get objects count
     * 
     * @return object count
     */
    public long count()
    {
        CriteriaQuery<Object> criteriaQuery = emEntityManager.getCriteriaBuilder().createQuery();
        Root<T> root = criteriaQuery.from(classEntity);
        criteriaQuery.select(emEntityManager.getCriteriaBuilder().count(root));
        Query query = emEntityManager.createQuery(criteriaQuery);
        return ((Long) query.getSingleResult()).longValue();
    }

    /**
     * Remove by PK
     * 
     * @param id - id of the entity to be removed
     */
    public void delete(Object id)
    {
        delete(emEntityManager.find(classEntity, id));
    }

    /**
     * Remove entity.
     * 
     * @param entity - entity to remove
     */
    public void delete(T entity)
    {
        // remove object
        new Deleter(entity, emEntityManager).execute();
    }

    /**
     * Remove set of entities by according "where" condition
     * 
     * @param findByParentId4Types - "where" condition ID
     * @param objects - parameters for the condition
     */
    public void deleteAll(int findByParentId4Types, Object[] objects)
    {
        List<T> lstItemsToDelete = (List<T>) loadAll(findByParentId4Types, objects);
        if (!lstItemsToDelete.isEmpty())
        {
            // remove object
            new Deleter(lstItemsToDelete, emEntityManager).execute();
        }
    }

    /**
     * Detach given entities list from current EntityManager
     * 
     * @param entity - entities list to be detached
     * @return - detached entities list
     */
    public List<T> detach(List<T> lstEntities)
    {
        // detach each entity
        for (T t : lstEntities)
        {
            emEntityManager.detach(t);
        }
        return lstEntities;
    }

    /**
     * Detach given entity from current EntityManager
     * 
     * @param entity - entity to be detached from current EntityManager
     * @return - detached entity
     */
    public T detach(T entity)
    {
        emEntityManager.detach(entity);
        return entity;
    }

    /**
     * Detach given entities list from current EntityManager
     * 
     * @param entity - entities list to be detached
     * @param bIsDetachAlloved - a flag which allows detaching
     */
    protected List<T> detachAuto(List<T> lstEntities, boolean bIsDetachAlloved)
    {
        if (bIsDetachAlloved)
        {
            // detach each entity
            for (T t : lstEntities)
            {
                emEntityManager.detach(t);
            }
        }
        return lstEntities;
    }

    /**
     * Detach given entity from current EntityManager
     * 
     * @param entity - entity to be detached
     * @param bIsDetachAlloved - a flag which allows detaching
     */
    protected T detachAuto(T entity, boolean bIsDetachAlloved)
    {
        if (bIsDetachAlloved)
        {
            emEntityManager.detach(entity);
        }
        return entity;
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        // try to close associated EntityManager
        if (emEntityManager != null && emEntityManager.isOpen())
        {
            emEntityManager.clear();
        }
    }

    /**
     * Find by PK.
     * 
     * @param id - id to search by
     * @return - found object
     */
    public T find(Object id)
    {
        // get object
        T tObject = emEntityManager.find(classEntity, id);
        // return refreshed/detached item
        return refreshAuto(tObject, bRefreshCachedObjects);
    }

    /**
     * builds basic SQL statement for the Entity in a style "Select All".
     * 
     * @return SQL statement
     */
    protected String getBasicSQL()
    {
        return "select o from " + classEntity.getSimpleName() + " o ";
    }

    /**
     * Get assigned Entity Manager
     * 
     * @return Entity manager.
     */
    protected EntityManager getEntityManager()
    {
        return emEntityManager;
    }

    public TypedQuery<T> getNamedQuery(String queryName)
    {
        return emEntityManager.createNamedQuery(queryName, classEntity);
    }

    public TypedQuery<T> getQuery(String queryString)
    {
        return emEntityManager.createQuery(queryString, classEntity);
    }

    /**
     * Get according "where" clause for given search type
     * 
     * @param findByParentId4Types - search type ID
     * 
     * @return a string with according "where" clause
     * @throws MethodNotSupportedException
     */
    protected String getWhereClause(int findByParentId4Types)
    {
        return null;
    }

    /**
     * Returns the state of "refresh each object" flag.
     * 
     * @return Returns the m_bRefreshCacheedObjects.
     */
    public boolean isRefreshCachedObjects()
    {
        return bRefreshCachedObjects;
    }

    /**
     * Load all entities.
     * 
     * @return - list of loaded entities
     */
    public List<T> loadAll()
    {
        List<T> lstObjects = emEntityManager.createQuery(getBasicSQL(), classEntity).getResultList();
        // return updated/detached list
        return refreshAuto(lstObjects, bRefreshCachedObjects);
    }

    /**
     * Load entities by according conditions
     * 
     * @param findByParentId4Types - search type ID
     * @param param - array of search argument
     * 
     * @return result list
     */
    public List<?> loadAll(int findByParentId4Types, Object[] param)
    {
        final List<T> lstObjects = new ArrayList<T>();
        String strWhereClause = getWhereClause(findByParentId4Types);
        if (strWhereClause != null && !strWhereClause.isEmpty())
        {
            // create query
            Query q = emEntityManager.createQuery(getBasicSQL() + " where " + strWhereClause, classEntity);
            // set parameters
            for (int i = 1; i <= param.length; i++)
            {
                q.setParameter(i, param[i - 1]);
            }
            // execute query
            lstObjects.addAll(q.getResultList());
        }
        // return updated/detached list
        return refreshAuto(lstObjects, bRefreshCachedObjects);
    }

    /**
     * Execute named query.
     * 
     * @param queryName - query name
     * 
     * @return query result list
     */
    public List<T> loadNamedQuery(String queryName)
    {
        List<T> lstObjects = getNamedQuery(queryName).getResultList();
        return refreshAuto(lstObjects, bRefreshCachedObjects);
    }

    public List<T> loadQuery(String queryString)
    {
        List<T> lstObjects = getQuery(queryString).getResultList();
        // refresh all entities
        return refreshAuto(lstObjects, bRefreshCachedObjects);
    }

    /**
     * Reload data of the entity from DB.
     * 
     * @param entity - entity to be refreshed
     */
    public T refresh(T entity)
    {
        // return refreshed object
        return new Refresher(entity, emEntityManager).execute();
    }

    /**
     * Make a refresh operation on given entities list.
     * 
     * @param lstEntities - list of entities to be refreshed
     * @param bIsRefreshAlloved - refresh resolution flag
     * 
     * @return - refreshed objects
     */
    protected List<T> refreshAuto(List<T> lstEntities, boolean bIsRefreshAlloved)
    {
        // return refreshed objects
        return bIsRefreshAlloved ? new RefresherList(lstEntities, emEntityManager).doProcess() : lstEntities;
    }

    /**
     * Make a refresh operation on given entity
     * 
     * @param entity - entity to be refreshed
     * @param bIsRefreshAlloved - refresh resolution flag
     */
    protected T refreshAuto(T entity, boolean bIsRefreshAlloved)
    {
        return bIsRefreshAlloved ? refresh(entity) : entity;
    }

    /**
     * Save given entity
     * 
     * @param entity - entity to be saved
     */
    public T save(T entity)
    {
        // save object
        return new Saver(entity, emEntityManager).execute();
    }

    /**
     * Make a batch insert for AbstractBaseJPA objects
     * 
     * @param lstDataToInsert - list of AbstractEAO entities
     */
    public List<T> saveBulk(List<T> lstDataToInsert)
    {
        return new BulkSaver(lstDataToInsert, emEntityManager).execute();
    }

    /**
     * Sets new state for "refresh each object" flag
     * 
     * @param refreshObjects The m_bRefreshCacheedObjects to set.
     */
    public void setRefreshCachedObjects(boolean refreshObjects)
    {
        bRefreshCachedObjects = refreshObjects;
    }

    public Class<T> getClassType()
    {
        return classEntity;
    }
}