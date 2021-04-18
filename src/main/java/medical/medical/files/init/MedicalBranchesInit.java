package medical.medical.files.init;

import medical.medical.files.service.MedicalBranchesService;
import org.springframework.stereotype.Component;



import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;



@Component
public class MedicalBranchesInit {

    private static final String one = "Cardiology - branch of medicine that deals with disorders of the heart and the blood vessels.";
    private static final String two = "Dermatology - branch of medicine that deals with the skin, hair, and nails.";
    private static final String three = "General practice - called (family medicine) is a branch of medicine that specializes in primary care.";
    private static final String four = "Neurology - branch of medicine that deals with the brain and the nervous system.";
    private static final String five = "Obstetrics - care of women during and after pregnancy";
    private static final String six = "Orthopaedics - a branch of medicine that deals with conditions involving the musculoskeletal system.";
    private static final String seven = "Otorhinolaryngology - a branch of medicine that deals with the ears, nose, and throat.";
    private static final String eight = "Pulmonology - a branch of medicine that deals with the respiratory system.";
    private static final String nine = "Surgery - branch of medicine that uses operative techniques to investigate or treat both disease and injury, or to help improve bodily function or appearance.";
    private final BufferedReaderClass bufferedReader;
    private final MedicalBranchesService medicalBranchesService;

    public MedicalBranchesInit(BufferedReaderClass bufferedReader, MedicalBranchesService medicalBranchesService) {
        this.bufferedReader = bufferedReader;
        this.medicalBranchesService = medicalBranchesService;
    }

    public void addMedicineBranches() throws IOException {
        Map<String, String> medicalBranchesDescription = this.getMedicalBranchesDescription();
        for (Map.Entry<String, String> medicalBranch : medicalBranchesDescription.entrySet()) {
            this.medicalBranchesService.saveBranch(medicalBranch.getKey(), medicalBranch.getValue());


        }
    }

    private Map<String, String> getMedicalBranchesDescription() throws IOException {
//
        String string;
        String[] all = {one, two, three, four, five, six, seven, eight, nine};
        Map<String, String> medicalBranches = new LinkedHashMap<>();
        for (int i = 0; i <all.length ; i++) {
            string=all[i];
//
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
