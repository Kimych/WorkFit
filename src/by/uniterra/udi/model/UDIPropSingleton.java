/**
 * 
 */
package by.uniterra.udi.model;

import java.io.File;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author vnedbailo
 *
 */
public class UDIPropSingleton
{
    private static final String SZ_PROPERTYFILE_PATH = "/config/udi/";
    private static final String SZ_DEFAULT_LOCALE = "/config/udi/en.xml";
    private static final String SZ_NO_RESOURCE = "";
    private static final String DEF_LOCALE = "ru";

    
    private static Document xml_Doc = null;

    /**
     * Get current localization document
     * @return - w3c Document
     *
     * @author Anton Niadbaila
     * @date Aug 11, 2014
     */
    private static Document getDoc()
    {
	if (xml_Doc == null)
	{
	    loadDoc();
	}
	return xml_Doc;
    }

    /**
     * Load localization data from XML file
     * 
     *
     * @author Anton Niadbaila
     * @date Aug 11, 2014
     */
    private static void loadDoc()
    {
        String CURENT_LOCALE = Locale.getDefault().getLanguage();
        boolean  FILE_LOCATION;
        System.out.println(SZ_PROPERTYFILE_PATH + CURENT_LOCALE + ".xml");
        File file = new File(SZ_PROPERTYFILE_PATH + CURENT_LOCALE + ".xml");
        FILE_LOCATION = (file.exists()&&file.isFile());
        

        if(FILE_LOCATION)
        {
            System.out.println("OK");
        } else
        {
            System.out.println("NOT ОК");
        }
        
	try
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    

	    if((CURENT_LOCALE == "en")||(CURENT_LOCALE == null)||(!FILE_LOCATION))
	    {
	        xml_Doc = builder.parse(UDIPropSingleton.class.getResourceAsStream(SZ_DEFAULT_LOCALE), "UTF-8");
	        System.out.println("EN DEFALT LOCATION!");
	    } else 
	    {
	        xml_Doc = builder.parse(UDIPropSingleton.class.getResourceAsStream(SZ_PROPERTYFILE_PATH + CURENT_LOCALE + ".xml"), "UTF-8");
	        System.out.println("RU CUSTOM LOCATION!");
	    }
	} catch (Exception e)
	{
	    e.printStackTrace();
	    System.out.println("NO UDI XML-based file found...");
	    xml_Doc = null;
	}
    }

    /**
     * Get Document root element
     * @return Element which represents root element
     *
     * @author Anton Niadbaila
     * @date Aug 11, 2014
     */
    synchronized private static Element getPropRoot()
    {
	return getDoc() != null ? getDoc().getDocumentElement() : null;
    }

    /**
     * Get localization string value
     * 
     * @param szClass - class to search for
     * @param szKey - key to search for
     * @return - founded resource value for current locale
     *
     * @author Anton Niadbaila
     * @date Aug 11, 2014
     */
    synchronized public static String getString(String szClass, String szKey)
    {
	return getString(szClass, szKey, Locale.getDefault().getLanguage());
    }
    
    /**
     * Get localization string value
     *   
     * @param mObj - object to search for
     * @param szKey - key to search for
     * @param szLocale -locale to search for
     * @return founded resource value
     *
     * @author Anton Niadbaila
     * @date Aug 11, 2014
     */
    synchronized public static String getString(Object mObj, String szKey, String szLocale)
    {
	return getString(fetchClassName(mObj), szKey, szLocale);
    }
    
    /**
     * Get localization string value
     * 
     * @param mObj - object to search for
     * @param szKey - key to search for
     * @return founded resource value
     *
     * @author Anton Niadbaila
     * @date Aug 11, 2014
     */
    synchronized public static String getString(Object mObj, String szKey)
    {
	String szRetStr = getString(fetchClassName(mObj), szKey, Locale.getDefault().getLanguage());
	szRetStr = (szRetStr.trim().isEmpty()) ? getString(fetchClassName(mObj), szKey, DEF_LOCALE) : szRetStr;
	return szRetStr;
    }

    /**
     * Get localization string value
     * 
     * @param szClass - class to search for
     * @param szKey - key to search for
     * @param szLocale -locale to search for
     * @return founded resource value
     *
     * @author Anton Niadbaila
     * @date Aug 11, 2014
     */
    synchronized public static String getString(String szClass, String szKey, String szLocale)
    {
	String szRes = SZ_NO_RESOURCE;
	try
	{
	    NodeList nlstClassesList = getPropRoot().getChildNodes();
	    for (int i = 0; i < nlstClassesList.getLength(); i++)
	    {
		Node nodeCurNode = nlstClassesList.item(i);
		if (nodeCurNode.getNodeType() == Node.ELEMENT_NODE && nodeCurNode.getLocalName().equals(szClass))
		{
		    NodeList nlstItemsList = nodeCurNode.getChildNodes();
		    for (int j = 0; j < nlstItemsList.getLength(); j++)
		    {
			nodeCurNode = nlstItemsList.item(j);
			if (nodeCurNode.getNodeType() == Node.ELEMENT_NODE && nodeCurNode.getLocalName().equals(szKey))
			{
			    szRes = nodeCurNode.getAttributes().getNamedItem(szLocale).getNodeValue();
			    break;
			}
		    }
		    break;
		}
	    }

	} catch (Exception e)
	{
	    e.printStackTrace();
	}
	return szRes;
    }

    /**
     * Get class name from object
     * @param mObj - object to recognize class name for
     * @return - recognized class name
     *
     * @author Anton Niadbaila
     * @date Aug 11, 2014
     */
    private static String fetchClassName(Object mObj)
    {
	String szName = (mObj instanceof Class<?>) ? ((Class<?>) mObj).getName() : mObj.getClass().getName();

	return szName.substring(0, szName.indexOf("$") == -1 ? szName.length() : szName.indexOf("$"));
    }
}
