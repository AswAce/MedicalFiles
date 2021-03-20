package medical.medical.files.service;

import medical.medical.files.model.enteties.MedicalBranchEntity;
import medical.medical.files.model.enteties.ScheduleEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;

public interface MedicalBranchesService {
    void saveBranch(String name, String description);
   MedicalBranchEntity findByName(String name);

    String addPhoto(MedicalBranchesEnum valueOf);
    ScheduleEntity createSchedule(MedicalBranchesEnum valueOf);
}
