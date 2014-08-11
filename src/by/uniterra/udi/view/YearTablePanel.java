package by.uniterra.udi.view;

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

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.dai.entity.Year;
import by.uniterra.udi.model.YearTableModel;

public class YearTablePanel extends JPanel implements ActionListener
{
	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -3705271245863973712L;

	// members
	private YearTableModel ytm;
	private JTable tTable;
	private KeyEventDispatcher keyDispatcher;

	private static final String ACTION_SAVE_DB_YEAR = "New Records";
	private static final String ACTION_REFRESH_YEAR_TABLE = "Refresh";
	private static final String ACTION_DEL_ROW = "Delete row";

	public YearTablePanel()
	{
		// create UI
		jbInit();

		// bring data from DB to view
		readValues();

		// unregister key dispatcher on shutdown action
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				if (keyDispatcher != null)
				{
					KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyDispatcher);
				}
			}
		});

	}

	private void readValues()
	{
		ServiceBaseEAO.connectToDB();

		ytm.setTableData(new YearEAO(ServiceBaseEAO.connectToDB()).loadAll());

		ServiceBaseEAO.disconnectFromDb();

	}

	private void writeValues()
	{
		YearOptionPanel yop = new YearOptionPanel();
		int res = JOptionPane.showConfirmDialog(tTable, yop, "Enter data", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (res == JOptionPane.OK_OPTION)
		{
			Year year = new Year();
			try
			{
				year.setNumber(Integer.valueOf(yop.getYearNumber()));
				year.setDeskription(yop.getYearDeskription());
				new YearEAO(ServiceBaseEAO.connectToDB()).save(year);
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Неверный формат строки.", "Ошибка!", JOptionPane.ERROR_MESSAGE);
			}
			readValues();
			ServiceBaseEAO.disconnectFromDb();

		}
		else
		{
			System.out.println("Input Canceled");
		}
	}

	private void deleteValues()
	{
		int[] arrSelIndexes = tTable.getSelectedRows();
		for (int i = 0; i < arrSelIndexes.length; i++)
		{
			// convert view to model index number
			int iModelIndex = (tTable.convertRowIndexToModel(arrSelIndexes[i]));
			// get data by index value
			Year idCurYear = (Year) ytm.getRowData(iModelIndex);
			// check for existing model
			if (idCurYear.getYearId() != 0)
			{
				// setDocsToRemove.add(idCurDoc);
				new YearEAO(ServiceBaseEAO.connectToDB()).delete(idCurYear.getYearId());
			}

		}
		// ytm.removeRowData(tTable.getSelectedRow());
		readValues();
		ServiceBaseEAO.disconnectFromDb();

	}

	private void jbInit()
	{
		setLayout(new GridBagLayout());
		ytm = new YearTableModel();
		tTable = new JTable(ytm);

		final JScrollPane YearTableScrollPage = new JScrollPane(tTable);

		// popup menu
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItemDelRow = new JMenuItem("Delete Row");
		menuItemDelRow.setActionCommand(ACTION_DEL_ROW);
		menuItemDelRow.addActionListener(this);
		popup.add(menuItemDelRow);

		// add new row in db
		JButton buttonSaveDB = new JButton("Новая запись");
		buttonSaveDB.setActionCommand(ACTION_SAVE_DB_YEAR);
		buttonSaveDB.addActionListener(this);
		super.add(buttonSaveDB, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		JButton buttonRefreshDB = new JButton("Обновить");
		buttonRefreshDB.setActionCommand(ACTION_REFRESH_YEAR_TABLE);
		buttonRefreshDB.addActionListener(this);
		super.add(buttonRefreshDB, new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		YearTableScrollPage.setPreferredSize(new Dimension(400, 400));
		super.add(YearTableScrollPage,
				new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		// Register keyboard
		keyDispatcher = new KeyEventDispatcher()
		{
			@Override
			public boolean dispatchKeyEvent(KeyEvent e)
			{
				// check key ID (reakt only on KEY_RELEASED event) and code
				// (react only on F5)
				boolean bResult = e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_F5;
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
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyDispatcher);

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

	@Override
	public void actionPerformed(ActionEvent arg0)
	{

		try
		{
			switch (arg0.getActionCommand())
			{
			case ACTION_SAVE_DB_YEAR:
				writeValues();
				break;
			case ACTION_REFRESH_YEAR_TABLE:
				readValues();
				break;
			case ACTION_DEL_ROW:
				deleteValues();
				break;
			default:
				break;
			}
		}
		catch (Exception e)
		{
			System.out.println("actionPerformed expressions");
		}

	}
}
