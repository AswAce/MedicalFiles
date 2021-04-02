package medical.medical.files.service;

import medical.medical.files.model.bindingModels.AddDoctorProfileBindingModel;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.MedicalBranchEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.serviceModels.AddDoctorProfileServiceModel;
import medical.medical.files.model.viewModels.DoctorSetViewModel;
import medical.medical.files.model.viewModels.SingleDoctorView;

import java.io.IOException;
import java.util.Set;

public interface DoctorService {
    DoctorEntity findByName(String doctorName, MedicalBranchesEnum medicalBranchesEnum);

    void saveDoctorToProfile(AddDoctorProfileServiceModel addDoctorProfileServiceModel) throws IOException;

    Set<DoctorSetViewModel> getAll();

    SingleDoctorView findById(long id);


    long getCount();

    DoctorEntity findByIdSetViewDoctor(int randomNumber);

    DoctorEntity findByIdEntity(Long valueOf);
}
