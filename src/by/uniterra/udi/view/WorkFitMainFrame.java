package by.uniterra.udi.view;


import javax.swing.JOptionPane;

public class WorkFitMainFrame
{

	public static void main(String[] args)
	{
/*		JFrame frame = new JFrame("WorkFit v.0");
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		frame.add(new YearTablePanel(), new GridBagConstraints(0, 0, 3, 1, 1,
				1, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
				new Insets(1, 1, 1, 1), 0, 0));

		frame.setVisible(true);*/
		
		
		
		JOptionPane.showMessageDialog(null, new YearTablePanel(), "Year Table", JOptionPane.PLAIN_MESSAGE);

	}

}
