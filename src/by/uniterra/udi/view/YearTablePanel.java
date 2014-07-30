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

	public YearTablePanel()
	{
		super.setBackground(Color.BLACK);
		super.setLayout(new GridBagLayout());
		YearTableModel ytm = new YearTableModel();
		JScrollPane YearTableScrollPage = new JScrollPane(new JTable(ytm));
		YearTableScrollPage.setPreferredSize(new Dimension(400, 400));
		super.add(YearTableScrollPage, new GridBagConstraints(2, 1, 1, 1, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
						1, 1, 1, 1), 0, 0));

	}

}
