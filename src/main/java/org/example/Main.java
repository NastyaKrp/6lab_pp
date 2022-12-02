package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

class Line {
    Integer x = 0;
    Integer y = 0;
    Integer x1 = 0;
    Integer y1 = 0;
    double k = Double.valueOf(1);
    double b = Double.valueOf(0);

    Line(Integer X, Integer Y, Integer X1, Integer Y1) {
        x = X;
        y = Y;
        x1 = X1;
        y1 = Y1;
        k = (double) (Y1 - Y) / (X1 - X);
        b = Y - X * k;
    }

    public static void SolTXT(Line d1, Line d2) {
        if (d1.k == d2.k && d1.b != d2.b) {
            System.out.println("line 1: y = " + d1.k + "x + " + d1.b + "; line 2: y = " + d2.k + "x + " + d2.b + "; are ||");
        } else if (d1.k == d2.k && d1.b == d2.b) {
            System.out.println("line 1: y = " + d1.k + "x + " + d1.b + "; line 2: y = " + d2.k + "x + " + d2.b + "; are equal");
        } else {
            double x = (d2.b - d1.b) / (d1.k - d2.k);
            double y = x * d1.k + d1.b;
            System.out.println("line 1: y = " + d1.k + "x + " + d1.b + "; line 2: y = " + d2.k + "x + " + d2.b + "; (" + x + "; " + y + ")");
        }
    }

    public static String SolXML(Line d1, Line d2) {
        String answer = "";
        if (d1.k == d2.k && d1.b != d2.b) {
            return  "are ||";
        } else if (d1.k == d2.k && d1.b == d2.b) {
            return "are equal";
        } else {
            double x = (d2.b - d1.b) / (d1.k - d2.k);
            double y = x * d1.k + d1.b;
            return "(" + x + "; " + y + ")";
        }
    }
}

class Res
{
    String line1;
    String line2;
    String dot;

    Res(String l1, String l2, String d)
    {
        line1 = l1;
        line2 = l2;
        dot = d;
    }
}


class WorkWithJSON {
    public static ArrayList<Line> ReadFromFileJSON(String filename) {
        ArrayList<Line> lines = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray list = (JSONArray) jsonParser.parse(new FileReader(filename));
            for (int i = 0; i < list.size(); ++i) {
                JSONObject object = (JSONObject) list.get(i);
                Integer x1, y1, x2, y2;
                x1 = Integer.valueOf((String) object.get("x1"));
                x2 = Integer.valueOf((String) object.get("x2"));
                y1 = Integer.valueOf((String) object.get("y1"));
                y2 = Integer.valueOf((String) object.get("y2"));
                Line line = new Line(x1, y1, x2, y2);
                lines.add(line);
            }
        } catch (IOException | org.json.simple.parser.ParseException ex) {
            throw new RuntimeException(ex);
        }
        return lines;
    }
    public static void WriteInFileJSON(ArrayList<Res> p) throws IOException {
        FileWriter writer = new FileWriter("out_file.json");
        JSONArray obj = new JSONArray();
        writer.write("[ ");
        for(int i = 0; i < p.size(); ++i) {
            if(i + 1 == p.size()) {
                JSONObject object = new JSONObject();
                object.put("Line1", p.get(i).line1);
                object.put("Line2", p.get(i).line2);
                object.put("Dot", p.get(i).dot);
                writer.write(object.toJSONString());
                break;
            }
            JSONObject object = new JSONObject();
            object.put("Line1", p.get(i).line1);
            object.put("Line2", p.get(i).line2);
            object.put("dot", p.get(i).dot);
            writer.write(object.toJSONString() + ",\n");
        }
        writer.write(" ]");
        writer.flush();
    }
}

class WorkWithXML
{
    public static Node getLanguage(Document doc, String name1, String name2, String dot) {
        Element lines = doc.createElement("Lines");
        lines.appendChild(getLanguageElements(doc, lines, "First", name1));
        lines.appendChild(getLanguageElements(doc, lines, "Second", name2));
        lines.appendChild(getLanguageElements(doc, lines, "dot", dot));
        return lines;
    }

    private static Node getLanguageElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}


public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        TreeMap<Integer, Line> mapJSON = new TreeMap<>();
        ArrayList<Res> ResJSON = new ArrayList<Res>();
        ArrayList<Line> linesJ = WorkWithJSON.ReadFromFileJSON("jsonin.json");
        for(int i = 0; i < linesJ.size(); i++)
        {
            mapJSON.put(i+1, linesJ.get(i));
        }
        String resJ, line1J, line2J;
        for(int i = 1; i < mapJSON.size(); i++) {
            for (int j = i + 1; j <= mapJSON.size(); j++) {
                resJ = mapJSON.get(i).SolXML(mapJSON.get(i), mapJSON.get(j));
                line1J = "y = " + mapJSON.get(i).k + "x + " + mapJSON.get(i).b;
                line2J = "y = " + mapJSON.get(j).k + "x + " + mapJSON.get(j).b;
                ResJSON.add(new Res(line1J, line2J, resJ));
            }
        }
        WorkWithJSON.WriteInFileJSON(ResJSON);

        TreeMap<Integer, Line> mapXML = new TreeMap<>();
        ArrayList<Integer> dotsXML = new ArrayList<Integer>();
        int k = 1;

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse("input.xml");

        Node root = document.getDocumentElement().getFirstChild();
        Node line = root.getNextSibling();
        while(line != null)
        {
            String str = line.getTextContent();
            String regex = "[^\\d]";

            String[] reg = str.split(regex);

            for(String word : reg){
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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document2 = builder.newDocument();
        Element element = document2.createElementNS("lines", "answer");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transform = transformerFactory.newTransformer();
        transform.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document2);
        StreamResult result = new StreamResult(new File("out.xml"));
        String answerXML = mapXML.get(1).SolXML(mapXML.get(1), mapXML.get(2));
        String line1XML = "y = " + mapXML.get(1).k + "x + " + mapXML.get(1).b;
        String line2XML = "y = " + mapXML.get(2).k + "x + " + mapXML.get(2).b;
        document2.appendChild(element);
        element.appendChild(WorkWithXML.getLanguage(document2, line1XML, line2XML, answerXML));
        for(int i = 1; i <= mapXML.size() - 1; i++)
        {
            for(int j = i + 1; j <= mapXML.size(); j++)
            {
                answerXML = mapXML.get(i).SolXML(mapXML.get(i), mapXML.get(j));
                line1XML = "y = " + mapXML.get(i).k + "x + " + mapXML.get(i).b;
                line2XML = "y = " + mapXML.get(j).k + "x + " + mapXML.get(j).b;
                element.appendChild(WorkWithXML.getLanguage(document2, line1XML, line2XML, answerXML));
            }
        }
        transform.transform(source, result);
    }
}