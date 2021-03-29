package medical.medical.files.service;

import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.MedicalBranchEntity;
import medical.medical.files.model.enteties.ScheduleEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.viewModels.DepartmentView;
import medical.medical.files.model.viewModels.SingleDepartmentView;

import java.util.Set;

public interface MedicalBranchesService {
    void saveBranch(String name, String description);
   MedicalBranchEntity findByName(String name);

    String addPhoto(MedicalBranchesEnum valueOf);


    MedicalBranchEntity findByMedicalEnum(MedicalBranchesEnum medicalBranch);

    void addDoctorToTheBranch(MedicalBranchEntity medicalBranchEntity);

    Set< DepartmentView> getAllDepartments();

    SingleDepartmentView findById(long id);

    void deleteDoctorFromBrnach(DoctorEntity doctorEntity);

    void removeDoctorFromBranch(MedicalBranchesEnum medicalBranch, DoctorEntity doctorEntity);
}
