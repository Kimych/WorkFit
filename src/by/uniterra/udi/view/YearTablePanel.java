package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.eao.YearService;
import by.uniterra.dai.entity.Year;
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
	private static final String CUSTOMER_PROPERTIES = "custom.properties";
	private static final String PROPERTIES_FILE_EXTENSION = ".properties";

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
		// try to override hardcoded settigs by custom properties
		overrideSettingsFromResource(mapCustomProp, CUSTOMER_PROPERTIES);
		// a new connection (factory) to target DB
		emfFactory = Persistence.createEntityManagerFactory(
				PERSISTENCE_UNIT_NAME, mapCustomProp);
		emManager = emfFactory.createEntityManager();
	}

	/**
	 * Override some DB settings by data from given resource file
	 * 
	 * @param mapCustomProp
	 *            - properties map
	 * @param strResourceName
	 *            - resource file name
	 */
	private static void overrideSettingsFromResource(
			Map<String, String> mapCustomProp, String strResourceName)
	{
		// check if according property file exists
		if (YearTablePanel.class.getClassLoader().getResource(strResourceName) != null)
		{
			try
			{
				// load properties (we should ignore ".properties" extension for
				// the resource file name)
				ResourceBundle res = ResourceBundle.getBundle(strResourceName
						.replace(PROPERTIES_FILE_EXTENSION, ""));
				mapCustomProp.put(PersistenceUnitProperties.JDBC_URL,
						res.getString("db.url"));
				mapCustomProp.put(PersistenceUnitProperties.JDBC_USER,
						res.getString("db.user"));
				mapCustomProp.put(PersistenceUnitProperties.JDBC_PASSWORD,
						res.getString("db.pwd"));
				mapCustomProp.put(PersistenceUnitProperties.JDBC_DRIVER,
						res.getString("db.driver"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
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

		final JScrollPane YearTableScrollPage = new JScrollPane(tTable);

		// popup
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItemAddNewRow = new JMenuItem("Add new Row");
		menuItemAddNewRow.getAccessibleContext().setAccessibleDescription(
				"Item1");
		JMenuItem menuItemDelRow = new JMenuItem("Delete Row");
		menuItemDelRow.getAccessibleContext().setAccessibleDescription("Item2");

		menuItemAddNewRow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				List<Year> lst = ytm.setData();
				lst.add(new Year());
				ytm.addData(lst);
			}

		});

		menuItemDelRow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				List<Year> lst = ytm.setData();
				lst.remove(tTable.getSelectedRow());
				ytm.addData(lst);
			}

		});
		popup.add(menuItemAddNewRow);
		popup.add(menuItemDelRow);

		YearTableScrollPage.setPreferredSize(new Dimension(400, 400));
		super.add(YearTableScrollPage, new GridBagConstraints(2, 1, 1, 1, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
						1, 1, 1, 1), 0, 0));

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

		tTable.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				showPopup(e);
				System.out.println("mousePressed");
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				showPopup(e);
				System.out.println("mouseReleased");
			}

			private void showPopup(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					// popup.show(e.getComponent(), e.getX(), e.getY());
					// get the coordinates of the mouse click
					Point p = e.getPoint();
					// get source
					JTable jtTable = (JTable) e.getSource();
					// get the row index that contains that coordinate
					int rowNumber = jtTable.rowAtPoint(p);
					// Get the ListSelectionModel of the JTable
					ListSelectionModel model = jtTable.getSelectionModel();
					// set the selected interval of rows. Using the "rowNumber"
					// variable for the beginning and end selects only that one
					// row.
					model.setSelectionInterval(rowNumber, rowNumber);
					// show popup menu
					popup.show(jtTable, e.getX(), e.getY());
					System.out.println("Popup");
				}
			}
		});
	}

}
