/**
 * 
 */
package by.uniterra.udi.model;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;

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
    private static final String ROPERTYFILE_PATH = "/config/udi/";
    private static final String FILE_EXTENSION = ".xml";
    private static final String DEF_LOCALE = "en";
    private static final String SZ_NO_RESOURCE = "!!!unknown!!!";
    
    private static Document xml_Doc;
    private static Document xml_DefDoc;

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
	    xml_Doc = loadDoc(Locale.getDefault().getLanguage());
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
    private static Document loadDoc(String strCurLocaleName)
    {
        Document docResult = null;
        // try to find according file
        InputStream isCurrentResourceFile = UDIPropSingleton.class.getResourceAsStream(ROPERTYFILE_PATH + strCurLocaleName + FILE_EXTENSION);
        if (isCurrentResourceFile == null)
        {
            // we didn't find according resource, use default one
            isCurrentResourceFile = UDIPropSingleton.class.getResourceAsStream(ROPERTYFILE_PATH + DEF_LOCALE + File.separator + FILE_EXTENSION);
        }
        // load data from the resource
        try
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    docResult = factory.newDocumentBuilder().parse(isCurrentResourceFile, "UTF-8");
	} catch (Exception e)
	{
	    e.printStackTrace();
	    System.out.println("NO UDI XML-based file found...");
	}
        return docResult;
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
	String szRes = getString(szClass, szKey, getPropRoot());
	if (szRes == SZ_NO_RESOURCE)
	{
	    szRes = getString(szClass, szKey, getDefRoot());
	}
	return szRes;
    }
    
    private static Element getDefRoot()
    {
        return getDefDoc() != null ? getDefDoc().getDocumentElement() : null;
    }

    private static Document getDefDoc()
    {
        if (xml_DefDoc == null)
        {
            xml_DefDoc = loadDoc(DEF_LOCALE);
        }
        return xml_DefDoc;
    }

    synchronized public static String getString(String szClass, String szKey, Element elTargetLocaleRoot)
    {
        String szRes = SZ_NO_RESOURCE;
        try
        {
            NodeList nlstClassesList = elTargetLocaleRoot.getChildNodes();
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
                            //szRes = nodeCurNode.getAttributes().getNamedItem(szLocale).getNodeValue();
                            szRes = nodeCurNode.getTextContent();
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
