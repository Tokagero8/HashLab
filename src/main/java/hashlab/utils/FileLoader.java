package hashlab.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public String loadFileContent(String filePath) throws IOException{
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null){
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        return content.toString();
    }
}
