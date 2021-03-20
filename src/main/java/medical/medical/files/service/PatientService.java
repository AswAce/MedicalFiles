package medical.medical.files.service;

import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.bindingModels.PatientBindingModel;
import medical.medical.files.model.serviceModels.AddExaminationServiceModel;
import medical.medical.files.model.serviceModels.PatientServiceModel;
import medical.medical.files.model.viewModels.DiseaseViewModel;
import medical.medical.files.model.viewModels.ExaminationViewModel;
import medical.medical.files.model.viewModels.PatientViewModel;

import java.util.ArrayList;

public interface PatientService {
    PatientBindingModel savePatient(PatientServiceModel createPatientServiceModel);

    void addExaminationToThePatient(AddExaminationServiceModel examinationServiceModel);

    PatientViewModel findById(long patientId);

    ExaminationViewModel getPatientExamination(long patientId, long examinationId) throws ExaminationNotFoundException;

    ArrayList<DiseaseViewModel> getAllDiseasesByPatientId(long patientId);
}
