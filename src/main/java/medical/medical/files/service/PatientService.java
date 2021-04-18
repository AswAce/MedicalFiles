package medical.medical.files.service;

import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.bindingModels.AddPatientBindingModel;
import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.serviceModels.AddDiseaseServiceModel;
import medical.medical.files.model.serviceModels.AddExaminationServiceModel;
import medical.medical.files.model.serviceModels.PatientServiceModel;
import medical.medical.files.model.viewModels.PatientViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public interface PatientService {
    void savePatient(PatientServiceModel createPatientServiceModel) throws IOException;



    PatientViewModel findById(long patientId);

    void deletePatient(PatientEntity patientEntity);


    void addDiseaseToThePatient(AddDiseaseServiceModel addDiseaseServiceModel) throws ExaminationNotFoundException;

    boolean patientHasExaminationsInThisDepartment(MedicalBranchesEnum name);

    Set<String> findPatientDoctorsForThisDepartment(MedicalBranchesEnum name);

    int countAll();

    ArrayList<PatientViewModel> getAll();

    void deletePatientById(long id);

    void editPatient(long roleId, AddPatientBindingModel patientBindingModel);
}
