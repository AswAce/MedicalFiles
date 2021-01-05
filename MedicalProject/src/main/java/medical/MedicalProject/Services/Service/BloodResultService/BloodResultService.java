package medical.MedicalProject.Services.Service.BloodResultService;

import medical.MedicalProject.Services.SeedDto.SeetBloodResult.BloodResultDto;
import medical.MedicalProject.Services.ViewDto.BloodResultView.BloodResultDtoView;

import java.util.ArrayList;

public interface BloodResultService {

    void saveBloodResult(BloodResultDto bloodResultDto);

    ArrayList<BloodResultDtoView> getAllBloodServiceForHealthProfile(int id);
}
