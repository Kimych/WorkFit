package by.uniterra.dai;

import static org.junit.Assert.fail;

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
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.NameMonth;
import by.uniterra.dai.entity.SpentHoliday;
import by.uniterra.dai.entity.Worker;
import by.uniterra.dai.entity.Year;

public class AllEntitiesTest
{
	private static final String PERSISTENCE_UNIT_NAME = "WorkFit";
	public ExpectedException thrown = ExpectedException.none();
	private static EntityManagerFactory emfFactory;
	private static EntityManager emManager;

	public static int ID_WORKER_ADD_HOLIDAY = 2;
	public static int ID_YEAR_ADD_HOLIDAY = 1;
	public static int ID_WORKER_ADD_SPENT_HOLIDAY = 2;
	public static int ID_MONTH_ADD_SPENT_HOLIDAY = 1;
	public static int ID_NAME_MONTH_ADD_MONTH = 8;
	public static int ID_YEAR_ADD_MONTH = 1;
	public static int ID_MONTH_ADD_DAYS_OF_WORK = 4;
	public static int ID_WORKER_ADD_DAYS_OF_WORK = 1;

	@Before
	public void setUp() throws Exception
	{
		System.out.println("connect");
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

	@After
	public void tearDown() throws Exception
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
		System.out.println("disconnect");
	}

	@Test
	public void testYearTable() throws UnknownUserException
	{
		// create a new entity instance
		Year year = new Year();
		year.setNumber(2015);
		year.setDeskription("test description");
		try
		{
			YearService yearEAO = new YearService(emManager);
			// save it into DB
			year = yearEAO.save(year);
			// delete it from DB
			yearEAO.delete(year);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testNameMonthTable() throws UnknownUserException
	{
		NameMonth nameMonth = new NameMonth();
		nameMonth.setName("Небритябрь");
		try
		{
			NameMonthService monthEAO = new NameMonthService(emManager);
			nameMonth = monthEAO.save(nameMonth);
			monthEAO.delete(nameMonth);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testWorkerTable() throws UnknownUserException
	{
		Worker worker = new Worker();
		worker.setFirstName("Первое имя");
		worker.setSecondName("Второе имя");
		worker.setThirdName("Третье имя");
		try
		{
			WorkerService workerEAO = new WorkerService(emManager);
			worker = workerEAO.save(worker);
			workerEAO.delete(worker);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testHolidayTable() throws UnknownUserException
	{
		// retrieve existing values
		Worker wExistingWorker = new WorkerService(emManager)
				.find(ID_WORKER_ADD_HOLIDAY);
		Year yExistingYear = new YearService(emManager)
				.find(ID_YEAR_ADD_HOLIDAY);
		// create a new entry
		Holiday holiday = new Holiday(wExistingWorker, yExistingYear);
		holiday.setCountDays(24);
		try
		{
			// save new entry
			HolidayService holidayEAO = new HolidayService(emManager);
			holiday = holidayEAO.save(holiday);
			// delete saved entry
			holidayEAO.remove(holiday);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testSpentHolidayTable() throws UnknownUserException
	{
		Worker wExistingWorker2 = new WorkerService(emManager)
				.find(ID_WORKER_ADD_SPENT_HOLIDAY);
		Month mExistingMonth = new MonthService(emManager)
				.find(ID_MONTH_ADD_SPENT_HOLIDAY);
		SpentHoliday spholiday = new SpentHoliday(wExistingWorker2,
				mExistingMonth);
		spholiday.setCountDays(5);
		spholiday.setDescription("тестовая запись");
		spholiday.setWorker(new WorkerService(emManager)
				.find(ID_WORKER_ADD_SPENT_HOLIDAY));
		spholiday.setMonth(new MonthService(emManager)
				.find(ID_MONTH_ADD_SPENT_HOLIDAY));
		try
		{
			SpentHolidayService spHolidayEAO = new SpentHolidayService(
					emManager);
			spholiday = spHolidayEAO.save(spholiday);
			spHolidayEAO.remove(spholiday);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testMonthTable() throws UnknownUserException
	{
		Month month = new Month();
		month.setWorkingDaysCount(20);
		month.setDescription("тестовая запись месяца");
		month.setNameMonth(new NameMonthService(emManager)
				.find(ID_NAME_MONTH_ADD_MONTH));
		month.setYear(new YearService(emManager).find(ID_YEAR_ADD_MONTH));
		try
		{
			MonthService monthEAO = new MonthService(emManager);
			month = monthEAO.save(month);
			monthEAO.delete(month);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testDaysOfWorkTable() throws UnknownUserException
	{
		DaysOfWork days = new DaysOfWork();
		days.setAktualWorkedDays(0);
		days.setBonusTime(33.5);
		days.setBonusTimeDescription("test");
		days.setMonth(new MonthService(emManager)
				.find(ID_MONTH_ADD_DAYS_OF_WORK));
		days.setTimestamp(new Date());
		days.setWorker(new WorkerService(emManager)
				.find(ID_WORKER_ADD_DAYS_OF_WORK));
		days.setWorklog(25.3);
		try
		{
			DaysOfWorkService daysEAO = new DaysOfWorkService(emManager);
			days = daysEAO.save(days);
			daysEAO.delete(days);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

}
