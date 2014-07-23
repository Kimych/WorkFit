package by.uniterra.system.main;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.eao.HolidayService;
import by.uniterra.dai.eao.NameMonthService;
import by.uniterra.dai.eao.WorkerService;
import by.uniterra.dai.eao.YearService;
import by.uniterra.dai.entity.Holiday;
import by.uniterra.dai.entity.NameMonth;
import by.uniterra.dai.entity.Worker;
import by.uniterra.dai.entity.Year;

public class Task13
{
	private static final String PERSISTENCE_UNIT_NAME = "WorkFit";

	public static int ID_DEL_YEAR = 6;
	public static int ID_DEL_MOUTH_NAME = 13;
	public static int ID_DEL_WORKER = 4;
	/*public static int ID_WORKER_ADD_HOLIDAY = 1;
	public static int ID_YEAR_ADD_HOLIDAY = 1;*/

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

		/*
		 * // print Year YearService service = new YearService(emManager);
		 * service.printAllItems();
		 * 
		 * // add new Year Year year = new Year(); year.setNumber(2015);
		 * year.setDeskription("test description"); service.save(year);
		 * 
		 * // print Year service.printAllItems();
		 * 
		 * // dell Year YearService serviceDell = new YearService(emManager);
		 * serviceDell.delete(ID_DEL_YEAR); serviceDell.printAllItems();
		 * 
		 * //print Name NameMonthService service2 = new
		 * NameMonthService(emManager); service2.printAllItems();
		 * 
		 * // add new Name NameMonth nameMonth = new NameMonth();
		 * nameMonth.setName("Небритябрь"); service2.save(nameMonth);
		 * service2.printAllItems();
		 * 
		 * // dell Name NameMonthService service2Dell = new
		 * NameMonthService(emManager); service2Dell.delete(ID_DEL_MOUTH_NAME);
		 * service2Dell.printAllItems();
		 * 
		 * //print Worker WorkerService service3 = new WorkerService(emManager);
		 * service3.printAllItems();
		 * 
		 * //add new Worker Worker worker = new Worker();
		 * worker.setFirstName(""); worker.setSecondName("Второе имя");
		 * worker.setThirdName("Третье имя"); service3.save(worker);
		 * service3.printAllItems();
		 * 
		 * //dell Worker WorkerService service3Dell = new
		 * WorkerService(emManager); service3Dell.delete(ID_DEL_WORKER);
		 * service3Dell.printAllItems();
		 */

		// print Holiday
		HolidayService service4 = new HolidayService(emManager);
		service4.printAllItems();

		
		
		// add Holiday
		Holiday holiday = new Holiday();
		holiday.setCountDays(24);
		
		/*WorkerService sericeAddWorker = new WorkerService(emManager);
		YearService serviceAddYear = new YearService(emManager);
		holiday.setWorker(sericeAddWorker.find(ID_WORKER_ADD_HOLIDAY));
		holiday.setYear(serviceAddYear.find(ID_YEAR_ADD_HOLIDAY));
		service4.save(holiday);*/

		service4.printAllItems();

		disconnectFromDb();
	}

}
