package by.uniterra.udi.view;

import javax.swing.JOptionPane;

import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.udi.model.UDIPropSingleton;

public class WorkFitMainFrame
{
    public static void main(String[] args)
    {
        // uncomment this line to use given locale (by default we use "ru")
        // Locale.setDefault(new Locale("en"));

        ServiceBaseEAO.connectToDB();

        JOptionPane.showMessageDialog(null, new YearTablePanel(), UDIPropSingleton.getString(WorkFitMainFrame.class, "YearTableFrame.title"),
                JOptionPane.PLAIN_MESSAGE);

        ServiceBaseEAO.disconnectFromDb();

    }
}
