package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel
{
    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 7067901994049701935L;

    
    private JTextField tfLogin;
    private JPasswordField pfPassword;
    private static KeyEventDispatcher keyDispatcher;

    private String userLogin;
    private String userPassword;

    //************************************
    public static void main(String[] args)
    {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new LoginPanel());
        frame.setVisible(true);
        
        //jbUnit();
    }

    public LoginPanel()
    {
        super(new GridBagLayout());
        jbUnit();

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

    private static void jbUnit()
    {
        final JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());

        JLabel jlLogin = new JLabel("Login:");
        JLabel jlPassword = new JLabel("Password:");

        JButton btnLogin = new JButton("Login");

        JTextField tfLOgin = new JTextField(10);
        JPasswordField pfPassword = new JPasswordField(10);

        loginPanel.add(jlLogin, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        loginPanel.add(jlPassword, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        loginPanel.add(tfLOgin, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        loginPanel.add(pfPassword, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        loginPanel.add(btnLogin, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

        btnLogin.addActionListener(new LoginButtonActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                JOptionPane.showMessageDialog(loginPanel, "Test Message");

            }
        });

        keyDispatcher = new KeyEventDispatcher()
        {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e)
            {
                // check key ID (reakt only on KEY_RELEASED event) and code
                // (react only on VK_ENTER)
                boolean bResult = e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER;
                // check result
                if (bResult)
                {
                    
                    JOptionPane.showMessageDialog(loginPanel, "Hot KEY");

                }
                return bResult;
            }
        };
        // register new KeyEventDispatcher
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyDispatcher);

    }

    public static class LoginButtonActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("pressed");
        }
    }

    public String getLogin(String login)
    {
        int loginLength = login.length();
        String retLogin = null;

        if (loginLength == 0)
        {
           // JOptionPane.showMessageDialog(frame, "enter login!");
        }
        else
        {
            if (loginLength <= 30)
            {
                retLogin = login;
            }
            else
            {
               // JOptionPane.showMessageDialog(frame, "max 30!");
            }
        }
        return retLogin;
    }
    
    public String getPassword(String pasw)
    {
        int paswLength = pasw.length();
        String retPasw = null;

        if (paswLength == 0)
        {
           //JOptionPane.showMessageDialog(, "enter login!");
        }
        else
        {
            if (paswLength <= 30)
            {
                retPasw = pasw;
            }
            else
            {
                //JOptionPane.showMessageDialog(frame, "max 30!");
            }
        }
        return retPasw;
    }

}
