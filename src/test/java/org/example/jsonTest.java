package org.example;

import junit.framework.TestCase;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class jsonTest extends TestCase {

    @Test
    public void testReadFromFileJSON1(){
        String res = "3 5 6 2 7 1 8 3";
        String f = "";
        ArrayList<Line> tmp = json.ReadFromFileJSON("src/test/java/inputfiles/test1.json");
        for(int i = 0; i < tmp.size(); i++) {
            if(i == tmp.size() - 1)
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
    public void testReadFromFileJSON2(){
        String res = "2 5 0 2 0 0 1 1 2 0 4 2";
        String f = "";
        ArrayList<Line> tmp = json.ReadFromFileJSON("src/test/java/inputfiles/test2.json");
        for(int i = 0; i < tmp.size(); i++) {
            if(i == tmp.size() - 1)
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