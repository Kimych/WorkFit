package by.uniterra.dai;

//import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.omg.CORBA.UnknownUserException;

import by.uniterra.system.main.Task13;

public class AllEntitiesTest
{
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception
	{
		System.out.println("connect");
		Task13.connectToDB();
	}

	@After
	public void tearDown() throws Exception
	{
		Task13.disconnectFromDb();
		System.out.println("disconnect");
	}

	@Test
	public void testYearTable() throws UnknownUserException
	{
		Task13.addDellYear();
	}

	@Test
	public void testNameMonthTable() throws UnknownUserException
	{
		Task13.addDellNameMonth();
	}

	@Test
	public void testWorkerTable() throws UnknownUserException
	{
		Task13.addDellWorker();
	}

	@Test
	public void testHolidayTable() throws UnknownUserException
	{
		Task13.addDellHoliday();
	}

	@Test
	public void testSpentHolidayTable() throws UnknownUserException
	{
		Task13.addDellSpentHoliday();
	}

	@Test
	public void testMonthTable() throws UnknownUserException
	{
		Task13.addDellMonth();
	}

	@Test
	public void testDaysOfWorkTable() throws UnknownUserException
	{
		Task13.addDellDaysOfWork();
	}

}
