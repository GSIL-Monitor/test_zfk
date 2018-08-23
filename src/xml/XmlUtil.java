package xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtil
{
    /*
     * 初始化一个空Document对象返回。
     * 
     * @return a Document
     */
    public static Document newXMLDocument()
    {
        try
        {
            return newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*
     * 初始化一个DocumentBuilder，提供一个解析器
     * 
     * @return a DocumentBuilder
     * 
     * @throws ParserConfigurationException
     */
    private static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException
    {
        return newDocumentBuilderFactory().newDocumentBuilder();
    }

    /*
     * 初始化一个DocumentBuilderFactory，提供一个解析工厂
     * 
     * @return a DocumentBuilderFactory
     */
    private static DocumentBuilderFactory newDocumentBuilderFactory()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf;
    }

    /*
     * 将传入的一个XML String转换成一个org.w3c.dom.Document对象返回。
     * 
     * @param xmlString 一个符合XML规范的字符串表达。
     * 
     * @return a Document
     */
    public static Document parseXMLDocument(String xmlString)
    {
        if (xmlString == null)
        {
            throw new IllegalArgumentException();
        }
        try
        {
            return newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*
     * 给定一个输入流，解析为一个org.w3c.dom.Document对象返回。
     * 
     * @param input
     * 
     * @return a org.w3c.dom.Document
     */
    public static Document parseXMLDocument(InputStream input)
    {
        if (input == null)
        {
            throw new IllegalArgumentException("参数为null！");
        }
        try
        {
            return newDocumentBuilder().parse(input);
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*
     * 给定一个文件名，获取该文件并解析为一个org.w3c.dom.Document对象返回。
     * 
     * @param fileName 待解析文件的文件名
     * 
     * @return a org.w3c.dom.Document
     */
    public static Document loadXMLDocumentFromFile(String fileName)
    {
        if (fileName == null)
        {
            throw new IllegalArgumentException("未指定文件名及其物理路径！");
        }
        try
        {
            return newDocumentBuilder().parse(new File(fileName));
        } catch (SAXException e)
        {
            throw new IllegalArgumentException("目标文件（" + fileName + "）不能被正确解析为XML！" + e.getMessage());
        } catch (IOException e)
        {
            throw new IllegalArgumentException("不能获取目标文件（" + fileName + "）！" + e.getMessage());
        } catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*
     * 把dom文件转换为xml字符串
     */
    public static String toStringFromDoc(Document document)
    {
        String result = null;

        if (document != null)
        {
            StringWriter strWtr = new StringWriter();
            StreamResult strResult = new StreamResult(strWtr);
            TransformerFactory tfac = TransformerFactory.newInstance();
            try
            {
                javax.xml.transform.Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
                // text
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                t.transform(new DOMSource(document.getDocumentElement()), strResult);
            } catch (Exception e)
            {
                System.err.println("XML.toString(Document): " + e);
            }
            result = strResult.getWriter().toString();
            try
            {
                strWtr.close();
            } catch (IOException e)
            {
            	e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * 把dom文件转换为xml文件
     */
    public static String toFileFromDoc(Document document, String filePath)
    {
        String result = null;

        if (document != null)
        {
            StringWriter strWtr = new StringWriter();
            StreamResult strResult = new StreamResult(strWtr);
            TransformerFactory tfac = TransformerFactory.newInstance();
            try
            {
                javax.xml.transform.Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
                // text
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                t.transform(new DOMSource(document.getDocumentElement()), strResult);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            result = strResult.getWriter().toString();
            try
            {
                StrToFile(result, filePath);
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
            try
            {
                strWtr.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * 将xml字符串写入文件
     */
    public static void StrToFile(String str, String filePath) throws Exception
    {
        FileWriter file = new FileWriter(filePath);
        PrintWriter out = new PrintWriter(new BufferedWriter(file, 1024));
        out.write(str);
    }

    /*
     * 获取一个Transformer对象，由于使用时都做相同的初始化，所以提取出来作为公共方法。
     * 
     * @return a Transformer encoding gb2312
     */
    private static Transformer newTransformer()
    {
        try
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Properties properties = transformer.getOutputProperties();
            properties.setProperty(OutputKeys.ENCODING, "utf-8");
            properties.setProperty(OutputKeys.METHOD, "xml");
            properties.setProperty(OutputKeys.VERSION, "1.0");
            properties.setProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperties(properties);
            return transformer;
        } catch (TransformerConfigurationException tce)
        {
            throw new RuntimeException(tce.getMessage());
        }
    }

    /*
     * 查找节点，并返回第一个符合条件节点
     */
    public static Node selectSingleNode(String express, Object source)
    {
        Node result = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try
        {
            result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /*
     * 查找节点，并返回符合条件所有节点
     */
    public static NodeList selectNodes(String express, Object source)
    {
        NodeList result = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try
        {
            result = (NodeList) xpath.evaluate(express, source, XPathConstants.NODESET);
        } catch (XPathExpressionException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 获取Node的xml字符串
     */
    public static String toStrFromNode(Node node)
    {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        try
        {
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource();
            source.setNode(node);
            StringWriter strWtr = new StringWriter();
            StreamResult result = new StreamResult(strWtr);
            // result.setOutputStream(System.out);
            transformer.transform(source, result);
            String str = result.getWriter().toString();
            return str;
        } catch (TransformerConfigurationException e)
        {
            e.printStackTrace();
        } catch (TransformerException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 根据节点名称获取节点的所有文本值
     */
    public static List<String> getTexts(Node node, String TagName)
    {

        List<String> list = new ArrayList<String>();
        NodeList node3 = XmlUtil.selectNodes(".//" + TagName, node);
        for (int i = 0; i < node3.getLength(); i++)
        {
            String str = node3.item(i).getTextContent();
            list.add(str);
        }
        return list;
    }

    /*
     * 在节点集中根据节点名称获取节点的文本值，若有多个则为第一个
     */
    public static String getText(Node node, String TagName)
    {

        Node node3 = XmlUtil.selectSingleNode(".//" + TagName, node);
        String text = node3.getTextContent();
        return text;
    }

    /*
     * 在节点集中根据节点名称获取节点的所有属性值
     */
    public static NamedNodeMap getAttrs(Node node, String TagName)
    {

        Node node4 = XmlUtil.selectSingleNode(".//" + TagName, node);
        NamedNodeMap str = node4.getAttributes();
        return str;
    }

    /*
     * 在节点集中根据节点名称获取节点的指定属性值
     */
    public static String getAttr(Node node, String TagName, String attrName)
    {

        Node node4 = XmlUtil.selectSingleNode(".//" + TagName, node);
        String attrValue = node4.getAttributes().getNamedItem(attrName).getNodeValue();
        return attrValue;
    }

    /*
     * 在Node中获取节点所有属性值封装到 Map中
     */
    public static Map<String, String> getAttrMap(Node node)
    {
        Map<String, String> map = new HashMap<String, String>();

        NamedNodeMap subListAttr = node.getAttributes();
        for (int i = 0; i < subListAttr.getLength(); i++)
        {
            Node nodeAttr = subListAttr.item(i);
            map.put(nodeAttr.getNodeName(), nodeAttr.getNodeValue());
        }
        return map;
    }

    /*
     * 在NodeList中获取节点所有属性值封装到 List<Map>中
     */
    public static List<Map<String, String>> getAttrListMap(NodeList nodeList, List<Map<String, String>> list)
    {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node nodeF = nodeList.item(i);
            if (nodeF.hasAttributes())
            {
                map = getAttrMap(nodeF);
                list.add(map);
            }
            if (nodeF.hasChildNodes())
            {
                XmlUtil.getAttrListMap(nodeF.getChildNodes(), list);
            }
        }
        return list;
    }

    /*
     * 将文件xml里面的属性全部解析，存放在List<Map>中
     */
    public static List<Map<String, String>> fileToMap(String filePath)
    {
        if (filePath == null || "".equals(filePath))
        {
            return null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Document doc = XmlUtil.loadXMLDocumentFromFile(filePath);

        Element root = doc.getDocumentElement();
        NodeList subList = root.getChildNodes();
        XmlUtil.getAttrListMap(subList, list);
        return list;
    }

    /*
     * 将String里面的属性全部解析，存放在List<Map>中
     */
    public static List<Map<String, String>> StringToMap(String xmlString)
    {
        if (xmlString == null || "".equals(xmlString))
        {
            return null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Document doc = XmlUtil.parseXMLDocument(xmlString);

        Element root = doc.getDocumentElement();
        NodeList subList = root.getChildNodes();
        XmlUtil.getAttrListMap(subList, list);
        return list;
    }

    /*
     * 将String里面的属性全部解析，存放在List<Map>中,此方法可将第一行解析，上面方法第一行不解析
     */
    public static List<Map<String, String>> StringToMap2(String xmlString)
    {
        if (xmlString == null || "".equals(xmlString))
        {
            return null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();

        Document doc = XmlUtil.parseXMLDocument(xmlString);

        Element root = doc.getDocumentElement();
        map = getAttrMap(root);
        list.add(map);
        NodeList subList = root.getChildNodes();
        XmlUtil.getAttrListMap(subList, list);
        return list;
    }
    
}
