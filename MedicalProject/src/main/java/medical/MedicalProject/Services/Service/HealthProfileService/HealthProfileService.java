package medical.MedicalProject.Services.Service.HealthProfileService;

import medical.MedicalProject.Services.SeedDto.SeedHealthProfile.HealthProfileDto;
import medical.MedicalProject.Services.SeedDto.SeetBloodResult.BloodResultDto;
import medical.MedicalProject.Services.ViewDto.HealthProfileView.HealthProfileViewDto;

public interface HealthProfileService {
    void createHealthProfile(HealthProfileDto healthProfileDto);

    void addBloodResult(BloodResultDto bloodResultDto);

    HealthProfileViewDto getHealthProfile(int id);
}
