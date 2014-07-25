package by.uniterra.dai;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.omg.CORBA.UnknownUserException;

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

public class AllEntitiesTest
{
	private static final String PERSISTENCE_UNIT_NAME = "WorkFit";
	public ExpectedException thrown = ExpectedException.none();
	private static EntityManagerFactory emfFactory;
	private static EntityManager emManager;

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

	public static void addDellYear()
	{
		Year year = new Year();
		year.setYearId(ID_DEL_YEAR);
		year.setNumber(2015);
		year.setDeskription("test description");
		new YearService(emManager).save(year);

		new YearService(emManager).delete(ID_DEL_YEAR);
	}

	public static void addDellNameMonth()
	{
		NameMonth nameMonth = new NameMonth();
		nameMonth.setNameMonthId(ID_DEL_MONTH_NAME);
		nameMonth.setName("Небритябрь");
		new NameMonthService(emManager).save(nameMonth);

		new NameMonthService(emManager).delete(ID_DEL_MONTH_NAME);
	}

	public static void addDellWorker()
	{
		Worker worker = new Worker();
		worker.setWorkerId(ID_DEL_WORKER);
		worker.setFirstName("");
		worker.setSecondName("Второе имя");
		worker.setThirdName("Третье имя");
		new WorkerService(emManager).save(worker);

		new WorkerService(emManager).delete(ID_DEL_WORKER);
	}

	public static void addDellHoliday()
	{
		Holiday holiday = new Holiday();
		holiday.setId(new HolidayPK(ID_WORKER_ADD_HOLIDAY, ID_YEAR_ADD_HOLIDAY));
		holiday.setCountDays(24);
		holiday.setWorker(new WorkerService(emManager)
				.find(ID_WORKER_ADD_HOLIDAY));
		holiday.setYear(new YearService(emManager).find(ID_YEAR_ADD_HOLIDAY));
		new HolidayService(emManager).save(holiday);

		new HolidayService(emManager)
				.remove(new HolidayService(emManager).find(new HolidayPK(
						ID_WORKER_ADD_HOLIDAY, ID_YEAR_ADD_HOLIDAY)));
	}

	public static void addDellSpentHoliday()
	{
		SpentHoliday spholiday = new SpentHoliday();
		spholiday.setId(new SpentHolidayPK(ID_WORKER_ADD_SPENT_HOLIDAY,
				ID_MONTH_ADD_SPENT_HOLIDAY));
		spholiday.setCountDays(5);
		spholiday.setDescription("тестовая запись");
		spholiday.setWorker(new WorkerService(emManager)
				.find(ID_WORKER_ADD_SPENT_HOLIDAY));
		spholiday.setMonth(new MonthService(emManager)
				.find(ID_MONTH_ADD_SPENT_HOLIDAY));
		new SpentHolidayService(emManager).save(spholiday);

		new SpentHolidayService(emManager).remove(new SpentHolidayService(
				emManager).find(new SpentHolidayPK(ID_WORKER_ADD_SPENT_HOLIDAY,
				ID_MONTH_ADD_SPENT_HOLIDAY)));

	}

	public static void addDellMonth()
	{
		Month month = new Month();
		month.setMonthId(ID_DEL_MONTH);
		month.setWorkingDaysCount(20);
		month.setDescription("тестовая запись месяца");
		month.setNameMonth(new NameMonthService(emManager)
				.find(ID_NAME_MONTH_ADD_MONTH));
		month.setYear(new YearService(emManager).find(ID_YEAR_ADD_MONTH));
		new MonthService(emManager).save(month);

		new MonthService(emManager).delete(ID_DEL_MONTH);
	}

	public static void addDellDaysOfWork()
	{
		DaysOfWork days = new DaysOfWork();
		days.setDaysOfWorkId(ID_DEL_DAYS_OF_WORK);
		days.setAktualWorkedDays(0);
		days.setBonusTime(33.5);
		days.setBonusTimeDescription("test");
		days.setMonth(new MonthService(emManager)
				.find(ID_MONTH_ADD_DAYS_OF_WORK));
		days.setTimestamp(new Date());
		days.setWorker(new WorkerService(emManager)
				.find(ID_WORKER_ADD_DAYS_OF_WORK));
		days.setWorklog(25.3);
		new DaysOfWorkService(emManager).save(days);

		new DaysOfWorkService(emManager).delete(ID_DEL_DAYS_OF_WORK);
	}

	@Before
	public void setUp() throws Exception
	{
		System.out.println("connect");
		connectToDB();
	}

	@After
	public void tearDown() throws Exception
	{
		disconnectFromDb();
		System.out.println("disconnect");
	}

	@Test
	public void testYearTable() throws UnknownUserException
	{
		addDellYear();
	}

	@Test
	public void testNameMonthTable() throws UnknownUserException
	{
		addDellNameMonth();
	}

	@Test
	public void testWorkerTable() throws UnknownUserException
	{
		addDellWorker();
	}

	@Test
	public void testHolidayTable() throws UnknownUserException
	{
		addDellHoliday();
	}

	@Test
	public void testSpentHolidayTable() throws UnknownUserException
	{
		addDellSpentHoliday();
	}

	@Test
	public void testMonthTable() throws UnknownUserException
	{
		addDellMonth();
	}

	@Test
	public void testDaysOfWorkTable() throws UnknownUserException
	{
		addDellDaysOfWork();
	}

}