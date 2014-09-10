/**
 * Filename  : RequestFocusListener.java
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

package by.uniterra.udi.model;

import javax.swing.JComponent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * The <code>RequestFocusListener</code> is a convenience class to request focus on a component.
 *
 * @author Anton Nedbailo
 * @since Sep 11, 2013
 */
public class RequestFocusListener implements AncestorListener
{
    private boolean removeListener;

    /*
     * Convenience constructor. The listener is only used once and then it is
     * removed from the component.
     */
    public RequestFocusListener()
    {
	this(true);
    }

    /*
     * Constructor that controls whether this listen can be used once or
     * multiple times.
     * 
     * @param removeListener when true this listener is only invoked once
     * otherwise it can be invoked multiple times.
     */
    public RequestFocusListener(boolean removeListener)
    {
	this.removeListener = removeListener;
    }

    @Override
    public void ancestorAdded(AncestorEvent e)
    {
	JComponent component = e.getComponent();
	component.requestFocusInWindow();

	if (removeListener)
	    component.removeAncestorListener(this);
    }

    @Override
    public void ancestorMoved(AncestorEvent e)
    {
    }

    @Override
    public void ancestorRemoved(AncestorEvent e)
    {
    }
}
