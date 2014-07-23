package by.uniterra.system.main;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class Main
{

	private static final String PERSISTENCE_UNIT_NAME = "WorkFit";

	private static EntityManagerFactory emfFactory;
	private static EntityManager emManager;

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
		// a new connection (factory) to target DB
		emfFactory = Persistence.createEntityManagerFactory(
				PERSISTENCE_UNIT_NAME, mapCustomProp);
		emManager = emfFactory.createEntityManager();
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

	public static void main(String[] args)
	{
		connectToDB();
		disconnectFromDb();
	}
}
