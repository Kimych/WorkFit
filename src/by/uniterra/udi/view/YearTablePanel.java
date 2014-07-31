package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.eao.YearService;
import by.uniterra.udi.model.YearTableModel;

public class YearTablePanel extends JPanel
{
	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -3705271245863973712L;
	
	
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
	
	
	
	// members
	private YearTableModel ytm;

	public YearTablePanel()
	{
	    // create UI
	    jbInit();
	    
	    // bring data from DB to view
	    readValues();
	    
	    // save data from view to Db
	    writeValues();

	}

    private void writeValues()
    {
        // TODO Auto-generated method stub
        
    }

    private void readValues()
    {
    	connectToDB();
    	YearTableModel ytm = new YearTableModel();
    	
    	ytm.addDate(new YearService(emManager).loadAll());
    	
    	
        
        /*String []str = new String[3];
        str[0] = "1";
        str[1] = "test record 1";
        str[2] = "test record 2";
        ytm.addDate(str);*/
        
    }

    private void jbInit()
    {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        
        ytm = new YearTableModel();

        JScrollPane YearTableScrollPage = new JScrollPane(new JTable(ytm));
        YearTableScrollPage.setPreferredSize(new Dimension(400, 400));
        super.add(YearTableScrollPage,
                  new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
    }
    
    public static void main(String[] args)
	{
    	
    	
	}
    
    
}
