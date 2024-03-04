package hashlab.utils;

import java.io.File;

public class FileUnils {

    public static String getFilePath(String resultFileName){
        String baseDirectory = ".";
        File file = new File(baseDirectory, resultFileName + ".csv");
        return file.getAbsolutePath();
    }
}
