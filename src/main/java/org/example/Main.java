package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

    public static void Sol(Line d1, Line d2) {
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

public class Main {

    public static void main(String[] args) throws IOException {
        TreeMap<Integer, Line> map = new TreeMap<>();
        ArrayList<Res> Res = new ArrayList<Res>();
        System.out.println("Hello world!");
        ArrayList<Line> linesJ = WorkWithJSON.ReadFromFileJSON("jsonin.json");
        System.out.println(linesJ.size());
        for(int i = 0; i < linesJ.size(); i++)
        {
            System.out.println(linesJ.get(0).x + "; " + linesJ.get(0).y + " : "+ linesJ.get(0).x1 + "; " + linesJ.get(0).y1);
        }
        for(int i = 0; i < linesJ.size(); i++)
        {
            map.put(i+1, linesJ.get(i));
        }
        String res, line1, line2;
        for(int i = 1; i < map.size(); i++) {
            for (int j = i + 1; j <= map.size(); j++) {
                res = map.get(i).SolXML(map.get(i), map.get(j));
                line1 = "y = " + map.get(i).k + "x + " + map.get(i).b;
                line2 = "y = " + map.get(j).k + "x + " + map.get(j).b;
                Res.add(new Res(line1, line2, res));
            }
        }
        WorkWithJSON.WriteInFileJSON(Res);
    }
}