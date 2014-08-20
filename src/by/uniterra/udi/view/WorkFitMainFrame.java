package by.uniterra.udi.view;

import java.util.Locale;

import javax.swing.JOptionPane;

import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.udi.model.UDIPropSingleton;

public class WorkFitMainFrame
{
    public static void main(String[] args)
    {
        // uncomment this line to use given locale (by default we use "ru")
        /*Locale.setDefault(new Locale("en"));

        ServiceBaseEAO.connectToDB();
        YearTablePanel panelYear = new YearTablePanel();
        JOptionPane.showMessageDialog(null, panelYear, UDIPropSingleton.getString(WorkFitMainFrame.class, "YearTableFrame.title"), JOptionPane.PLAIN_MESSAGE);*/
    
        // save to DB
        //panelYear.writeValues();

       /* MonthTablePanel panelMonth = new MonthTablePanel();
        JOptionPane.showMessageDialog(null, panelMonth, "Количество рабочих дней", JOptionPane.PLAIN_MESSAGE);
        panelMonth.writeValues();

        ServiceBaseEAO.disconnectFromDb();
        
*/
    }
}
