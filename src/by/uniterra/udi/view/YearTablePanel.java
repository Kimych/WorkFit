package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import by.uniterra.udi.model.YearTableModel;

public class YearTablePanel extends JPanel
{
	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -3705271245863973712L;
	
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
        
        String []str = new String[3];
        str[0] = "1";
        str[1] = "test record 1";
        str[2] = "test record 2";
        ytm.addDate(str);
        
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

}
