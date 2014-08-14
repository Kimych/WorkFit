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

import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.entity.Month;
import by.uniterra.udi.model.MonthTableModel;
import by.uniterra.udi.model.UDIPropSingleton;


public class MonthTablePanel extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 6992479839840204367L;

    // members
    private MonthTableModel mtm;
    private JTable tTable;
    private KeyEventDispatcher keyDispatcher;

    private List<Month> lstChangedRows;
    private List<Month> lstMonthToDelete;

    private static final String ACTION_SAVE_TO_MODEL = "Save to model";
    private static final String ACTION_REFRESH_TABLE = "Refresh";
    private static final String ACTION_DEL_ROW = "Delete row";
    private static final String ACTION_EDIT_ROW = "Edit row";

    public MonthTablePanel()
    {
        lstChangedRows = new ArrayList<Month>();
        lstMonthToDelete = new ArrayList<Month>();
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
        lstMonthToDelete.clear();
        lstChangedRows.clear();
        mtm.setTableData(new MonthEAO(ServiceBaseEAO.getDefaultEM()).loadAll());
    }

    public void writeValues()
    {
        MonthEAO eaoMonth = new MonthEAO(ServiceBaseEAO.getDefaultEM());
        for (Month month : lstMonthToDelete)
        {
            eaoMonth.delete(month);
        }
        for (Month month : lstChangedRows)
        {
            eaoMonth.save(month);
        }

    }

    private void jbInit()
    {
        setLayout(new GridBagLayout());
        mtm = new MonthTableModel();
        tTable = new JTable(mtm);
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
        MonthOptionPanel mop = new MonthOptionPanel();
        mop.setModel(new Month());
        if (JOptionPane.showConfirmDialog(tTable, mop, UDIPropSingleton.getString(this, "addMonthOptionPanel.title"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            try
            {
                Month month = mop.getModel();
                mtm.addTableData(month);
                lstChangedRows.add(month);
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
            Month yCurMonth = (Month) mtm.getRowData(iModelIndex);

            // check if it exists in DB
            if (yCurMonth.getMonthId() != 0)
            {
                lstMonthToDelete.add(yCurMonth);
            }

            // check if we edited the entity
            if (lstChangedRows.contains(yCurMonth))
            {
                lstChangedRows.remove(yCurMonth);
            }

            // dell data by index value
            mtm.removeTableData(iModelIndex);
        }
    }

    public void editValuesFromModel()
    {
        int[] arrSelIndexes = tTable.getSelectedRows();
        // convert view to model index number
        int iModelIndex = (tTable.convertRowIndexToModel(arrSelIndexes[0]));
        // get data by index value
        Month monthEditedModel = (Month) mtm.getRowData(iModelIndex);
        // check if it's already in changed list
        int iChangedListIndex = lstChangedRows.indexOf(monthEditedModel);

        MonthOptionPanel mop = new MonthOptionPanel();
        mop.setModel(monthEditedModel);
        if (JOptionPane.showConfirmDialog(tTable, mop, UDIPropSingleton.getString(this, "editMonthOptionPanel.title"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            try
            {
                Month month = mop.getModel();
                mtm.setTableData(month, iModelIndex);
                // FIXME check if we already edited the same value
                if (iChangedListIndex != -1)
                {
                    lstChangedRows.set(iChangedListIndex, month);
                }
                else
                {
                    lstChangedRows.add(month);
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
