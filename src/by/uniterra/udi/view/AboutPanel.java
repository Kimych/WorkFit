package by.uniterra.udi.view;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;





import by.uniterra.system.model.SystemModel;

/**
 * The <code>AboutPanel</code> is used to show copyright information
 *
 * @author Anton Nedbailo
 * @since 26 ���. 2013 �.
 */
public class AboutPanel extends JXPanel
{
    private static final long serialVersionUID = -4846352000519417092L;

    public AboutPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }
    
    /**
     * Create UI
     * 
     *
     * @author Anton Nedbailo
     * @date 26 ���. 2013 �.
     */
    protected void jbInit()
    {
        JXLabel lblCopyright = new JXLabel("<html> <b>WorkFit</b>, ver. " + SystemModel.getString("sw.version", "unknown") 
                + "<br>Time tracking system<br>" +
                "&copy UniTerra 2014<br> " +
                "<a href='http://www.uniterra.by'>http://www.uniterra.by</a>" +
                "</html>");
        //lblCopyright.setIcon(ImageLoader.createImageIcon("uniterra360x120.png", 100, 30));
        int iXpos = 0;
        int iYpos = 0;
        add(lblCopyright, new GridBagConstraints(iXpos++, iYpos, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }
}
