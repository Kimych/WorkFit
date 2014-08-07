package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class YearOptionPanel extends JPanel
{

	/** TODO document <code>serialVersionUID</code> */
	private static final long serialVersionUID = -5254096192556277102L;

	private JTextField tfNuber;
	private JTextArea tfDeskription;
	private JLabel jlNumber;
	private JLabel jlDesk;

	public YearOptionPanel()
	{
		jbInit();
	}

	public void jbInit()
	{
		new GridBagLayout();
		jlNumber = new JLabel("Number:");
		jlDesk = new JLabel("Desk:");
		tfNuber = new JTextField(10);
		tfDeskription = new JTextArea(10, 20);
		add(jlNumber, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
		add(tfNuber, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(jlDesk, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
		add(tfDeskription, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));

	}

	public String getYearNumber()
	{
		return tfNuber.getText();
	}

	public String getYearDeskription()
	{
		return tfDeskription.getText();
	}

}