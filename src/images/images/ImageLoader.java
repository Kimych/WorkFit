/**
 * Filename  : ImageLoader.java
 *
 * ***************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ***************************************************************************
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package images;

import java.net.URL;

import javax.swing.ImageIcon;

import by.uniterra.system.util.Log;

/**
 * The <code>ImageLoader</code> is used to load images from current directory
 *
 * @author Anton Nedbailo
 * @since Sep 21, 2013
 */
public class ImageLoader
{
    /**
     * Returns an ImageIcon, or null if the path was invalid
     * 
     * @param path - path for the image
     * @param description - image description
     * @return - ImageIcon, or null if the path was invalid
     *
     * @author Anton Nedbailo
     * @date Sep 21, 2013
     */
    public static ImageIcon createImageIcon(String path)
    {
        ImageIcon icoResult = null;
        URL imgURL = ImageLoader.class.getResource(path);
        if (imgURL != null)
        {
            icoResult = new ImageIcon(imgURL);
        } else
        {
            Log.error(ImageLoader.class, "Couldn't find file: " + path);
        }
        return icoResult;
    }
    
    /**
     * Returns an ImageIcon, or null if the path was invalid
     *  
     * @param path - path to the image
     * @param iWidth - new width
     * @param iHeight - new height
     * @return  scaled ImageIcon, or null if the path was invalid
     *
     * @author Anton Nedbailo
     * @date Sep 21, 2013
     */
    public static ImageIcon createImageIcon(String path, int iWidth, int iHeight)
    {
        ImageIcon icoResult = createImageIcon(path);
        try
        {
            icoResult = new ImageIcon(  icoResult.getImage().getScaledInstance( iWidth, iHeight,  java.awt.Image.SCALE_SMOOTH ) );
        } catch (Exception e)
        {
            Log.error(ImageLoader.class, e, "createImageIcon error ");
        }
        return icoResult;
    }
}
