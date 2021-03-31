package medical.medical.files.service;

import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.exeptions.WrongDoctorException;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.ExaminationEntity;
import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.serviceModels.*;
import medical.medical.files.model.viewModels.ExaminationViewModel;
import medical.medical.files.model.viewModels.SetExaminationsForUserView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ExaminationService {




    void addPrescription(AddPrescriptionServiceModel prescriptionServiceModel ) throws ExaminationNotFoundException;

    void addAdditionalData(AdditionalDataServiceModel additionalDataServiceModel ) throws ExaminationNotFoundException, WrongDoctorException, IOException;

    void completeExamination ( long examinationId) throws ExaminationNotFoundException;

    ExaminationEntity findByExaminationId(long examinationId) throws ExaminationNotFoundException;

      ExaminationViewModel getExamination(ExaminationEntity examination);

    ArrayList<ExaminationViewModel> findByDoctorId(long doctorId);



    ExaminationEntity addExamination(AddExaminationServiceModel examinationServiceModel);

    List<SetExaminationsForUserView> findAllExaminationsForThisUser(long id);

    ExaminationViewModel getExaminationView(long id) throws ExaminationNotFoundException;

    void addLocationDetails(ExaminationByDoctorServiceModel examinationByDoctorServiceModel) throws ExaminationNotFoundException;

    ArrayList<ExaminationViewModel> findExaminationFoThisPatient(long patientId);

    ArrayList<ExaminationViewModel> findAllByPatientIdAndDepartment(long id, MedicalBranchesEnum name);


    int countAll();

    void deleteExamiantionsForDoctor(DoctorEntity doctorEntity);

    ArrayList<SetExaminationsForUserView> getAll();

    void deleteExamination(long id);
}
