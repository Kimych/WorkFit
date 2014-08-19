package by.uniterra.udi.view;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class CompCellRendered extends AbstractCellEditor implements TableCellRenderer, TableCellEditor
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 545375560661793984L;
    
    private final JButton btnButton;
    
    public CompCellRendered(ActionListener alListener, String strTitle, String strActionCommand)
    {
        btnButton = new JButton(strTitle);
        btnButton.setActionCommand(strActionCommand);
        btnButton.addActionListener(alListener);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        return btnButton;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        return btnButton;
    }

    @Override
    public boolean isCellEditable(EventObject arg0)
    {
        return true;
    }

    @Override
    public Object getCellEditorValue()
    {
        return null;
    }

}
