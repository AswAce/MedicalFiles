package medical.MedicalProject.Utils;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;

@Data
@AllArgsConstructor
public class FIleIotImpl implements  FileIot {
    @Override
    public void write(String file, String path) throws IOException {

        FileWriter fileWriter=new FileWriter(path);
        fileWriter.write(file);
        fileWriter.flush();
    }
}
