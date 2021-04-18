package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.DayEnum;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDoctorProfileServiceModel {


    private MedicalBranchesEnum medicalBranch;

    private String fullName;


    private String bio;


    private String phone;


    private MultipartFile img;


    private String roomNumber;


    private int experience;


    private String monday;


    private String tuesday;


    private String wednesday;


    private String thursday;


    private String friday;

    public Map<DayEnum, String> makeSchedule() {
        Map<DayEnum, String> schedule = new LinkedHashMap<>();
        schedule.put(DayEnum.MONDAY, this.monday);
        schedule.put(DayEnum.TUESDAY, this.tuesday);
        schedule.put(DayEnum.WEDNESDAY, this.wednesday);
        schedule.put(DayEnum.THURSDAY, this.thursday);
        schedule.put(DayEnum.FRIDAY, this.friday);
        return schedule;
    }
}
