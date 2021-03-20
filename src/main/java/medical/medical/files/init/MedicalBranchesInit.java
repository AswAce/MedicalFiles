package medical.medical.files.init;

import medical.medical.files.service.MedicalBranchesService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static medical.medical.files.filesPaths.TextFilesPaths.MEDICAL_BRANCHES_INIT_FILE;

@Component
public class MedicalBranchesInit {

    private final BufferedReaderClass bufferedReader;
    private final MedicalBranchesService medicalBranchesService;

    public MedicalBranchesInit(BufferedReaderClass bufferedReader, MedicalBranchesService medicalBranchesService) {
        this.bufferedReader = bufferedReader;
        this.medicalBranchesService = medicalBranchesService;
    }

    public void addMedicineBranches() throws IOException {
        Map<String, String> medicalBranchesDescription = this.getMedicalBranchesDescription();
        for (Map.Entry<String, String> medicalBranch : medicalBranchesDescription.entrySet()){
                this.medicalBranchesService.saveBranch(medicalBranch.getKey(),medicalBranch.getValue());


        }
    }
    private Map<String, String> getMedicalBranchesDescription() throws IOException {
        this.bufferedReader.setBufferedReader(MEDICAL_BRANCHES_INIT_FILE);
        BufferedReader br = this.bufferedReader.getBufferedReader();
        String string;
        Map<String, String> medicalBranches = new LinkedHashMap<>();
        while ((string = br.readLine()) != null) {
            String[] split = string.split("( - )");
            String name = split[0].toUpperCase();
            String text = split[1].
                    replaceFirst(String.valueOf(split[1].charAt(0)),
                            String.valueOf(split[1].charAt(0)).toUpperCase());

            medicalBranches.put(name, text);
        }

        return medicalBranches;
    }
}
