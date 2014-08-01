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
import java.util.Map;

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

		final JScrollPane YearTableScrollPage = new JScrollPane(tTable);

		// popup
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItem1 = new JMenuItem("item1");
		menuItem1.getAccessibleContext().setAccessibleDescription("Item1");
		menuItem1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(YearTableScrollPage,
						"item1 cliced!");
			}

		});
		popup.add(menuItem1);

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
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				showPopup(e);
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
				}
			}
		});
	}

}
