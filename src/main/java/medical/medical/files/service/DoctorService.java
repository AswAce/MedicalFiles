package medical.medical.files.service;

import medical.medical.files.model.bindingModels.AddDoctorProfileBindingModel;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.serviceModels.AddDoctorProfileServiceModel;

import java.io.IOException;

public interface DoctorService {
    DoctorEntity findByName(String doctorName);
    void saveDoctorToProfile(AddDoctorProfileServiceModel addDoctorProfileServiceModel) throws IOException;
}
