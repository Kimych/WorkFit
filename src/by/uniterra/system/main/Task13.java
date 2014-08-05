package by.uniterra.system.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.HolidayEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.NameMonthEAO;
import by.uniterra.dai.eao.SpentHolidayEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.eao.YearEAO;
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

	public static void addAllData()
	{
		Year year = new Year();
		year.setYearId(ID_DEL_YEAR);
		year.setNumber(2015);
		year.setDeskription("test description");
		new YearEAO(emManager).save(year);

		NameMonth nameMonth = new NameMonth();
		nameMonth.setNameMonthId(ID_DEL_MONTH_NAME);
		nameMonth.setName("Небритябрь");
		new NameMonthEAO(emManager).save(nameMonth);

		Worker worker = new Worker();
		worker.setWorkerId(ID_DEL_WORKER);
		worker.setFirstName("");
		worker.setSecondName("Второе имя");
		worker.setThirdName("Третье имя");
		new WorkerEAO(emManager).save(worker);

		Holiday holiday = new Holiday();
		holiday.setId(new HolidayPK(ID_WORKER_ADD_HOLIDAY, ID_YEAR_ADD_HOLIDAY));
		holiday.setCountDays(24);
		holiday.setWorker(new WorkerEAO(emManager)
				.find(ID_WORKER_ADD_HOLIDAY));
		holiday.setYear(new YearEAO(emManager).find(ID_YEAR_ADD_HOLIDAY));
		new HolidayEAO(emManager).save(holiday);

		SpentHoliday spholiday = new SpentHoliday();
		spholiday.setId(new SpentHolidayPK(ID_WORKER_ADD_SPENT_HOLIDAY,
				ID_MONTH_ADD_SPENT_HOLIDAY));
		spholiday.setCountDays(5);
		spholiday.setDescription("тестовая запись");
		spholiday.setWorker(new WorkerEAO(emManager)
				.find(ID_WORKER_ADD_SPENT_HOLIDAY));
		spholiday.setMonth(new MonthEAO(emManager)
				.find(ID_MONTH_ADD_SPENT_HOLIDAY));
		new SpentHolidayEAO(emManager).save(spholiday);

		Month month = new Month();
		month.setMonthId(ID_DEL_MONTH);
		month.setWorkingDaysCount(20);
		month.setDescription("тестовая запись месяца");
		month.setNameMonth(new NameMonthEAO(emManager)
				.find(ID_NAME_MONTH_ADD_MONTH));
		month.setYear(new YearEAO(emManager).find(ID_YEAR_ADD_MONTH));
		new MonthEAO(emManager).save(month);

		DaysOfWork days = new DaysOfWork();
		days.setDaysOfWorkId(ID_DEL_DAYS_OF_WORK);
		days.setAktualWorkedDays(0);
		days.setBonusTime(33.5);
		days.setBonusTimeDescription("test");
		days.setMonth(new MonthEAO(emManager)
				.find(ID_MONTH_ADD_DAYS_OF_WORK));
		days.setTimestamp(new Date());
		days.setWorker(new WorkerEAO(emManager)
				.find(ID_WORKER_ADD_DAYS_OF_WORK));
		days.setWorklog(25.3);
		new DaysOfWorkEAO(emManager).save(days);

	}

	public static void printAllData()
	{
		new YearEAO(emManager).printAllItems();
		new NameMonthEAO(emManager).printAllItems();
		new WorkerEAO(emManager).printAllItems();
		new HolidayEAO(emManager).printAllItems();
		new SpentHolidayEAO(emManager).printAllItems();
		new MonthEAO(emManager).printAllItems();
		new DaysOfWorkEAO(emManager).printAllItems();
	}

	public static void dellAllData()
	{
		new YearEAO(emManager).delete(ID_DEL_YEAR);
		new NameMonthEAO(emManager).delete(ID_DEL_MONTH_NAME);
		new WorkerEAO(emManager).delete(ID_DEL_WORKER);
		new HolidayEAO(emManager)
				.remove(new HolidayEAO(emManager).find(new HolidayPK(
						ID_WORKER_ADD_HOLIDAY, ID_YEAR_ADD_HOLIDAY)));
		new SpentHolidayEAO(emManager).remove(new SpentHolidayEAO(
				emManager).find(new SpentHolidayPK(ID_WORKER_ADD_SPENT_HOLIDAY,
				ID_MONTH_ADD_SPENT_HOLIDAY)));
		new MonthEAO(emManager).delete(ID_DEL_MONTH);
		new DaysOfWorkEAO(emManager).delete(ID_DEL_DAYS_OF_WORK);
	}

	public static void addDellYear()
	{
		// create a new entity instance
		Year year = new Year();
		year.setNumber(2015);
		year.setDeskription("test description");
		try
		{
			YearEAO yearEAO = new YearEAO(emManager);
			// save it into DB
			year = yearEAO.save(year);
			// delete it from DB
			yearEAO.delete(year);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void addDellNameMonth()
	{
		NameMonth nameMonth = new NameMonth();
		nameMonth.setName("Небритябрь");
		try
		{
			NameMonthEAO monthEAO = new NameMonthEAO(emManager);
			nameMonth = monthEAO.save(nameMonth);
			monthEAO.delete(nameMonth);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void addDellWorker()
	{
		Worker worker = new Worker();
		worker.setFirstName("Первое имя");
		worker.setSecondName("Второе имя");
		worker.setThirdName("Третье имя");
		try
		{
			WorkerEAO workerEAO = new WorkerEAO(emManager);
			worker = workerEAO.save(worker);
			workerEAO.delete(worker);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void addDellHoliday()
	{
		Holiday holiday = new Holiday();
		holiday.setId(new HolidayPK(ID_WORKER_ADD_HOLIDAY, ID_YEAR_ADD_HOLIDAY));
		holiday.setCountDays(24);
		holiday.setWorker(new WorkerEAO(emManager)
				.find(ID_WORKER_ADD_HOLIDAY));
		holiday.setYear(new YearEAO(emManager).find(ID_YEAR_ADD_HOLIDAY));
		try
		{
			HolidayEAO holidayEAO = new HolidayEAO(emManager);
			holiday = holidayEAO.save(holiday);
			holidayEAO.remove(holiday);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void addDellSpentHoliday()
	{
		SpentHoliday spholiday = new SpentHoliday();
		spholiday.setId(new SpentHolidayPK(ID_WORKER_ADD_SPENT_HOLIDAY,
				ID_MONTH_ADD_SPENT_HOLIDAY));
		spholiday.setCountDays(5);
		spholiday.setDescription("тестовая запись");
		spholiday.setWorker(new WorkerEAO(emManager)
				.find(ID_WORKER_ADD_SPENT_HOLIDAY));
		spholiday.setMonth(new MonthEAO(emManager)
				.find(ID_MONTH_ADD_SPENT_HOLIDAY));
		try
		{
			SpentHolidayEAO spHolidayEAO = new SpentHolidayEAO(
					emManager);
			spholiday = spHolidayEAO.save(spholiday);
			spHolidayEAO.remove(spholiday);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void addDellMonth()
	{
		Month month = new Month();
		month.setWorkingDaysCount(20);
		month.setDescription("тестовая запись месяца");
		month.setNameMonth(new NameMonthEAO(emManager)
				.find(ID_NAME_MONTH_ADD_MONTH));
		month.setYear(new YearEAO(emManager).find(ID_YEAR_ADD_MONTH));
		try
		{
			MonthEAO monthEAO = new MonthEAO(emManager);
			month = monthEAO.save(month);
			monthEAO.delete(month);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void addDellDaysOfWork()
	{
		DaysOfWork days = new DaysOfWork();
		days.setAktualWorkedDays(0);
		days.setBonusTime(33.5);
		days.setBonusTimeDescription("test");
		days.setMonth(new MonthEAO(emManager)
				.find(ID_MONTH_ADD_DAYS_OF_WORK));
		days.setTimestamp(new Date());
		days.setWorker(new WorkerEAO(emManager)
				.find(ID_WORKER_ADD_DAYS_OF_WORK));
		days.setWorklog(25.3);
		try
		{
			DaysOfWorkEAO daysEAO = new DaysOfWorkEAO(emManager);
			days = daysEAO.save(days);
			daysEAO.delete(days);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		connectToDB();

		printAllData();

		addDellYear();
		addDellNameMonth();
		addDellWorker();
		addDellHoliday();
		addDellSpentHoliday();
		addDellMonth();
		addDellDaysOfWork();

		printAllData();

		disconnectFromDb();

	}

}
