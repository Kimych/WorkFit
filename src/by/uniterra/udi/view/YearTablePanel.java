package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.eao.YearService;
import by.uniterra.udi.model.YearTableModel;

public class YearTablePanel extends JPanel
{
	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -3705271245863973712L;

	// members
	private YearTableModel ytm;

	private JTable tTable;

	private KeyEventDispatcher keyDispatcher;

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

	public YearTablePanel()
	{
		// create UI
		jbInit();

		// bring data from DB to view
		readValues();

		// save data from view to Db
		writeValues();

		// unregister key dispatcher on shutdown action
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				if (keyDispatcher != null)
				{
					KeyboardFocusManager.getCurrentKeyboardFocusManager()
							.removeKeyEventDispatcher(keyDispatcher);
				}
			}
		});

	}

	private void writeValues()
	{
		// TODO Auto-generated method stub

	}

	private void readValues()
	{
		connectToDB();

		ytm.addData(new YearService(emManager).loadAll());

		disconnectFromDb();

	}

	private void jbInit()
	{
		setBackground(Color.BLACK);
		setLayout(new GridBagLayout());

		ytm = new YearTableModel();
		tTable = new JTable(ytm);

		JScrollPane YearTableScrollPage = new JScrollPane(tTable);
		/*JButton buttonEdit = new JButton("Изменить");
		buttonEdit.addActionListener(new ButtonEditActionListener());*/

		YearTableScrollPage.setPreferredSize(new Dimension(400, 400));
		super.add(YearTableScrollPage, new GridBagConstraints(2, 1, 1, 1, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
						1, 1, 1, 1), 0, 0));

		/*super.add(buttonEdit, new GridBagConstraints(2, 2, 1, 1, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
						1, 1, 1, 1), 0, 0));*/
		// Register keyboard
		keyDispatcher = new KeyEventDispatcher()
		{
			@Override
			public boolean dispatchKeyEvent(KeyEvent e)
			{
				// check key ID (reakt only on KEY_RELEASED event) and code
				// (react only on F5)
				boolean bResult = e.getID() == KeyEvent.KEY_RELEASED
						&& e.getKeyCode() == KeyEvent.VK_F5;
				// check result
				if (bResult)
				{
					// do table data refresh
					readValues();
				}
				return bResult;
			}
		};
		// register new KeyEventDispatcher
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(keyDispatcher);
	}

/*	public class ButtonEditActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			//

		}

	}*/
}
