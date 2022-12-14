package org.example;

import org.xml.sax.SAXException;

import javax.crypto.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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

        Comparator<Line> byK = (Line o1, Line o2) -> Double.toString(o1.k).compareTo(Double.toString(o2.k));
        LinkedHashMap<Integer, Line> sortedMap = mapJSON.entrySet().stream()
                .sorted(Map.Entry.<Integer, Line>comparingByValue(byK))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Set set = sortedMap.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            mapJSON.get(me.getKey()).Print();
        }

        //------- Rar archiving --------

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("output.rar"));
        FileInputStream fileInputStream = new FileInputStream("out_file.json");
        ZipEntry zipEntry = new ZipEntry("out_file.json");
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(fileInputStream.readAllBytes());
        zipOutputStream.closeEntry();
        fileInputStream = new FileInputStream("out.xml");
        zipEntry = new ZipEntry("out.xml");
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(fileInputStream.readAllBytes());
        zipOutputStream.closeEntry();
        zipOutputStream.close();

        //------------------------------

        //------- Jar archiving --------

        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream("output.jar"));
        fileInputStream = new FileInputStream("out_file.json");
        JarEntry jarEntry = new JarEntry("out_filet.json");
        jarOutputStream.putNextEntry(jarEntry);
        jarOutputStream.write(fileInputStream.readAllBytes());
        jarOutputStream.closeEntry();
        fileInputStream = new FileInputStream("out.xml");
        jarEntry = new JarEntry("out.xml");
        jarOutputStream.putNextEntry(jarEntry);
        jarOutputStream.write(fileInputStream.readAllBytes());
        jarOutputStream.closeEntry();
        jarOutputStream.close();

        //------------------------------

        //--------- Encryption ----------

        Cipher cipher_encrypted = Cipher.getInstance("AES");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        Key key = keyGenerator.generateKey();
        cipher_encrypted.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher_encrypted.doFinal(new FileInputStream("out_file.json").readAllBytes());
        FileOutputStream fileOutputStream = new FileOutputStream("output_encrypted.json");
        fileOutputStream.write(cipherText);
        fileOutputStream.close();

        Cipher cipher_deencrypted = Cipher.getInstance("AES");
        cipher_deencrypted.init(Cipher.DECRYPT_MODE, key);
        byte[] cipher_deencrypted_Text = cipher_deencrypted.doFinal(new FileInputStream("output_encrypted.json").readAllBytes());
        fileOutputStream = new FileOutputStream("output_deencrypted.json");
        fileOutputStream.write(cipher_deencrypted_Text);
        fileOutputStream.close();

        //------------------------------
    }
}