package by.uniterra.udi.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;

public class CompCellRendered extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener
{
    private static final long serialVersionUID = 545375560661793984L;
    // members
    private final JButton btnButton;
    private Object objEditableValue;

    private IModelOwner moModelOwner;
    
    public CompCellRendered(String strTitle, IModelOwner moModelOwner)
    {
        btnButton = new JButton(strTitle);
        btnButton.addActionListener(this);
        this.moModelOwner = moModelOwner;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
	objEditableValue = value;
        return btnButton;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
	objEditableValue = value;
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
        return objEditableValue;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	if (e.getSource().equals(btnButton))
	{
	    moModelOwner.setModel(objEditableValue);
	    if (JOptionPane.showConfirmDialog(null, moModelOwner, UDIPropSingleton.getString(this, "editMonthOptionPanel.title"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
	    {
		objEditableValue = moModelOwner.getModel();
	    } else
	    {
		System.out.println("Input Canceled");
	    }
	}

    }

}
