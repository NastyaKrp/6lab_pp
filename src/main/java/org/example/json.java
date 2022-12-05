package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class json {
    public static ArrayList<Line> ReadFromFileJSON(String filename) {
        ArrayList<Line> lines = new ArrayList<Line>();
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
        } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
        }
        return lines;
    }

    public static void WriteInFileJSON(ArrayList<Res> p) throws IOException {
        FileWriter writer = new FileWriter("out_file.json");
        JSONArray obj = new JSONArray();
        writer.write("[ ");
        for (int i = 0; i < p.size(); ++i) {
            if (i + 1 == p.size()) {
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