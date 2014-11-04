/**
 * Filename  : SelectAllFocusAdapter.java
 *
 * ***************************************************************************
 * Copyright (C) 2013-2013 by UniTerra Ltd.
 * K. Chornogo 31, office 5, room 3
 * 220012 Minsk / Republic of Belarus
 * info@uniterra.by
 *                                                      
 * This material is a CONFIDENTIAL, unpublished work of authorship
 * created in 2013 and subsequently updated. It is a TRADE SECRET which
 * is property of UniTerra Ltd.
 *
 * All use, disclosure, and/or reproduction not specifically authorized
 * by UniTerra Ltd in writing is prohibited. All rights reserved.
 ***************************************************************************
 * Project    : ARMParis
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.udi.util;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 * The <code>SelectAllFocusAdapter</code> is used like a tool to select all text in an JTextField when focus gained by the component
 *
 * @author Anton Nedbailo
 * @since Sep 15, 2013
 */
public class SelectAllFocusAdapter extends FocusAdapter
{
    //members 
    private JTextComponent tcOwner;
    /**
     * 
     * Constructor.
     *
     * @param tcOwner - owner of the adapter
     */
    public SelectAllFocusAdapter(JTextComponent tcOwner)
    {
        this.tcOwner = tcOwner;
    }
    
    @Override
    public void focusGained(FocusEvent evt)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //Select all the text in the tcOwner component
                tcOwner.selectAll();
            }
        });
    }

}
