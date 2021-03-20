package medical.medical.files.init;


import org.springframework.stereotype.Component;

import java.io.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static medical.medical.files.filesPaths.TextFilesPaths.MEDICAL_BRANCHES_INIT_FILE;

@Component
public class BufferedReaderClass {
    private BufferedReader bufferedReader;

    public BufferedReaderClass( )   {

    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(String path) throws FileNotFoundException {
        File file = new File(path);

        this.bufferedReader = new BufferedReader(new FileReader(file));
    }


}
