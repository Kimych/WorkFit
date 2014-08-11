package by.uniterra.dai.eao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.udi.view.YearTablePanel;

public class ServiceBaseEAO<T extends Serializable>
{
	protected EntityManager em;
	protected Class<T> classType;
	// objects refresh flag
	protected boolean m_bRefreshCachedObjects = true;

	private static final String PERSISTENCE_UNIT_NAME = "WorkFit";
	private static final String CUSTOMER_PROPERTIES = "custom.properties";
	private static final String PROPERTIES_FILE_EXTENSION = ".properties";

	private static EntityManagerFactory emfFactory;
	private static EntityManager emManager;

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
			if (tx != null && tx.isActive())
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
			em.remove(id.getClass().equals(classType) ? id : em.find(classType,
					id));
			tx.commit();
		}
		catch (Exception e)
		{
			if (tx != null && tx.isActive())
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
				if (tx != null && tx.isActive())
				{
					tx.rollback();
				}
				throw e;
			}
		}
	}

	public List<T> loadAll()
	{
		List<T> lstObjects = em.createQuery(getBasicSQL(), classType)
				.getResultList();
		return lstObjects;
	}

	protected String getBasicSQL()
	{
		return "select o from " + classType.getSimpleName() + " o ";
	}

	public static void connectToDB()
	{
		Map<String, String> mapCustomProp = new HashMap<String, String>();
		// put system configuration properties
		mapCustomProp.put(PersistenceUnitProperties.JDBC_URL,
				"jdbc:mysql://192.168.1.19:3306/Workfit");
		mapCustomProp.put(PersistenceUnitProperties.JDBC_USER, "testdb");
		mapCustomProp.put(PersistenceUnitProperties.JDBC_PASSWORD, "testdb");
		mapCustomProp.put(PersistenceUnitProperties.JDBC_DRIVER,
				"com.mysql.jdbc.Driver");
		// the correct way to disable the shared cache (L2 cache)
		mapCustomProp.put("eclipselink.cache.shared.default", "false");
		// try to override hardcoded settigs by custom properties
		overrideSettingsFromResource(mapCustomProp, CUSTOMER_PROPERTIES);
		// a new connection (factory) to target DB
		emfFactory = Persistence.createEntityManagerFactory(
				PERSISTENCE_UNIT_NAME, mapCustomProp);
		/*emManager = emfFactory.createEntityManager();
		return emManager;*/
	}
	
	public static EntityManager getDefaultEM()
	{
		emManager = emfFactory.createEntityManager();
		return emManager;
	}

	/**
	 * Override some DB settings by data from given resource file
	 * 
	 * @param mapCustomProp
	 *            - properties map
	 * @param strResourceName
	 *            - resource file name
	 */
	private static void overrideSettingsFromResource(
			Map<String, String> mapCustomProp, String strResourceName)
	{
		// check if according property file exists
		if (YearTablePanel.class.getClassLoader().getResource(strResourceName) != null)
		{
			try
			{
				// load properties (we should ignore ".properties" extension for
				// the resource file name)
				ResourceBundle res = ResourceBundle.getBundle(strResourceName
						.replace(PROPERTIES_FILE_EXTENSION, ""));
				mapCustomProp.put(PersistenceUnitProperties.JDBC_URL,
						res.getString("db.url"));
				mapCustomProp.put(PersistenceUnitProperties.JDBC_USER,
						res.getString("db.user"));
				mapCustomProp.put(PersistenceUnitProperties.JDBC_PASSWORD,
						res.getString("db.pwd"));
				mapCustomProp.put(PersistenceUnitProperties.JDBC_DRIVER,
						res.getString("db.driver"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void disconnectFromDb()
	{
		// close EntityManager
		if (emManager != null && emManager.isOpen())
		{
			emManager.close();
		}
		// close EntityManagerFactory
		if (emfFactory != null && emfFactory.isOpen())
		{
			emfFactory.close();
		}
	}
}