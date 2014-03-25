package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Util {
    
    public static void saveFile(File file,String path) throws IOException {
        FileWriter fstream = new FileWriter(path);
        BufferedWriter out = new BufferedWriter(fstream);
        BufferedReader bReader = new BufferedReader(new FileReader(file));
        String line = "", content = "";
        while ((line = bReader.readLine()) != null) 
            content += line+"\n";
        out.write(content);
        out.close();
        bReader.close();
        fstream.close();
    }

}
