package by.uniterra.system.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.eao.DaysOfWorkService;
import by.uniterra.dai.eao.HolidayService;
import by.uniterra.dai.eao.MonthService;
import by.uniterra.dai.eao.NameMonthService;
import by.uniterra.dai.eao.SpentHolidayService;
import by.uniterra.dai.eao.WorkerService;
import by.uniterra.dai.eao.YearService;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Holiday;
import by.uniterra.dai.entity.HolidayPK;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.NameMonth;
import by.uniterra.dai.entity.SpentHoliday;
import by.uniterra.dai.entity.SpentHolidayPK;
import by.uniterra.dai.entity.Worker;
import by.uniterra.dai.entity.Year;

public class Task13
{
	private static final String PERSISTENCE_UNIT_NAME = "WorkFit";

	public static int ID_DEL_YEAR = 6;
	public static int ID_DEL_MONTH_NAME = 13;
	public static int ID_DEL_WORKER = 4;
	public static int ID_WORKER_ADD_HOLIDAY = 2;
	public static int ID_YEAR_ADD_HOLIDAY = 1;
	public static int ID_WORKER_ADD_SPENT_HOLIDAY = 2;
	public static int ID_MONTH_ADD_SPENT_HOLIDAY = 1;
	public static int ID_NAME_MONTH_ADD_MONTH = 8;
	public static int ID_YEAR_ADD_MONTH = 1;
	public static int ID_DEL_MONTH = 7;
	public static int ID_MONTH_ADD_DAYS_OF_WORK = 4;
	public static int ID_WORKER_ADD_DAYS_OF_WORK = 1;
	public static int ID_DEL_DAYS_OF_WORK = 16;

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
		 * NameMonthService(emManager); service2Dell.delete(ID_DEL_MONTH_NAME);
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
/*		Holiday holiday = new Holiday();
		holiday.setCountDays(24);
		holiday.setId(new HolidayPK(ID_WORKER_ADD_HOLIDAY, ID_YEAR_ADD_HOLIDAY));
		holiday.setWorker(new WorkerService(emManager).find(ID_WORKER_ADD_HOLIDAY));
		holiday.setYear(new YearService(emManager).find(ID_YEAR_ADD_HOLIDAY));
		service4.save(holiday);
		service4.printAllItems();*/
		
		//dell Holiday ???
		HolidayService service4Dell = new HolidayService(emManager);
		Holiday holidayDel = new Holiday();
		holidayDel.setId(new HolidayPK(ID_WORKER_ADD_HOLIDAY, ID_YEAR_ADD_HOLIDAY));
/*		holidayDel.setCountDays(24);
		holidayDel.setWorker(new WorkerService(emManager).find(ID_WORKER_ADD_HOLIDAY));
		holidayDel.setYear(new YearService(emManager).find(ID_YEAR_ADD_HOLIDAY));*/
		service4Dell.remove(holidayDel);
		service4Dell.printAllItems();
		
/*		//print SpentHoliday
		SpentHolidayService service5 = new SpentHolidayService(emManager);
		service5.printAllItems();
		
		//add SpentHoliday
		SpentHoliday spholiday = new SpentHoliday();
		spholiday.setCountDays(5);
		spholiday.setDescription("тестовая запись");
		spholiday.setWorker(new WorkerService(emManager).find(ID_WORKER_ADD_SPENT_HOLIDAY));
		spholiday.setMonth(new MonthService(emManager).find(ID_MONTH_ADD_SPENT_HOLIDAY));
		spholiday.setId(new SpentHolidayPK(ID_WORKER_ADD_SPENT_HOLIDAY, ID_MONTH_ADD_SPENT_HOLIDAY));
		service5.save(spholiday);
		service5.printAllItems();
		
		//dell SpentHoliday ???
*/		
		
/*		//print Month
		MonthService service6 = new MonthService(emManager);
		service6.printAllItems();
		
		//add Month
		Month month = new Month();
		month.setWorkingDaysCount(20);
		month.setDescription("тестовая запись месяца");
		month.setNameMonth(new NameMonthService(emManager).find(ID_NAME_MONTH_ADD_MONTH));
		month.setYear(new YearService(emManager).find(ID_YEAR_ADD_MONTH));
		service6.save(month);
		service6.printAllItems();
		
		//dell Month
		new MonthService(emManager).delete(ID_DEL_MONTH);
		service6.printAllItems();*/
		
/*		//print DaysOfWork
		DaysOfWorkService service7 = new DaysOfWorkService(emManager);
		service7.printAllItems();
		
		//add DaysOfWork
		DaysOfWork days = new DaysOfWork();
		days.setAktualWorkedDays(0);
		days.setBonusTime(33.5);
		days.setBonusTimeDescription("test");
		days.setMonth(new MonthService(emManager).find(ID_MONTH_ADD_DAYS_OF_WORK));
		days.setTimestamp(new Date());
		days.setWorker(new WorkerService(emManager).find(ID_WORKER_ADD_DAYS_OF_WORK));
		days.setWorklog(25.3);
		service7.save(days);
		service7.printAllItems();
		
		//dell 
		new DaysOfWorkService(emManager).delete(ID_DEL_DAYS_OF_WORK);
		service7.printAllItems();*/
		
		disconnectFromDb();
	}

}
