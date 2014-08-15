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
import java.util.ArrayList;
import java.util.List;

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
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.model.YearTableModel;

public class YearTablePanel extends JPanel implements ActionListener
{
    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3705271245863973712L;

    // members
    private YearTableModel ytm;
    private JTable tTable;
    private KeyEventDispatcher keyDispatcher;

    private List<Year> lstChangedRows;
    private List<Year> lstYearsToDelete;

    private static final String ACTION_SAVE_TO_MODEL = "Save to model";
    private static final String ACTION_REFRESH_TABLE = "Refresh";
    private static final String ACTION_DEL_ROW = "Delete row";
    private static final String ACTION_EDIT_ROW = "Edit row";

    public YearTablePanel()
    {
        lstChangedRows = new ArrayList<Year>();
        lstYearsToDelete = new ArrayList<Year>();

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
        lstYearsToDelete.clear();
        lstChangedRows.clear();
        ytm.setTableData(new YearEAO(ServiceBaseEAO.getDefaultEM()).loadAll());
    }

    public void writeValues()
    {
        YearEAO eaoYear = new YearEAO(ServiceBaseEAO.getDefaultEM());
        for (Year year : lstYearsToDelete)
        {
            eaoYear.delete(year);
        }
        for (Year year : lstChangedRows)
        {
            eaoYear.save(year);
        }

    }

    private void jbInit()
    {
        setLayout(new GridBagLayout());
        ytm = new YearTableModel();
        tTable = new JTable(ytm);
        tTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // popup menu del row
        final JPopupMenu popup = new JPopupMenu();
        JMenuItem menuItemDelRow = new JMenuItem(UDIPropSingleton.getString(this, "menuItemDelRow.label"));
        menuItemDelRow.setActionCommand(ACTION_DEL_ROW);
        menuItemDelRow.addActionListener(this);
        popup.add(menuItemDelRow);

        // popup edit row
        JMenuItem menuItemEditRow = new JMenuItem(UDIPropSingleton.getString(this, "menuItemEditRow.label"));
        menuItemEditRow.setActionCommand(ACTION_EDIT_ROW);
        menuItemEditRow.addActionListener(this);
        popup.add(menuItemEditRow);

        // add new row in db
        JButton buttonSaveDB = new JButton(UDIPropSingleton.getString(this, "buttonSaveDB.label"));
        buttonSaveDB.setActionCommand(ACTION_SAVE_TO_MODEL);
        buttonSaveDB.addActionListener(this);
        super.add(buttonSaveDB, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

        JButton buttonRefreshDB = new JButton(UDIPropSingleton.getString(this, "buttonRefreshDB.label"));
        buttonRefreshDB.setActionCommand(ACTION_REFRESH_TABLE);
        buttonRefreshDB.addActionListener(this);
        super.add(buttonRefreshDB, new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

        final JScrollPane yearTableScrollPage = new JScrollPane(tTable);
        yearTableScrollPage.setPreferredSize(new Dimension(400, 400));
        super.add(yearTableScrollPage,
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

            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    editValuesFromModel();
                }
            }

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
            case ACTION_SAVE_TO_MODEL:
                saveValuesToModel();
                break;
            case ACTION_REFRESH_TABLE:
                readValues();
                break;
            case ACTION_DEL_ROW:
                removeValuesFromModel();
                break;
            case ACTION_EDIT_ROW:
                editValuesFromModel();
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

    public void saveValuesToModel()
    {
        YearOptionPanel yop = new YearOptionPanel();
        yop.setModel(new Year());
        if (JOptionPane.showConfirmDialog(tTable, yop, UDIPropSingleton.getString(this, "addYearOptionPanel.title"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            try
            {
                Year year = (Year) yop.getModel();
                ytm.addTableData(year);
                lstChangedRows.add(year);
            }
            catch (NumberFormatException ex)
            {
                showNumberErrorMessage();
            }
        }
        else
        {
            System.out.println("Input Canceled");
        }
    }

    public void removeValuesFromModel()
    {
        int[] arrSelIndexes = tTable.getSelectedRows();

        for (int i = 0; i < arrSelIndexes.length; i++)
        {
            // convert view to model index number
            int iModelIndex = (tTable.convertRowIndexToModel(arrSelIndexes[i]));
            Year yCurYear = (Year) ytm.getRowData(iModelIndex);

            // check if it exists in DB
            if (yCurYear.getYearId() != 0)
            {
                lstYearsToDelete.add(yCurYear);
            }

            // check if we edited the entity
            if (lstChangedRows.contains(yCurYear))
            {
                lstChangedRows.remove(yCurYear);
            }

            // dell data by index value
            ytm.removeTableData(iModelIndex);
        }
    }

    public void editValuesFromModel()
    {
        int[] arrSelIndexes = tTable.getSelectedRows();
        // convert view to model index number
        int iModelIndex = (tTable.convertRowIndexToModel(arrSelIndexes[0]));
        // get data by index value
        Year yearEditedModel = (Year) ytm.getRowData(iModelIndex);
        // check if it's already in changed list
        int iChangedListIndex = lstChangedRows.indexOf(yearEditedModel);

        IModelOwner yop = new YearOptionPanel();
        yop.setModel(yearEditedModel);
        if (JOptionPane.showConfirmDialog(tTable, yop, UDIPropSingleton.getString(this, "editYearOptionPanel.title"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            try
            {
                Year year = (Year) yop.getModel();
                ytm.setTableData(year, iModelIndex);
                // FIXME check if we already edited the same value
                if (iChangedListIndex != -1)
                {
                    lstChangedRows.set(iChangedListIndex, year);
                }
                else
                {
                    lstChangedRows.add(year);
                }
            }
            catch (NumberFormatException ex)
            {
                showNumberErrorMessage();
            }

        }
        else
        {
            System.out.println("Input Canceled");
        }
    }

    public void showNumberErrorMessage()
    {
        JOptionPane.showMessageDialog(null, UDIPropSingleton.getString(this, "errorMessageDialog.lable"),
                UDIPropSingleton.getString(this, "errorMessageDialog.title"), JOptionPane.ERROR_MESSAGE);
    }
}
