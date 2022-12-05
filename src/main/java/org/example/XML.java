package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class XML {
    public static Node getLanguage(Document doc, String name1, String name2, String dot) {
        Element lines = doc.createElement("Lines");
        lines.appendChild(getLanguageElements(doc, lines, "First", name1));
        lines.appendChild(getLanguageElements(doc, lines, "Second", name2));
        lines.appendChild(getLanguageElements(doc, lines, "dot", dot));
        return lines;
    }

    static Node getLanguageElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static TreeMap<Integer, Line> ReadXML(String file) throws ParserConfigurationException, IOException, SAXException {
        TreeMap<Integer, Line> mapXML = new TreeMap<Integer, Line>();
        ArrayList<Integer> dotsXML = new ArrayList<Integer>();
        int k = 1;
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        Node root = document.getDocumentElement().getFirstChild();
        Node line = root.getNextSibling();
        while (line != null) {
            String str = line.getTextContent();
            String regex = "[^\\d]";

            String[] reg = str.split(regex);

            for (String word : reg) {
                try {
                    Integer rez = Integer.valueOf(word);
                    dotsXML.add(rez);
                } catch (NumberFormatException e) {
                }
            }
            mapXML.put(k, new Line(dotsXML.get(0), dotsXML.get(1), dotsXML.get(2), dotsXML.get(3)));
            dotsXML.clear();
            line = line.getNextSibling().getNextSibling();
            k++;
        }
        return mapXML;
    }

    public static void WriteXML(TreeMap<Integer, Line> mapXML, String file) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document2 = builder.newDocument();
        Element element = document2.createElementNS("lines", "answer");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transform = transformerFactory.newTransformer();
        transform.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document2);
        StreamResult result = new StreamResult(new File(file));
        String answerXML = Line.SolXML(mapXML.get(1), mapXML.get(2));
        String line1XML = "y = " + mapXML.get(1).k + "x + " + mapXML.get(1).b;
        String line2XML = "y = " + mapXML.get(2).k + "x + " + mapXML.get(2).b;
        document2.appendChild(element);
        element.appendChild(XML.getLanguage(document2, line1XML, line2XML, answerXML));
        for (int i = 1; i <= mapXML.size() - 1; i++) {
            for (int j = i + 1; j <= mapXML.size(); j++) {
                answerXML = Line.SolXML(mapXML.get(i), mapXML.get(j));
                line1XML = "y = " + mapXML.get(i).k + "x + " + mapXML.get(i).b;
                line2XML = "y = " + mapXML.get(j).k + "x + " + mapXML.get(j).b;
                element.appendChild(XML.getLanguage(document2, line1XML, line2XML, answerXML));
            }
        }
        transform.transform(source, result);
    }
}