package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;


public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        TreeMap<Integer, Line> mapJSON = new TreeMap<>();
        ArrayList<Res> ResJSON = new ArrayList<Res>();
        ArrayList<Line> linesJ = json.ReadFromFileJSON("jsonin.json");
        for(int i = 0; i < linesJ.size(); i++)
        {
            mapJSON.put(i+1, linesJ.get(i));
        }
        String resJ, line1J, line2J;
        for(int i = 1; i < mapJSON.size(); i++) {
            for (int j = i + 1; j <= mapJSON.size(); j++) {
                resJ = Line.SolXML(mapJSON.get(i), mapJSON.get(j));
                line1J = "y = " + mapJSON.get(i).k + "x + " + mapJSON.get(i).b;
                line2J = "y = " + mapJSON.get(j).k + "x + " + mapJSON.get(j).b;
                ResJSON.add(new Res(line1J, line2J, resJ));
            }
        }
        json.WriteInFileJSON(ResJSON);

        TreeMap<Integer, Line> mapXML = XML.ReadXML("input.xml");
        XML.WriteXML(mapXML, "out.xml");
    }
}