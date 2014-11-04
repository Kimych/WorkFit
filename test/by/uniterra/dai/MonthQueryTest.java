package by.uniterra.dai;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.entity.Month;
import by.uniterra.system.model.SystemModel;

public class MonthQueryTest
{
    private static final int TEST_MONTH_NUMBER = 10;
    private static final int TEST_YEAR_NUMBER = 2014;
    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
      SystemModel.initJPA();  
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
      SystemModel.disposeJPA();
    }

    @Test
    public void test()
    {
        MonthEAO eaoMonth = new MonthEAO(SystemModel.getDefaultEM());
        
        Month result = eaoMonth.getMonthByMonthNumberAndYearNumber(TEST_MONTH_NUMBER, TEST_YEAR_NUMBER);
        
        System.out.println(result.toString());
        
        assertTrue(result != null);
    }

}
