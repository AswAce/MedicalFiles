package medical.medical.files.service;

import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.bindingModels.FeedbackBindingModel;
import medical.medical.files.model.enteties.ExaminationEntity;
import medical.medical.files.model.serviceModels.*;
import medical.medical.files.model.viewModels.ExaminationViewModel;

import java.util.ArrayList;

public interface ExaminationService {
    void editExamination(AddExaminationServiceModel examinationServiceModel, Long id);

    void deleteExamination(long id);

    void addDoctorPart(ExaminationByDoctorServiceModel examinationByDoctorServiceModel, long id) throws ExaminationNotFoundException;

    void addPrescription(PrescriptionServiceModel prescriptionServiceModel, long id) throws ExaminationNotFoundException;

    void addAdditionalData(AdditionalDataServiceModel additionalDataServiceModel, long examinationId) throws ExaminationNotFoundException;

    void completeExamination(long doctorId, long examinationId) throws ExaminationNotFoundException;

    ExaminationEntity findByExaminationId(long examinationId) throws ExaminationNotFoundException;

      ExaminationViewModel getExamination(ExaminationEntity examination);

    ArrayList<ExaminationViewModel> findByDoctorId(long doctorId);

    FeedbackBindingModel addFeedback(FeedbackServiceModel feedbackServiceModel, long examinationId) throws ExaminationNotFoundException;

    ExaminationEntity addExamination(AddExaminationServiceModel examinationServiceModel);
}
