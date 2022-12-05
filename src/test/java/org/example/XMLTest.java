package org.example;

import junit.framework.TestCase;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.TreeMap;

public class XMLTest extends TestCase {

    @Test
    public void testReadXML1() throws ParserConfigurationException, IOException, SAXException {
        String res = "3 5 6 2 7 1 8 3";
        String f = "";
        TreeMap<Integer, Line> tmp = XML.ReadXML("src/test/java/inputfiles/test1.xml");
        for(int i = 1; i <= tmp.size(); i++) {
            if(i == tmp.size())
            {
                f += tmp.get(i).x + " " + tmp.get(i).y + " " + tmp.get(i).x1 + " " + tmp.get(i).y1;
            }
            else {
                f += tmp.get(i).x + " " + tmp.get(i).y + " " + tmp.get(i).x1 + " " + tmp.get(i).y1 + " ";
            }
        }
        assertEquals(res, f);
    }

    @Test
    public void testReadXML2() throws ParserConfigurationException, IOException, SAXException {
        String res = "2 5 0 2 0 0 1 1 2 0 4 2";
        String f = "";
        TreeMap<Integer, Line> tmp = XML.ReadXML("src/test/java/inputfiles/test2.xml");
        for(int i = 1; i <= tmp.size(); i++) {
            if(i == tmp.size())
            {
                f += tmp.get(i).x + " " + tmp.get(i).y + " " + tmp.get(i).x1 + " " + tmp.get(i).y1;
            }
            else {
                f += tmp.get(i).x + " " + tmp.get(i).y + " " + tmp.get(i).x1 + " " + tmp.get(i).y1 + " ";
            }
        }
        assertEquals(res, f);
    }
}